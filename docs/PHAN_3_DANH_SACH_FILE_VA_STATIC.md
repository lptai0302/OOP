# BÁO CÁO CHI TIẾT PHẦN 3: LỚP MẢNG ĐỐI TƯỢNG, FILE TEXT UTF-8 & THÀNH PHẦN STATIC

> **Phân công phụ trách:** Thành viên 3  
> **Chủ đề:** Lớp mảng danh sách đối tượng (Class Array), Cơ chế Đọc/Ghi File Text chuẩn UTF-8 & Thuộc tính / Phương thức Static.

---

## 1. TỔNG QUAN CÁC LỚP PHỤ TRÁCH (4 LỚP & 3 FILE DỮ LIỆU)
1. `DanhSachSanPham` (Lớp mảng quản lý danh mục sản phẩm)
2. `DanhSachKhachHang` (Lớp mảng quản lý người dùng / nhân sự)
3. `ChiTietHoaDon` (Lớp trung gian biểu diễn 1 dòng sản phẩm trong đơn)
4. `HoaDon` (Lớp hóa đơn thanh toán & xuất báo cáo)
5. Dữ liệu File Text: `products.txt`, `users.txt`, `hoadon.txt`

---

## 2. NGUYÊN LÝ HƯỚNG ĐỐI TƯỢNG (OOP) CÀI ĐẶT TRONG PHẦN 3

### 2.1. Lớp mảng các đối tượng (Class Array / Collection Wrapper)
Đồ án không dùng mảng trần `ArrayList` rải rác trong `Main`, mà đóng gói toàn bộ logic vào 2 lớp chuyên biệt:
- **`DanhSachSanPham`**:
  - Quản lý tập hợp `ArrayList<SanPhamThoiTrang>`.
  - Cung cấp đầy đủ các nghiệp vụ quản lý: `them()`, `xoa(maSP)`, `sua(maSP, tenMoi, giaMoi)`, `timKiem(maSP)`, `timKiemTheoTen(tuKhoa)`.
  - Thuật toán tự sinh mã tự động `taoMaTuDong(loaiSP)`: Dựa trên tiền tố sản phẩm (`AK`, `QJ`, `AT`, `PK`, `VD`, `GD`) và quét mã số lớn nhất hiện có để tự động tăng dần (VD: `AK001`, `AK002`...).
- **`DanhSachKhachHang`**:
  - Quản lý tập hợp `ArrayList<Nguoi>`.
  - Cung cấp nghiệp vụ: `them()`, `timKiemTheoSDT()`, `sua(sdt, tenMoi)`, `xoaTheoSDT()`.
  - Kiểm tra tính toàn vẹn: Chặn tuyệt đối việc thêm trùng số điện thoại đã tồn tại.

### 2.2. Đọc và ghi dữ liệu File Text chuẩn hóa UTF-8
- Sử dụng cặp luồng tiêu chuẩn:
  - `BufferedReader` kết hợp `InputStreamReader(..., StandardCharsets.UTF_8)` để đọc.
  - `BufferedWriter` kết hợp `OutputStreamWriter(..., StandardCharsets.UTF_8)` để ghi.
- **Ưu điểm vượt trội**: Giữ nguyên vẹn 100% tiếng Việt có dấu trên mọi hệ điều hành (Windows, Linux, macOS) mà không bị lỗi font (mojibake).
- **Cơ chế lưu trữ**:
  - `products.txt`: Lưu trữ thông tin đa hình của 6 loại sản phẩm.
  - `users.txt`: Lưu trữ thông tin khách hàng VIP, thân thiết và nhân viên.
  - `hoadon.txt`: Tự động append lịch sử hóa đơn bán hàng cùng phiếu bảo hành mỗi khi hoàn tất thanh toán.

### 2.3. Cài đặt Thuộc tính Static & Phương thức Static
- **Thuộc tính static**:
  - `SanPhamThoiTrang.TI_LE_VAT = 0.1` (10%): Thuộc tính dùng chung cho toàn bộ cửa hàng, không bị sao chép thừa thãi cho từng đối tượng.
  - `SanPhamThoiTrang.tongSoLuongSanPhamDaTao`: Tự động tăng mỗi khi một sản phẩm mới được khởi tạo.
  - `HoaDon.tongSoHoaDon`: Biến static đếm tổng số hóa đơn đã xuất thành công trong phiên làm việc.
- **Phương thức static tiện ích**:
  - `SanPhamThoiTrang.formatTien(double)`: Định dạng số tiền sang chuẩn Việt Nam có dấu chấm phân cách hàng nghìn (VD: `1.800.000 VND`).
  - `SanPhamThoiTrang.formatCell(String, int)`: Cắt chuỗi và căn lề tự động, giúp hiển thị bảng danh sách sản phẩm thẳng hàng đẹp mắt như Excel trên giao diện Console.
  - `SanPhamThoiTrang.inTieuDeBang()`, `SanPhamThoiTrang.inChanBang()`: In viền khung bảng chuẩn.

---

## 3. BỘ CÂU HỎI VẤN ĐÁP THẦY HIẾU & CÂU TRẢ LỜI MẪU (10 ĐIỂM)

### Câu hỏi 1: Lớp mảng danh sách đối tượng có vai trò gì trong kiến trúc OOP so với việc dùng `ArrayList` trực tiếp ở hàm `main`?
- **Trả lời:** Việc đưa mảng vào trong một lớp riêng (`DanhSachSanPham`, `DanhSachKhachHang`) tuân thủ nguyên lý Đóng gói (Encapsulation) và Trách nhiệm đơn lẻ (Single Responsibility Principle). Hàm `main` chỉ đóng vai trò giao diện nhận lệnh, còn toàn bộ việc lưu trữ, tìm kiếm, kiểm tra trùng mã, tự sinh mã hay đọc/ghi file đều được đóng gói bên trong lớp danh sách, giúp code module hóa rõ ràng, dễ bảo trì và dễ mở rộng.

### Câu hỏi 2: Tại sao biến `TI_LE_VAT` lại được khai báo là `public static final`?
- **Trả lời:**
  - `static`: Vì tỷ lệ thuế VAT (10%) là quy định chung áp dụng cho mọi sản phẩm thời trang trong cửa hàng. Không cần thiết mỗi đối tượng sản phẩm phải tốn bộ nhớ lưu trữ riêng một bản sao tỷ lệ thuế.
  - `final`: Để đảm bảo giá trị thuế là hằng số cố định, không thể bị vô tình thay đổi ở bất cứ nơi nào trong quá trình chương trình chạy.

### Câu hỏi 3: Trong phương thức đọc file `products.txt`, làm thế nào chương trình biết dòng nào là Áo khoác, dòng nào là Áo thun hay Quần jean để khởi tạo đúng lớp con?
- **Trả lời:** Ở cột đầu tiên của mỗi dòng trong file text, em lưu định danh loại sản phẩm (`Type`). Khi đọc từng dòng, chương trình dùng hàm `split(",")`, lấy `parts[0]` và kiểm tra chuỗi: nếu là `AoKhoac` thì gọi `new AoKhoac(...)`, nếu là `AoThun` thì gọi `new AoThun(...)`. Đây chính là kỹ thuật Factory Method căn bản để tái tạo đối tượng đa hình từ dữ liệu lưu trữ dạng text.
