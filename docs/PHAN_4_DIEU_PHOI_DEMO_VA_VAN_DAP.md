# BÁO CÁO CHI TIẾT PHẦN 4: ĐIỀU PHỐI HỆ THỐNG, DEMO CRUD & VẤN ĐÁP
### HƯỚNG DẪN HỌC & BẢO VỆ VẤN ĐÁP ĐỒ ÁN OOP (ĐIỂM 10/10)

> **Phân công thành viên:** Thành viên 4 (Trưởng nhóm / Điều phối Demo chính)  
> **Các lớp phụ trách (3 lớp):** `QuanLyCuaHang`, `Main`, `TestCases`.  
> **Nhiệm vụ cốt lõi:** Nắm vững kiến trúc lớp điều phối trung tâm (Facade Pattern), toàn bộ vòng lặp giao diện Console Menu 10 chức năng (CRUD), quy trình bán hàng - thanh toán - in phiếu bảo hành và kịch bản vấn đáp toàn diện đạt điểm 10.

---

## 1. MÔ HÌNH KIẾN TRÚC ĐIỀU PHỐI HỆ THỐNG (FACADE PATTERN)

```text
                            [Người dùng / Console]
                                      |
                                      v
                                 [Main.java] (Giao diện View)
                                      |
                                      v
                             [QuanLyCuaHang.java] (Controller / Facade)
                                 /    |     \
                                /     |      \
                               v      v       v
                     [DanhSachSP]  [DanhSachKH] [ArrayList<NhaCungCap>]
                            |         |                 |
                            v         v                 v
                      products.txt  users.txt     (Nhà máy may mặc)
```

- **Mô hình Facade / Controller (`QuanLyCuaHang`):** Gom toàn bộ các kho dữ liệu rời rạc (sản phẩm, khách hàng, nhà cung cấp) vào một đầu mối duy nhất. Lớp `Main` không cần tương tác trực tiếp với file hay mảng con, chỉ cần gọi qua `store`.
- **Cơ chế Tự động Đồng bộ (Auto-save):** Mọi thao tác Thêm, Sửa, Xóa đều tự động kích hoạt `saveDataIntoFile()`, đảm bảo dữ liệu luôn được ghi tức thì xuống đĩa cứng.

---

## 2. GIẢI THÍCH CHI TIẾT TỪNG ĐOẠN CODE CỐT LÕI

