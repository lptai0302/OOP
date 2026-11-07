# BÁO CÁO CHI TIẾT PHẦN 2: PHÂN CẤP CON NGƯỜI & ĐA HÌNH CHIẾT KHẤU

> **Phân công phụ trách:** Thành viên 2  
> **Chủ đề:** Cây thừa kế Con người (Khách hàng & Nhân viên), Đa hình Chiết khấu qua Interface & Quản lý Nhà cung cấp.

---

## 1. TỔNG QUAN CÁC LỚP PHỤ TRÁCH (8 LỚP & INTERFACES)
1. `Nguoi` (Lớp cơ sở - Base Class)
2. `KhachHang` (Kế thừa `Nguoi`)
3. `KhachHangVIP` (Kế thừa `KhachHang`, hiện thực `IGiamGiaKhachHang`)
4. `KhachHangThanThiet` (Kế thừa `KhachHang`, hiện thực `IGiamGiaKhachHang`)
5. `NhanVien` (Kế thừa `Nguoi`, hiện thực `IGiamGiaKhachHang`)
6. `NhanVienQuanLy` (Kế thừa `NhanVien`)
7. `NhaCungCap` (Lớp độc lập quản lý đối tác)
8. `IGiamGiaKhachHang` (Interface tính tỷ lệ chiết khấu)

---

## 2. NGUYÊN LÝ HƯỚNG ĐỐI TƯỢNG (OOP) CÀI ĐẶT TRONG PHẦN 2

### 2.1. Cây phân cấp kế thừa chuẩn mực (Inheritance Tree)
```text
           [Nguoi]
          /       \
   [KhachHang]    [NhanVien]
     /      \          |
 [VIP]  [ThanThiet] [NhanVienQuanLy]
```
- Lớp gốc `Nguoi`: Chứa các thuộc tính định danh cá nhân căn bản: `name` (Họ tên) và `numberPhone` (Số điện thoại).
- Nhánh `KhachHang`: Quản lý khách hàng của cửa hàng.
  - `KhachHangVIP`: Có mã thẻ VIP (`cardCodeVIP`), được hưởng mức chiết khấu cao nhất cho khách hàng.
  - `KhachHangThanThiet`: Có điểm thưởng tích lũy (`diemTichLuy`), tích điểm qua từng lần mua hàng.
- Nhánh `NhanVien`: Quản lý nhân sự của cửa hàng.
  - `NhanVien`: Có mã nhân viên (`staffID`), hưởng chiết khấu mua hàng nội bộ.
  - `NhanVienQuanLy`: Kế thừa toàn bộ quyền lợi của nhân viên và bổ sung thêm thuộc tính `phuCapTrachNhiem` (Phụ cấp quản lý).

### 2.2. Đa hình chiết khấu qua Interface `IGiamGiaKhachHang`
Interface `IGiamGiaKhachHang` định nghĩa phương thức:
```java
public interface IGiamGiaKhachHang {
    double getDiscountRate();
}
```
Mỗi lớp đối tượng tự quyết định mức chiết khấu riêng biệt thông qua việc Override:
- **`KhachHangVIP`**: Chiết khấu **20%** (`getDiscountRate() { return 0.20; }`).
- **`KhachHangThanThiet`**: Chiết khấu **10%** (`getDiscountRate() { return 0.10; }`).
- **`NhanVien`**: Chiết khấu nội bộ **50%** (`getDiscountRate() { return 0.50; }`).
- **`NhanVienQuanLy`**: Kế thừa mức chiết khấu **50%** của nhân viên.
- **Khách hàng thông thường**: Không implement interface -> Chiết khấu **0%**.

### 2.3. Lớp đối tác `NhaCungCap`
- Quản lý các đơn vị may mặc, nhà máy cung ứng sản phẩm cho cửa hàng (`maNCC`, `tenNCC`, `diaChi`, `soDienThoai`).
- Đóng gói đầy đủ phương thức hiển thị thông tin dạng dòng bảng chuẩn chỉnh, phục vụ Menu chức năng 9.

---

## 3. BỘ CÂU HỎI VẤN ĐÁP THẦY HIẾU & CÂU TRẢ LỜI MẪU (10 ĐIỂM)

### Câu hỏi 1: Tại sao cả Khách hàng và Nhân viên đều có thể mua hàng và được giảm giá? Bạn cài đặt tính năng này bằng kỹ thuật gì?
- **Trả lời:** Em sử dụng kỹ thuật Đa hình thông qua Interface `IGiamGiaKhachHang`. Dù là `KhachHangVIP` hay `NhanVien`, khi thực hiện thanh toán, lớp `HoaDon` chỉ cần giữ một biến tham chiếu kiểu `Nguoi person`. Nếu đối tượng đó `instanceof IGiamGiaKhachHang`, chương trình sẽ gọi phương thức `getDiscountRate()` để lấy tỷ lệ giảm giá tương ứng mà không cần phải viết hàng loạt lệnh if-else kiểm tra từng loại đối tượng.

### Câu hỏi 2: Tại sao `NhanVienQuanLy` lại kế thừa `NhanVien` mà không kế thừa trực tiếp từ `Nguoi`?
- **Trả lời:** Vì Quản lý bản chất vẫn là một Nhân viên (Quan hệ IS-A), có đầy đủ mã nhân viên (`staffID`), các chức năng làm việc và chế độ ưu đãi giảm giá 50% của nhân viên. Kế thừa `NhanVien` giúp tái sử dụng mã nguồn và chỉ cần mở rộng thêm thuộc tính đặc thù là `phuCapTrachNhiem`.

### Câu hỏi 3: Trong phương thức khởi tạo và setter của lớp `Nguoi`, tại sao lại có thao tác `.replace(",", " ")`?
- **Trả lời:** Thao tác này nhằm loại bỏ dấu phẩy trong họ tên hoặc dữ liệu người dùng nhập vào. Do hệ thống sử dụng định dạng file CSV (phân cách bằng dấu phẩy) để lưu danh sách người dùng vào `users.txt`, nếu họ tên chứa dấu phẩy sẽ làm sai lệch số lượng cột khi đọc file, dẫn đến lỗi đọc dữ liệu. Việc khử dấu phẩy là biện pháp phòng vệ dữ liệu (Data Sanitization).
