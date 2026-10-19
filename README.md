# HỆ THỐNG QUẢN LÝ CỬA HÀNG THỜI TRANG (JAVA OOP)

> **Đồ án môn Lập trình hướng đối tượng (OOP)**  
> Ứng dụng Console quản lý kho hàng, khách hàng, nhân viên và hóa đơn thanh toán với giao diện dạng bảng lưới Excel trực quan.

---

## MỤC LỤC
1. [Giao diện Menu chính](#1-giao-diện-menu-chính)
2. [Chi tiết trực quan các chức năng](#2-chi-tiết-trực-quan-các-chức-năng)
   - [Chức năng 1: Thêm sản phẩm (Tự sinh mã)](#chức-năng-1-thêm-sản-phẩm-tự-sinh-mã)
   - [Chức năng 2: Xóa sản phẩm](#chức-năng-2-xóa-sản-phẩm)
   - [Chức năng 3: Tìm kiếm sản phẩm](#chức-năng-3-tìm-kiếm-sản-phẩm-tìm-kiếm-liên-tục)
   - [Chức năng 4: Cập nhật giá sản phẩm](#chức-năng-4-cập-nhật-giá-sản-phẩm)
   - [Chức năng 5: Hiển thị tất cả sản phẩm trong kho](#chức-năng-5-hiển-thị-tất-cả-sản-phẩm-trong-kho)
   - [Chức năng 6: Xem chương trình khuyến mãi 10%](#chức-năng-6-xem-chương-trình-khuyến-mãi-10)
   - [Chức năng 7: Thêm Khách hàng VIP / Nhân viên](#chức-năng-7-thêm-khách-hàng-vip--nhân-viên)
   - [Chức năng 8: Thanh toán hóa đơn & In phiếu bảo hành](#chức-năng-8-thanh-toán-hóa-đơn--in-phiếu-bảo-hành)
   - [Chức năng 0: Lưu dữ liệu và thoát](#chức-năng-0-lưu-dữ-liệu-và-thoát)
3. [Cấu trúc OOP trong dự án](#3-cấu-trúc-oop-trong-dự-án)
4. [Hướng dẫn cài đặt & chạy](#4-hướng-dẫn-cài-đặt--chạy)

---

## 1. GIAO DIỆN MENU CHÍNH

Khi khởi động chương trình, dữ liệu từ `products.txt` sẽ tự động tải vào bộ nhớ và hiển thị menu điều khiển:

```text
<-----------------Quan Ly Cua Hang Thoi Trang----------------->
1. Them san pham
2. Xoa san pham
3. Tim kiem san pham
4. Cap nhat gia san pham
5. Hien thi tat ca san pham
6. Xem chuong trinh khuyen mai
7. Them khach hang VIP hoac Nhan vien
8. Thanh toan hoa don
0. Luu va thoat
Nhap lua chon cua ban: _
```

> **Lưu ý thao tác**: Sau khi hoàn tất mỗi chức năng, chương trình luôn dừng lại với dòng nhắc:
> `Nhan Enter de quay lai menu...` để người dùng tiện xem kết quả trước khi xóa màn hình về lại menu.

---

## 2. CHI TIẾT TRỰC QUAN CÁC CHỨC NĂNG

### Chức năng 1: Thêm sản phẩm (Tự sinh mã)
- Người dùng chỉ cần chọn loại sản phẩm, hệ thống **tự động sinh mã duy nhất** theo tiền tố:
  - Áo khoác: `AK001`, `AK002`, ...
  - Quần jean: `QJ001`, `QJ002`, ...
  - Áo thun: `AT001`, `AT002`, ...
  - Phụ kiện: `PK001`, `PK002`, ...
- Dữ liệu thêm mới được **tự động lưu ngay lập tức** vào file `products.txt`.

**Màn hình thực tế:**
```text
Nhap loai san pham (AoKhoac / QuanJean / PhuKien / AoThun): AoKhoac
Ma san pham duoc tao tu dong: AK002
Nhap ten san pham: Nike Windbreaker
Nhap gia san pham: 2450000
Ao co non khong? (true/false): true
Da them ao khoac thanh cong. Ma SP: AK002

Nhan Enter de quay lai menu...
```

---

### Chức năng 2: Xóa sản phẩm
- Nhập mã sản phẩm cần xóa khỏi kho.
- Hệ thống kiểm tra và cập nhật file lưu trữ tức thì.

**Màn hình thực tế:**
```text
Nhap ma san pham can xoa: AK002
Da xoa san pham co ma: AK002

Nhan Enter de quay lai menu...
```

---

### Chức năng 3: Tìm kiếm sản phẩm (Tìm kiếm liên tục)
- Nhập mã sản phẩm để tra cứu.
- Kết quả tìm thấy được đóng khung bảng Excel trực quan.
- Có vòng lặp hỏi người dùng muốn tìm tiếp (`1`) hay quay lại menu (`0`).

**Màn hình thực tế:**
```text
Nhap ma san pham can tim: AK001

Ket qua tim kiem:
+------------+----------+------------------------+-----------------+----------------------+
| Loai SP    | Ma SP    | Ten San Pham           | Gia Ban         | Thong Tin Rieng      |
+------------+----------+------------------------+-----------------+----------------------+
| AoKhoac    | AK001    | Adidas                 |   1.800.000 VND | Co non: Khong        |
+------------+----------+------------------------+-----------------+----------------------+

Ban co muon tim tiep khong? (1: Tim tiep / 0: Quay lai menu): 0

Nhan Enter de quay lai menu...
```

---

### Chức năng 4: Cập nhật giá sản phẩm
- Cập nhật giá mới theo mã sản phẩm.
- Có kiểm tra điều kiện giá bán phải $\ge 0$.

**Màn hình thực tế:**
```text
Nhap ma san pham can cap nhat: AK001
Nhap gia moi: 2100000
Da cap nhat gia san pham ma AK001 thanh 2.100.000 VND

Nhan Enter de quay lai menu...
```

---

### Chức năng 5: Hiển thị tất cả sản phẩm trong kho
- Đóng khung dạng bảng tính Excel.
- Đơn giá hiển thị phân cách 3 chữ số bằng dấu chấm chuẩn Việt Nam (`1.800.000 VND`).

**Màn hình thực tế:**
```text
=== DANH SACH SAN PHAM TRONG KHO ===
+------------+----------+------------------------+-----------------+----------------------+
| Loai SP    | Ma SP    | Ten San Pham           | Gia Ban         | Thong Tin Rieng      |
+------------+----------+------------------------+-----------------+----------------------+
| AoKhoac    | AK001    | Adidas                 |   1.800.000 VND | Co non: Khong        |
| AoThun     | AT001    | Polo                   |     350.000 VND | Size: L              |
| AoKhoac    | AK002    | Nike Windbreaker       |   2.450.000 VND | Co non: Co           |
+------------+----------+------------------------+-----------------+----------------------+
Tong so san pham trong kho: 3

Nhan Enter de quay lai menu...
```

---

### Chức năng 6: Xem chương trình khuyến mãi (10%)
- Bảng Excel hiển thị song song giá gốc và giá sau khi chiết khấu 10%.
- Giúp nhân viên và khách hàng dễ dàng đối chiếu giá.

**Màn hình thực tế:**
```text
=== CHUONG TRINH KHUYEN MAI: XEM TRUOC GIAM GIA 10% ===
+------------+----------+------------------------+-----------------+-----------+-----------------+
| Loai SP    | Ma SP    | Ten San Pham           | Gia Goc         | Giam Gia  | Gia Sau Giam    |
+------------+----------+------------------------+-----------------+-----------+-----------------+
| AoKhoac    | AK001    | Adidas                 |   1.800.000 VND |    10%    |   1.620.000 VND |
| AoThun     | AT001    | Polo                   |     350.000 VND |    10%    |     315.000 VND |
+------------+----------+------------------------+-----------------+-----------+-----------------+
Luu y: Muc giam gia se tu dong duoc ap dung khi xuat hoa don.

Nhan Enter de quay lai menu...
```

---

### Chức năng 7: Thêm Khách hàng VIP / Nhân viên
- Phục vụ cho chính sách ưu đãi tự động khi tính tiền:
  - Khách hàng VIP: Giảm **20%** tổng bill.
  - Nhân viên cửa hàng: Giảm **50%** tổng bill.

**Màn hình thực tế:**
```text
1. Them Khach hang VIP | 2. Them Nhan vien: 1
Nhap ho ten: Nguyen Van A
Nhap so dien thoai: 0912345678
Nhap ma the VIP: VIP888
Da them khach hang VIP thanh cong.

Nhan Enter de quay lai menu...
```

---

### Chức năng 8: Thanh toán hóa đơn & In phiếu bảo hành
- Nhập danh sách mã sản phẩm muốn mua (nhập `0` để kết thúc giỏ hàng).
- Nhập số điện thoại khách hàng:
  - Nếu là khách vãng lai (nhập `0`): Áp dụng giá gốc + VAT 10%.
  - Nếu là VIP hoặc Nhân viên: Tự động nhận diện và trừ chiết khấu (20% hoặc 50%).
- Xuất hóa đơn chuẩn bảng Excel và tự động in **Phiếu bảo hành dạng thẻ** (Áo khoác: 12 tháng, Phụ kiện: 6 tháng).

**Màn hình thực tế:**
```text
--- TIEN HANH THANH TOAN ---
Nhap ma san pham muon mua (hoac nhap '0' de dung): AK001
Da them Adidas vao gio hang.
Nhap ma san pham muon mua (hoac nhap '0' de dung): 0
Khach hang co tai khoan VIP/Nhan vien khong? Nhap SDT (hoac '0' neu la khach vang lai): 0912345678

+---------------------------------------------------------------------------------+
|                                HOA DON BAN HANG                                 |
+---------------------------------------------------------------------------------+
| Ma hoa don   : HD1792394349200                                                  |
| Khach hang   : Nguyen Van A                                                     |
| So dien thoai: 0912345678                                                       |
| Doi tuong    : KhachHangVIP                                                     |
+----+------------+----------+------------------------+---------------+-----------+
| STT| Loai SP    | Ma SP    | Ten San Pham           | Don Gia (VND) | VAT (10%) |
+----+------------+----------+------------------------+---------------+-----------+
| 1  | AoKhoac    | AK001    | Adidas                 |     1.800.000 |   180.000 |
+----+------------+----------+------------------------+---------------+-----------+
| Da ap dung giam gia cho doi tuong: KhachHangVIP                                 |
+---------------------------------------------------------------------------------+
| TONG THANH TOAN (DA TINH VAT VA GIAM GIA):                        1.440.000 VND |
+---------------------------------------------------------------------------------+

+-------------------------------------------------------+
|                   PHIEU BAO HANH                      |
+-------------------------------------------------------+
| Ten san pham      : Adidas                            |
| Ma san pham       : AK001                             |
| Thoi han bao hanh : 12 thang                          |
+-------------------------------------------------------+

Nhan Enter de quay lai menu...
```

---

### Chức năng 0: Lưu dữ liệu và thoát
- Ghi toàn bộ dữ liệu an toàn vào file `products.txt` và đóng chương trình.

**Màn hình thực tế:**
```text
Da luu du lieu. Thoat chuong trinh.
```

---

## 3. CẤU TRÚC OOP TRONG DỰ ÁN

| Nguyên lý | Áp dụng trong mã nguồn |
| :--- | :--- |
| **Kế thừa (Inheritance)** | `AoKhoac`, `QuanJean`, `AoThun`, `PhuKien` kế thừa từ `SanPhamThoiTrang`<br>`KhachHangVIP`, `NhanVien` kế thừa từ `Nguoi` |
| **Đóng gói (Encapsulation)** | Toàn bộ thuộc tính đặt `private`, truy xuất qua Getter/Setter, kiểm tra dữ liệu đầu vào |
| **Đa hình (Polymorphism)** | Triển khai interface `IGiamGia`, `IGiamGiaKhachHang`, `IBaoHanh` với các mức giảm giá và chế độ bảo hành riêng biệt |
| **Trừu tượng (Abstraction)** | Lớp trừu tượng `SanPhamThoiTrang` với các phương thức trừu tượng `getStringSaveFile()`, `getThongTinRieng()` |

---

## 4. HƯỚNG DẪN CÀI ĐẶT & CHẠY

Mở Terminal tại thư mục chứa dự án và thực thi lệnh:

```powershell
# 1. Biên dịch toàn bộ file
javac *.java

# 2. Khởi chạy ứng dụng
java Main
```