### Đoạn 1: Lớp điều phối trung tâm `QuanLyCuaHang`
```java
public class QuanLyCuaHang {
    private DanhSachSanPham dsSanPham;
    private DanhSachKhachHang dsKhachHang;
    private ArrayList<NhaCungCap> dsNhaCungCap;

    public QuanLyCuaHang() {
        this.dsSanPham = new DanhSachSanPham();
        this.dsKhachHang = new DanhSachKhachHang();
        this.dsNhaCungCap = new ArrayList<>();
        khoiTaoNhaCungCapMacDinh();
    }

    public void addProduct(SanPhamThoiTrang product) {
        if (dsSanPham.them(product)) {
            saveDataIntoFile();
        }
    }

    public void removeProduct(String MaSP) {
        if (dsSanPham.xoa(MaSP)) {
            saveDataIntoFile();
            System.out.println("Da xoa san pham co ma: " + MaSP);
        } else {
            System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
        }
    }

    public void saveDataIntoFile() {
        dsSanPham.ghiVaoFile("products.txt");
        dsKhachHang.ghiVaoFile("users.txt");
    }

    public void loadDataFromFile(String fileName) {
        dsSanPham.docTuFile(fileName);
        dsKhachHang.docTuFile("users.txt");
    }
}
```
- **Giải thích từng dòng code:**
  - `private DanhSachSanPham dsSanPham; private DanhSachKhachHang dsKhachHang;`: Đóng gói các lớp mảng đối tượng thành các thuộc tính riêng biệt của hệ thống cửa hàng.
  - `khoiTaoNhaCungCapMacDinh()`: Nạp sẵn danh mục đối tác may mặc lớn (Việt Tiến, An Phước, Biti's) ngay khi khởi động.
  - `addProduct(...)` & `removeProduct(...)`: Kiểm tra kết quả thực hiện từ lớp danh sách; nếu thành công (`true`) thì lập tức gọi `saveDataIntoFile()` để đồng bộ file ngay. Người dùng không sợ mất dữ liệu khi mất điện hay tắt máy đột ngột.
  - `loadDataFromFile(...)`: Đọc toàn bộ kho hàng và danh sách người dùng khi mở ứng dụng.

---

### Đoạn 2: Vòng lặp Menu chính trong `Main.java`
```java
    public static void main(String[] args) {
        QuanLyCuaHang store = new QuanLyCuaHang();
        store.loadDataFromFile("products.txt");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            inMenu();
            System.out.print("Nhap lua chon cua ban: ");
            String input = scanner.nextLine().trim();
            int choice = -1;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le!");
                continue;
            }

            switch (choice) {
                case 1: // Them san pham
                case 2: // Xoa san pham
                case 3: // Tim kiem san pham (theo ma hoac theo ten)
                case 4: // Sua san pham (ten, gia, thuoc tinh rieng)
                case 5: // Hien thi tat ca san pham
                case 6: // Xem khuyen mai 10%
                case 7: // Quan ly Khach hang / Nhan vien (CRUD)
                case 8: // Thanh toan hoa don & In bao hanh
                case 9: // Xem danh sach Nha cung cap
                case 0:
                    store.saveDataIntoFile();
                    System.out.println("Da luu du lieu an toan. Thoat chuong trinh.");
                    return;
            }
        }
    }
```
- **Giải thích từng dòng code:**
  - Bọc trong vòng lặp vô hạn `while (true)`: Đảm bảo chương trình chạy liên tục cho đến khi người dùng chủ động chọn `0. Luu va thoat`.
  - Đọc dữ liệu bằng `scanner.nextLine().trim()` kết hợp `Integer.parseInt`: Tránh triệt để lỗi "trôi lệnh" (Skip line) khét tiếng của `scanner.nextInt()` trong Java khi nhấn Enter.
  - Khối `try-catch (NumberFormatException)`: Ngăn chặn chương trình bị crash khi người dùng vô tình nhập chữ cái thay vì số.

---

### Đoạn 3: Menu 4 - Chức năng Sửa toàn diện (Tên, Giá, Thuộc tính riêng)
```java
    if (editOpt.equals("4")) {
        if (oldSp instanceof AoKhoac) {
            System.out.println("Ao co non khong? (1: Co / 0: Khong): ");
            boolean hasHat = scanner.nextLine().trim().equals("1");
            ((AoKhoac) oldSp).setHasHat(hasHat);
        } else if (oldSp instanceof QuanJean) {
            System.out.println("Nhap kieu dang moi: ");
            String st = scanner.nextLine().trim().replace(",", " ");
            ((QuanJean) oldSp).setStyle(st);
        } else if (oldSp instanceof AoThun) {
            System.out.println("Nhap kich co moi (S/M/L/XL): ");
            String sz = scanner.nextLine().trim();
            ((AoThun) oldSp).setSize(sz);
        } else if (oldSp instanceof GiayDep) {
            System.out.println("Nhap size giay moi (35 - 45): ");
            int sg = Integer.parseInt(scanner.nextLine().trim());
            ((GiayDep) oldSp).setSizeGiay(sg);
        }
        store.saveDataIntoFile();
    }
```
- **Giải thích từng dòng code:**
  - `oldSp instanceof AoKhoac`: Toán tử kiểm tra kiểu thực tế của đối tượng tại runtime.
  - `((AoKhoac) oldSp)`: Kỹ thuật **Downcasting (Ép kiểu xuống)** từ lớp cha `SanPhamThoiTrang` về đúng lớp con cụ thể để gọi được các phương thức riêng biệt (`setHasHat`, `setSize`, `setStyle`, `setSizeGiay`).
  - Cập nhật xong gọi ngay `store.saveDataIntoFile()`.

---

### Đoạn 4: Menu 8 - Quy trình Thanh toán Hóa đơn & In Phiếu bảo hành
```java
    HoaDon newbill = new HoaDon(newBillCode, null);
    // Vòng lặp cho phép khách chọn nhiều sản phẩm và số lượng...
    SanPhamThoiTrang product = store.getProductByCode(pCode);
    newbill.addProduct(product, sl);

    // Nhập số điện thoại tra cứu đối tượng:
    Nguoi person = store.getVIPOrStaffByPhone(sdt);
    newbill.setPerson(person);

    // In hóa đơn ra màn hình và lưu file:
    newbill.displayBill();
    newbill.xuatHoaDonRaFile("hoadon.txt");
```
```java
    // Bên trong HoaDon.java:
    public void printAllWarrantyCards() {
        for (ChiTietHoaDon ct : chiTietList) {
            SanPhamThoiTrang sp = ct.getSanPham();
            if (sp instanceof IBaoHanh) {
                ((IBaoHanh) sp).printWarrantyCard();
            }
        }
    }
```
- **Giải thích từng dòng code:**
  - `newbill.setPerson(person)`: Gắn đối tượng khách hàng vào hóa đơn. Nếu là Khách VIP tự động chiết khấu 20%, nếu là Khách thân thiết giảm 10%, nếu là Nhân viên giảm 50%.
  - `sp instanceof IBaoHanh`: Duyệt danh sách các món hàng đã mua trong đơn; chỉ những món hàng cài đặt interface bảo hành mới gọi đa hình `printWarrantyCard()`.
  - `newbill.xuatHoaDonRaFile("hoadon.txt")`: Tự động append lịch sử hóa đơn hoàn chỉnh vào file văn bản để phục vụ đối soát kế toán.

---

## 3. KỊCH BẢN DEMO ĐỒ ÁN TRƯỚC GIẢNG VIÊN (7 BƯỚC ĐẠT ĐIỂM 10)

1. **Bước 1 (Giới thiệu cấu trúc - 30 giây):** Thưa Thầy, đồ án nhóm em có tổng cộng 20 lớp đối tượng và 3 interface, tổ chức theo mô hình phân tầng: Model Sản phẩm, Model Con người, Lớp mảng quản lý, Lớp điều phối Facade và View Console.
2. **Bước 2 (Xem danh sách sản phẩm - Phím 5):** Mở bảng sản phẩm. Nhấn mạnh việc đóng khung lưới như Excel và định dạng tiền tệ Việt Nam chuẩn dấu chấm (`1.800.000 VND`).
3. **Bước 3 (Thêm sản phẩm tự sinh mã - Phím 1):** Thêm một sản phẩm mới (ví dụ Giày thể thao). Cho Thầy thấy hệ thống **tự động sinh mã `GD002`** dựa trên tiền tố loại hàng và mã lớn nhất hiện có.
4. **Bước 4 (Tìm kiếm kép - Phím 3):** Demo tìm kiếm theo mã (`AK001`) và tìm kiếm gần đúng theo từ khóa tên (`Hunter` hoặc `Polo`).
5. **Bước 5 (Sửa thuộc tính riêng - Phím 4):** Chọn sản phẩm vừa thêm và sửa thuộc tính đặc thù (sửa size giày từ 40 lên 42). Mở file `products.txt` cho Thầy thấy file được cập nhật ngay lập tức.
6. **Bước 6 (Bán hàng đa hình & In bảo hành - Phím 8):** Mua Áo khoác (`AK001`, SL: 2) và Áo thun (`AT001`, SL: 1). Nhập SĐT VIP (`0912345678`). Cho Thầy xem bảng hóa đơn tự động tính VAT 10%, giảm giá 20% cho VIP và **tự động in Phiếu bảo hành 12 tháng** cho Áo khoác. Mở file `hoadon.txt` xem nhật ký đơn hàng.
7. **Bước 7 (Minh chứng kiểm thử tự động):** Chạy `java TestCases` trên Terminal để chứng minh 73/73 testcase kiểm thử logic, đa hình và biên ngoại lệ đều PASS 100%.

---

## 4. BỘ 10 CÂU HỎI VẤN ĐÁP KINH ĐIỂN CỦA THẦY HIẾU & CÂU TRẢ LỜI ĐẠT 10 ĐIỂM

1. **Thầy hỏi: Đồ án của nhóm có đủ 15 lớp theo yêu cầu không? Kể tên?**  
   *Trả lời:* Dạ thưa Thầy, nhóm em có **20 lớp và 3 interface**. Gồm: 7 lớp sản phẩm (`SanPhamThoiTrang`, `AoKhoac`, `AoThun`, `QuanJean`, `PhuKien`, `VayDam`, `GiayDep`), 6 lớp con người (`Nguoi`, `KhachHang`, `KhachHangVIP`, `KhachHangThanThiet`, `NhanVien`, `NhanVienQuanLy`), 4 lớp quản lý (`DanhSachSanPham`, `DanhSachKhachHang`, `QuanLyCuaHang`, `NhaCungCap`), 2 lớp hóa đơn (`ChiTietHoaDon`, `HoaDon`), 1 lớp `Main` và 3 interface (`IBaoHanh`, `IGiamGia`, `IGiamGiaKhachHang`).

2. **Thầy hỏi: Tại sao lớp `SanPhamThoiTrang` lại là abstract?**  
   *Trả lời:* Vì trong thực tế không có sản phẩm nào chung chung là "Sản phẩm thời trang". Lớp cha là abstract để đóng vai trò khuôn mẫu chung, ngăn chặn việc tạo đối tượng tùy tiện bằng `new`, và bắt buộc các lớp con phải override `getThongTinRieng()` và `getStringSaveFile()`.

3. **Thầy hỏi: Đa hình được thể hiện ở đâu trong mã nguồn?**  
   *Trả lời:* Thể hiện ở 3 vị trí then chốt: (1) Gọi phương thức trừu tượng qua tham chiếu lớp cha `SanPhamThoiTrang`, (2) Đa hình bảo hành qua `IBaoHanh.printWarrantyCard()`, (3) Đa hình chiết khấu đối tượng qua `IGiamGiaKhachHang.applyDiscount()`.

4. **Thầy hỏi: Lớp mảng danh sách đối tượng trong đồ án là gì?**  
   *Trả lời:* Nhóm đã xây dựng 2 lớp mảng chuyên biệt là `DanhSachSanPham` và `DanhSachKhachHang`. Chúng đóng gói `ArrayList` bên trong, cung cấp đầy đủ các phương thức nghiệp vụ CRUD, tự sinh mã và đọc/ghi file.

5. **Thầy hỏi: Kỹ thuật ép kiểu Downcasting được dùng ở đoạn nào?**  
   *Trả lời:* Dùng ở chức năng sửa sản phẩm và in bảo hành: `((AoKhoac) oldSp).setHasHat(hasHat)` và `((IBaoHanh) sp).printWarrantyCard()`, sau khi đã kiểm tra an toàn bằng `instanceof`.

6. **Thầy hỏi: Nhóm xử lý lỗi font tiếng Việt khi đọc/ghi file văn bản như thế nào?**  
   *Trả lời:* Nhóm sử dụng `InputStreamReader` và `OutputStreamWriter` kết hợp chỉ định tường minh bảng mã `StandardCharsets.UTF_8` cùng luồng đệm `BufferedReader`/`BufferedWriter`.

7. **Thầy hỏi: Biến `tongSoHoaDon` là static có tác dụng gì?**  
   *Trả lời:* Biến static thuộc về lớp `HoaDon`, được lưu tại một vùng nhớ duy nhất và chia sẻ chung cho toàn bộ các hóa đơn sinh ra, giúp thống kê chính xác tổng số đơn hàng đã hoàn tất trong phiên làm việc.

8. **Thầy hỏi: Tại sao trong hàm tạo và setter lại có lệnh `.replace(",", " ")`?**  
   *Trả lời:* Đây là biện pháp phòng vệ dữ liệu (Data Sanitization). Vì file lưu trữ theo định dạng CSV phân tách bằng dấu phẩy, nếu tên sản phẩm hay họ tên có dấu phẩy sẽ làm vỡ cấu trúc cột khi đọc file.

9. **Thầy hỏi: Nếu có 2 sản phẩm cùng mã được quét vào giỏ hàng thì hệ thống xử lý sao?**  
   *Trả lời:* Lớp `HoaDon` sẽ duyệt giỏ hàng bằng hàm `timChiTietTheoMa(code)`. Nếu đã có, hệ thống gọi `ct.tangSoLuong(soLuongMoi)` để cộng dồn số lượng chứ không tạo thêm dòng mới, đúng với nghiệp vụ siêu thị.

10. **Thầy hỏi: Nếu ứng dụng bị mất điện tắt đột ngột thì dữ liệu có mất không?**  
    *Trả lời:* Dạ không, vì hệ thống áp dụng cơ chế tự động lưu tức thì (Auto-save). Bất kỳ thao tác thêm, xóa, sửa sản phẩm hay người dùng đều được ghi đè ngay lập tức xuống `products.txt` và `users.txt`.
