# HỆ THỐNG QUẢN LÝ CỬA HÀNG THỜI TRANG (JAVA OOP)

> **Đồ án môn Lập trình hướng đối tượng (OOP) - ĐH Sài Gòn (SGU)**  
> Ứng dụng Console quản lý bán hàng thời trang với đầy đủ nguyên lý OOP, thiết kế dạng bảng lưới Excel trực quan, tự động tính thuế VAT, chiết khấu khách hàng và in phiếu bảo hành.

---

## BẢNG ĐỐI CHIẾU TIÊU CHÍ CHẤM ĐIỂM CỦA GIẢNG VIÊN

| STT | Tiêu chí yêu cầu của Giảng viên | Cài đặt trong đồ án | Minh chứng trong mã nguồn |
|:---:|:---|:---|:---|
| 1 | **Đề tài hợp lệ** | Quản lý bán hàng thời trang (Mục 9 trong danh sách đề tài) | Toàn bộ hệ thống quản lý sản phẩm, đơn hàng, khách hàng |
| 2 | **Ít nhất 15 lớp đối tượng** | **20 Lớp (Classes)** + **3 Interfaces** (Vượt tiêu chuẩn) | `SanPhamThoiTrang`, `AoKhoac`, `AoThun`, `QuanJean`, `PhuKien`, `VayDam`, `GiayDep`, `Nguoi`, `KhachHang`, `KhachHangVIP`, `KhachHangThanThiet`, `NhanVien`, `NhanVienQuanLy`, `NhaCungCap`, `ChiTietHoaDon`, `HoaDon`, `DanhSachSanPham`, `DanhSachKhachHang`, `QuanLyCuaHang`, `Main` |
| 3 | **Hàm thiết lập (Constructor)** | 100% các lớp đều có constructor mặc định và constructor có tham số | Có mặt trong toàn bộ 20 lớp |
| 4 | **Kế thừa hợp lý** | 2 cây phân cấp kế thừa chính: Sản phẩm và Con người | `SanPhamThoiTrang` -> 6 lớp con; `Nguoi` -> `KhachHang` / `NhanVien` -> các lớp con chuyên biệt |
| 5 | **Đa hình (Polymorphism)** | Đa hình qua lớp trừu tượng và qua interface | Nạp chồng (Overloading), ghi đè (Overriding), gọi phương thức thông qua con trỏ lớp cha / interface |
| 6 | **Lớp mảng các đối tượng (danh sách)** | 2 lớp quản lý danh sách mảng chuyên biệt | `DanhSachSanPham` (quản lý mảng sản phẩm), `DanhSachKhachHang` (quản lý mảng người dùng) |
| 7 | **Đọc và ghi dữ liệu file (Text)** | Đọc/ghi dữ liệu kho hàng và lưu vết lịch sử hóa đơn | `products.txt` (kho hàng), `users.txt` (khách hàng/nhân viên), `hoadon.txt` (lịch sử xuất hóa đơn) |
| 8 | **Thuộc tính static & Phương thức static** | Cài đặt đầy đủ cả thuộc tính static và phương thức static | Thuộc tính: `SanPhamThoiTrang.TI_LE_VAT`, `SanPhamThoiTrang.tongSoLuongSanPhamDaTao`, `HoaDon.tongSoHoaDon`<br>Phương thức: `SanPhamThoiTrang.formatTien()`, `SanPhamThoiTrang.formatSo()`, `SanPhamThoiTrang.inTieuDeBang()` |
| 9 | **Lớp trừu tượng & Hàm trừu tượng** | `SanPhamThoiTrang` là `abstract class` | Các hàm trừu tượng: `getStringSaveFile()`, `getThongTinRieng()` |
| 10 | **Interface** | 3 Interface nghiệp vụ | `IBaoHanh` (phiếu bảo hành), `IGiamGia` (giảm giá sản phẩm), `IGiamGiaKhachHang` (chiết khấu đối tượng) |
| 11 | **Đầy đủ chức năng CRUD** | Xem, Thêm, Sửa (tên/giá/thuộc tính riêng), Xóa, Tìm kiếm (theo mã/tên) cho Sản phẩm & Khách hàng | Có đầy đủ trên Menu chính (Chức năng 1, 2, 3, 4, 5, 7) |

---

