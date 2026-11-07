# BÁO CÁO CHI TIẾT PHẦN 4: ĐIỀU PHỐI HỆ THỐNG, DEMO CRUD & KỊCH BẢN VẤN ĐÁP

> **Phân công phụ trách:** Thành viên 4 (Trưởng nhóm / Người thuyết trình & Demo chính)  
> **Chủ đề:** Lớp điều phối hệ thống (Controller / Facade), Luồng chạy Console CRUD, Quy trình Thanh toán & Kịch bản vấn đáp bảo vệ đồ án 10 điểm.

---

## 1. TỔNG QUAN CÁC LỚP PHỤ TRÁCH (3 LỚP)
1. `QuanLyCuaHang` (Lớp điều phối trung tâm - Controller / Facade)
2. `Main` (Lớp giao diện dòng lệnh Console - View & Dispatcher)
3. `TestCases` (Lớp bộ kiểm thử tự động 73 testcase)

---

## 2. NGUYÊN LÝ HƯỚNG ĐỐI TƯỢNG (OOP) CÀI ĐẶT TRONG PHẦN 4

### 2.1. Lớp điều phối `QuanLyCuaHang` (Mô hình Facade / Controller)
- Đóng vai trò là cầu nối duy nhất giữa giao diện người dùng (`Main`) và các kho dữ liệu bên dưới (`DanhSachSanPham`, `DanhSachKhachHang`, `ArrayList<NhaCungCap>`).
- Gom nhóm các nghiệp vụ phức tạp:
  - Khởi tạo hệ thống tự động: Đọc dữ liệu từ file khi mở app, lưu dữ liệu khi có thay đổi.
  - Khởi tạo mặc định danh sách các Nhà cung cấp may mặc uy tín (Việt Tiến, An Phước, Biti's).
  - Tự động đồng bộ file mỗi khi có thao tác thêm, sửa, xóa sản phẩm hoặc người dùng.

### 2.2. Giao diện dòng lệnh Menu trực quan & Toàn diện chức năng (CRUD)
Hệ thống cung cấp Menu 10 chức năng đáp ứng 100% barem chấm điểm của Giảng viên:
```text
<-----------------Quan Ly Cua Hang Thoi Trang----------------->
1. Them san pham                     -> Thêm (Create)
2. Xoa san pham                      -> Xóa (Delete)
3. Tim kiem san pham                 -> Tìm kiếm (Read/Search theo mã hoặc tên)
4. Sua thong tin san pham            -> Sửa (Update tên, giá, thuộc tính riêng)
5. Hien thi tat ca san pham          -> Xem (Read toàn bộ dạng bảng lưới)
6. Xem chuong trinh khuyen mai       -> Xem trước giá sau giảm 10%
7. Quan ly Khach hang / Nhan vien    -> Đầy đủ CRUD cho đối tượng con người
8. Thanh toan hoa don & Bao hanh     -> Nghiệp vụ bán hàng & xuất file
9. Xem danh sach Nha cung cap        -> Đối tác may mặc
0. Luu va thoat                      -> Thoát an toàn
```

### 2.3. Quy trình Bán hàng & In hóa đơn chuyên nghiệp
1. Khách hàng chọn nhiều mặt hàng cùng lúc, hỗ trợ nhập số lượng mua (cộng dồn tự động nếu trùng mã).
2. Nhập số điện thoại khách hàng: Hệ thống tự động tra cứu để áp dụng tỷ lệ chiết khấu (VIP giảm 20%, Thân thiết giảm 10%, Nhân viên giảm 50%).
3. Tự động tính thuế VAT 10% và tổng thanh toán.
4. Xuất hóa đơn định dạng bảng 95 ký tự chuẩn xác.
5. Kiểm tra sản phẩm có hỗ trợ bảo hành (`instanceof IBaoHanh`) để in kèm Phiếu bảo hành chính hãng.
6. Tự động ghi toàn bộ nội dung hóa đơn và phiếu bảo hành vào file `hoadon.txt`.

---

## 3. KỊCH BẢN DEMO TỪNG BƯỚC CHO GIẢNG VIÊN (5 PHÚT HOÀN HẢO)

- **Bước 1 (Hiển thị danh sách - Phím 5):** Cho Thầy xem bảng sản phẩm với định dạng kẻ viền như Excel và tiền tệ Việt Nam chuẩn (`1.800.000 VND`).
- **Bước 2 (Thêm sản phẩm tự sinh mã - Phím 1):** Chọn thêm một Áo khoác hoặc Giày dép. Nhấn mạnh với Thầy hệ thống **tự động sinh mã** (`AK002`, `GD002`) mà không cần người dùng tự gõ mã bằng tay.
- **Bước 3 (Tìm kiếm kép - Phím 3):** Demo tìm kiếm theo mã (`AK001`) và tìm kiếm theo tên/từ khóa (`Polo` hoặc `Hunter`).
- **Bước 4 (Sửa thông tin - Phím 4):** Demo sửa giá và sửa thuộc tính riêng của sản phẩm (ví dụ sửa Áo khoác có nón thành không nón).
- **Bước 5 (Quản lý khách hàng - Phím 7):** Hiển thị danh sách khách hàng VIP, nhân viên, thực hiện thêm và sửa tên theo SĐT.
- **Bước 6 (Bán hàng & In hóa đơn - Phím 8):** Mua 2 mặt hàng (ví dụ Áo khoác + Áo thun), nhập SĐT khách hàng VIP (`0912345678`). Cho Thầy xem hóa đơn tự động giảm 20% và in kèm Phiếu bảo hành 12 tháng.
- **Bước 7 (Minh chứng kiểm thử tự động):** Chạy `java TestCases` cho Thầy xem kết quả 73/73 testcase vượt qua tuyệt đối.

---

## 4. TỔNG HỢP 3 CÂU HỎI VẤN ĐÁP KINH ĐIỂN CỦA THẦY HIẾU

### Câu hỏi 1: Đồ án của nhóm em có bao nhiêu lớp đối tượng, có đáp ứng đủ yêu cầu tối thiểu 15 lớp của thầy không?
- **Trả lời:** Dạ thưa Thầy, đồ án của nhóm em xây dựng tổng cộng **20 lớp đối tượng và 3 interface** (vượt xa tiêu chuẩn tối thiểu 15 lớp). Bao gồm:
  - 7 lớp sản phẩm thời trang (`SanPhamThoiTrang`, `AoKhoac`, `AoThun`, `QuanJean`, `PhuKien`, `VayDam`, `GiayDep`).
  - 6 lớp đối tượng con người (`Nguoi`, `KhachHang`, `KhachHangVIP`, `KhachHangThanThiet`, `NhanVien`, `NhanVienQuanLy`).
  - 4 lớp quản lý và danh sách (`DanhSachSanPham`, `DanhSachKhachHang`, `QuanLyCuaHang`, `NhaCungCap`).
  - 2 lớp hóa đơn (`ChiTietHoaDon`, `HoaDon`).
  - 1 lớp thực thi chính (`Main`) và 3 Interface nghiệp vụ (`IBaoHanh`, `IGiamGia`, `IGiamGiaKhachHang`).

### Câu hỏi 2: Đa hình được thể hiện ở những vị trí nào quan trọng nhất trong mã nguồn?
- **Trả lời:** Đa hình trong đồ án được cài đặt ở 3 vị trí cốt lõi:
  1. **Đa hình tính giá sản phẩm:** Lớp cha `SanPhamThoiTrang` định nghĩa phương thức `calculateAfterDiscount(double)`.
  2. **Đa hình bảo hành qua Interface `IBaoHanh`:** Chỉ có `AoKhoac`, `PhuKien`, `GiayDep` hiện thực interface này với thời hạn bảo hành khác nhau (12, 6, 3 tháng). Khi duyệt in hóa đơn, chương trình gọi đa hình `printWarrantyCard()`.
  3. **Đa hình chiết khấu qua Interface `IGiamGiaKhachHang`:** Khi thanh toán, hóa đơn giữ tham chiếu lớp cha `Nguoi person`. Tùy theo đối tượng thực tế truyền vào là `KhachHangVIP` (20%), `KhachHangThanThiet` (10%) hay `NhanVien` (50%), phương thức `getDiscountRate()` sẽ tự động tính đúng mức giảm giá.

### Câu hỏi 3: Nếu chương trình bị tắt đột ngột thì dữ liệu có bị mất không? Nhóm xử lý việc lưu trữ như thế nào?
- **Trả lời:** Dạ thưa Thầy, dữ liệu không bao giờ bị mất vì nhóm cài đặt cơ chế tự động đồng bộ tức thì (Auto-save). Bất kỳ khi nào có thao tác Thêm, Sửa, hoặc Xóa thành công, phương thức `saveDataIntoFile()` đều được gọi ngay lập tức để ghi đè dữ liệu mới nhất vào `products.txt` và `users.txt` bằng chuẩn mã hóa `UTF-8`. Đồng thời mọi hóa đơn sau khi thanh toán đều được lưu vết vĩnh viễn vào `hoadon.txt`.
