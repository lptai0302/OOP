# BÁO CÁO CHI TIẾT PHẦN 1: MÔ HÌNH SẢN PHẨM & ĐA HÌNH BẢO HÀNH

> **Phân công phụ trách:** Thành viên 1  
> **Chủ đề:** Cây thừa kế Sản phẩm, Tính Trừu tượng (Abstract), Tính Đa hình (Polymorphism) & Interface Bảo hành.

---

## 1. TỔNG QUAN CÁC LỚP PHỤ TRÁCH (8 LỚP & INTERFACES)
1. `SanPhamThoiTrang` (Lớp trừu tượng - Abstract Class)
2. `AoKhoac` (Kế thừa `SanPhamThoiTrang`, hiện thực `IBaoHanh`)
3. `AoThun` (Kế thừa `SanPhamThoiTrang`)
4. `QuanJean` (Kế thừa `SanPhamThoiTrang`)
5. `PhuKien` (Kế thừa `SanPhamThoiTrang`, hiện thực `IBaoHanh`)
6. `VayDam` (Kế thừa `SanPhamThoiTrang`)
7. `GiayDep` (Kế thừa `SanPhamThoiTrang`, hiện thực `IBaoHanh`)
8. `IBaoHanh`, `IGiamGia` (Interfaces)

---

## 2. NGUYÊN LÝ HƯỚNG ĐỐI TƯỢNG (OOP) CÀI ĐẶT TRONG PHẦN 1

### 2.1. Tính trừu tượng (Abstraction)
- **Lớp trừu tượng `SanPhamThoiTrang`**: Định nghĩa khung mẫu chung cho toàn bộ mặt hàng trong shop thời trang (`Type`, `MaSP`, `TenSP`, `GiaBan`). Lớp này không cho phép khởi tạo trực tiếp (`new SanPhamThoiTrang()`), buộc phải thông qua các lớp con cụ thể.
- **Phương thức trừu tượng (Abstract Methods)**:
  - `public abstract String getThongTinRieng()`: Mỗi loại sản phẩm có thông tin đặc trưng riêng (Áo khoác có nón hay không, Áo thun có size S/M/L, Quần jean có form dáng, Phụ kiện có chất liệu kim loại/da...).
  - `public abstract String getStringSaveFile()`: Mỗi loại sản phẩm tự định nghĩa chuỗi định dạng lưu xuống file `products.txt`.

### 2.2. Tính kế thừa (Inheritance)
- 6 lớp con: `AoKhoac`, `AoThun`, `QuanJean`, `PhuKien`, `VayDam`, `GiayDep` đều sử dụng từ khóa `extends SanPhamThoiTrang`.
- Tái sử dụng toàn bộ thuộc tính cha thông qua hàm thiết lập `super(Type, MaSP, TenSP, GiaSP)` và các phương thức dùng chung như `VATcalculation()`, `calculateAfterDiscount(double)`.

### 2.3. Tính đóng gói (Encapsulation)
- Toàn bộ thuộc tính đều để phạm vi truy cập `private`.
- Truy xuất và sửa đổi thông qua `getter` và `setter` có kèm logic kiểm tra (Validation):
  - `GiaBan >= 0`.
  - Tự động thay thế dấu phẩy `,` thành khoảng trắng để chống lỗi định dạng file CSV.
  - Chuẩn hóa size áo thun thành chữ in hoa `S, M, L, XL`. Size giày dép giới hạn từ 35 đến 45.

### 2.4. Tính đa hình & Interface (Polymorphism & Interface)
- **Interface `IBaoHanh`**: Khai báo 2 phương thức `printWarrantyCard()` và `getWarrantyPeriod()`.
- **Cài đặt đa hình nghiệp vụ**:
  - `AoKhoac`: Có bảo hành 12 tháng.
  - `PhuKien`: Có bảo hành 6 tháng.
  - `GiayDep`: Có bảo hành 3 tháng.
  - `AoThun`, `QuanJean`, `VayDam`: Không implement `IBaoHanh` vì quần áo thông thường không áp dụng chính sách bảo hành.
- Khi thanh toán, hệ thống duyệt danh sách và dùng toán tử `instanceof IBaoHanh` để tự động in phiếu bảo hành tương ứng.

---

## 3. BỘ CÂU HỎI VẤN ĐÁP THẦY HIẾU & CÂU TRẢ LỜI MẪU (10 ĐIỂM)

### Câu hỏi 1: Tại sao `SanPhamThoiTrang` lại là Abstract Class mà không phải là Class thông thường?
- **Trả lời:** Trong thực tế, khách hàng không bao giờ mua một "Sản phẩm thời trang" chung chung, mà họ mua một cái "Áo khoác", "Áo thun", hoặc "Giày dép" cụ thể. Vì vậy lớp cha được thiết kế trừu tượng để làm khuôn mẫu chuẩn, ngăn việc tạo đối tượng vô nghĩa và ép buộc các lớp con phải hiện thực hàm `getThongTinRieng()` và `getStringSaveFile()`.

### Câu hỏi 2: Sự khác nhau giữa việc kế thừa lớp cha và cài đặt Interface trong thiết kế của bạn là gì?
- **Trả lời:**
  - Kế thừa lớp cha (`SanPhamThoiTrang`) thể hiện quan hệ bản chất **"IS-A"** (Áo khoác là một sản phẩm thời trang), giúp tái sử dụng mã nguồn và quản lý danh mục.
  - Cài đặt interface (`IBaoHanh`) thể hiện năng lực/hành vi **"CAN-DO"** (Sản phẩm này có khả năng bảo hành). Không phải mọi sản phẩm thời trang đều bảo hành được, chỉ những mặt hàng đủ tiêu chuẩn mới cài đặt `IBaoHanh`.

### Câu hỏi 3: Nếu muốn thêm một loại sản phẩm mới như "DongHo" (Đồng hồ) thì phải sửa những đâu?
- **Trả lời:**
  1. Tạo class `DongHo extends SanPhamThoiTrang implements IBaoHanh`.
  2. Cài đặt các hàm bắt buộc: `getThongTinRieng()`, `getStringSaveFile()`, `printWarrantyCard()`, `getWarrantyPeriod()`.
  3. Bổ sung tiền tố sinh mã `DH` trong `DanhSachSanPham.taoMaTuDong()`.
  4. Bổ sung 1 nhánh trong Menu thêm sản phẩm và hàm đọc file. Code hoàn toàn tuân thủ nguyên lý Mở/Đóng (Open/Closed Principle).