## GIAO DIỆN MENU CHÍNH

```text
<-----------------Quan Ly Cua Hang Thoi Trang----------------->
1. Them san pham
2. Xoa san pham
3. Tim kiem san pham
4. Sua thong tin san pham
5. Hien thi tat ca san pham
6. Xem chuong trinh khuyen mai
7. Quan ly Khach hang / Nhan vien
8. Thanh toan hoa don & In phieu bao hanh
9. Xem danh sach Nha cung cap
0. Luu va thoat
Nhap lua chon cua ban: _
```

---

## CHI TIẾT CÁC CHỨC NĂNG CHÍNH

### 1. Thêm sản phẩm (Tự sinh mã theo loại)
- Hỗ trợ 6 danh mục: `AoKhoac` (AK), `QuanJean` (QJ), `AoThun` (AT), `PhuKien` (PK), `VayDam` (VD), `GiayDep` (GD).
- Tự động sinh mã duy nhất tăng dần: `AK001`, `VD001`, `GD001`...
- Tự động lưu tức thì vào file `products.txt`.

### 2. Xóa sản phẩm
- Nhập mã sản phẩm cần xóa, kiểm tra tồn tại và đồng bộ file lưu trữ.

### 3. Tìm kiếm sản phẩm
- Hỗ trợ 2 chế độ tìm kiếm:
  1. Tìm theo mã sản phẩm (chính xác)
  2. Tìm theo tên sản phẩm (tìm kiếm gần đúng / từ khóa)
- Đóng khung kết quả dạng bảng lưới trực quan.

### 4. Sửa thông tin sản phẩm (Đầy đủ chức năng Sửa)
- Cho phép người dùng tùy chọn:
  1. Sửa tên sản phẩm
  2. Sửa đơn giá
  3. Sửa cả tên và đơn giá
  4. Sửa thuộc tính riêng của từng loại sản phẩm (Có nón, Kích cỡ, Kiểu dáng, Chất liệu, Size giày)

### 5. Hiển thị tất cả sản phẩm trong kho
- Đóng khung bảng dạng Excel, định dạng tiền tệ Việt Nam chuẩn dấu chấm (`1.800.000 VND`).

### 6. Xem chương trình khuyến mãi (10%)
- Hiển thị bảng đối chiếu giá gốc và giá sau khi giảm giá 10%.

### 7. Quản lý Khách hàng & Nhân viên (Đầy đủ CRUD)
- **Thêm**:
  - Khách hàng VIP: Chiết khấu **20%** tổng hóa đơn.
  - Khách hàng Thân thiết: Chiết khấu **10%** tổng hóa đơn.
  - Nhân viên thu ngân: Chiết khấu nội bộ **50%**.
  - Nhân viên quản lý: Chiết khấu nội bộ **50%** + có phụ cấp trách nhiệm.
- **Xem**: Xem danh sách toàn bộ khách hàng và nhân viên dạng bảng.
- **Sửa**: Sửa thông tin họ tên người dùng theo số điện thoại.
- **Xóa**: Xóa người dùng theo số điện thoại.
- **Tìm kiếm**: Tìm kiếm thông tin người dùng theo số điện thoại.

### 8. Thanh toán hóa đơn & In phiếu bảo hành
- Nhập mã sản phẩm và số lượng mua.
- Nhập số điện thoại để tự động áp dụng ưu đãi theo đối tượng.
- In hóa đơn dạng bảng hoàn chỉnh kèm thuế VAT 10%.
- Tự động in **Phiếu bảo hành** cho các mặt hàng đạt chuẩn:
  - Áo khoác: 12 tháng.
  - Phụ kiện: 6 tháng.
  - Giày dép: 3 tháng.
- Tự động ghi lại toàn bộ nội dung hóa đơn vào file `hoadon.txt`.

### 9. Xem danh sách Nhà cung cấp
- Quản lý thông tin các đối tác cung ứng hàng may mặc, giày dép uy tín.

---

## HƯỚNG DẪN CÀI ĐẶT & CHẠY

Mở Terminal tại thư mục đồ án:

```powershell
# 1. Biên dịch toàn bộ mã nguồn
javac *.java

# 2. Chạy ứng dụng chính
java Main

# 3. Chạy bộ 50 testcase kiểm thử tự động
java TestCases
```
