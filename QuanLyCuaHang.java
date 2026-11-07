import java.util.ArrayList;

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

    private void khoiTaoNhaCungCapMacDinh() {
        dsNhaCungCap.add(new NhaCungCap("NCC01", "Tong Cong Ty Det May Viet Tien", "Quan Tan Binh, TP.HCM", "02838640800"));
        dsNhaCungCap.add(new NhaCungCap("NCC02", "Cong Ty Thoi Trang An Phuoc", "Quan 5, TP.HCM", "02838350059"));
        dsNhaCungCap.add(new NhaCungCap("NCC03", "Nha May Giay Biti's Viet Nam", "Quan 6, TP.HCM", "02838753443"));
    }

    public DanhSachSanPham getDsSanPham() {
        return dsSanPham;
    }

    public DanhSachKhachHang getDsKhachHang() {
        return dsKhachHang;
    }

    public ArrayList<NhaCungCap> getDsNhaCungCap() {
        return dsNhaCungCap;
    }

    public String taoMaSPTuDong(String type) {
        return dsSanPham.taoMaTuDong(type);
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

    public void displayAllProducts() {
        dsSanPham.hienThiDanhSach();
    }

    public void searchProducts(String MaSP) {
        SanPhamThoiTrang product = dsSanPham.timKiem(MaSP);
        if (product != null) {
            System.out.println("\nKet qua tim kiem:");
            SanPhamThoiTrang.inTieuDeBang();
            product.inDongBang();
            SanPhamThoiTrang.inChanBang();
            System.out.println();
        } else {
            System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
        }
    }

    public void searchProductsByName(String keyword) {
        ArrayList<SanPhamThoiTrang> list = dsSanPham.timKiemTheoTen(keyword);
        if (!list.isEmpty()) {
            System.out.println("\nKet qua tim kiem theo ten (\"" + keyword + "\"):");
            SanPhamThoiTrang.inTieuDeBang();
            for (SanPhamThoiTrang sp : list) {
                sp.inDongBang();
            }
            SanPhamThoiTrang.inChanBang();
            System.out.println("Tim thay " + list.size() + " san pham.\n");
        } else {
            System.out.println("Khong tim thay san pham nao voi tu khoa: " + keyword);
        }
    }

    public SanPhamThoiTrang getProductByCode(String MaSP) {
        return dsSanPham.timKiem(MaSP);
    }

    public void saveDataIntoFile() {
        dsSanPham.ghiVaoFile("products.txt");
        dsKhachHang.ghiVaoFile("users.txt");
    }

    public void loadDataFromFile(String fileName) {
        dsSanPham.docTuFile(fileName);
        dsKhachHang.docTuFile("users.txt");
    }

    public void updatePrice(String MaSP, double newPrice) {
        SanPhamThoiTrang product = dsSanPham.timKiem(MaSP);
        if (product != null) {
            boolean isUpdated = product.setGiaBan(newPrice);
            if (isUpdated) {
                saveDataIntoFile();
                System.out.println("Da cap nhat gia san pham ma " + MaSP + " thanh " + SanPhamThoiTrang.formatTien(newPrice));
            } else {
                System.out.println("Cap nhat gia that bai.");
            }
        } else {
            System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
        }
    }

    public void updateProduct(String MaSP, String newName, double newPrice) {
        if (dsSanPham.sua(MaSP, newName, newPrice)) {
            saveDataIntoFile();
            System.out.println("Da cap nhat thong tin san pham " + MaSP + " thanh cong!");
        } else {
            System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
        }
    }

    public void runAPromotion() {
        if (dsSanPham.isEmpty()) {
            System.out.println("Kho hang hien khong co san pham nao.");
            return;
        }
        System.out.println("\n=== CHUONG TRINH KHUYEN MAI: XEM TRUOC GIAM GIA 10% ===");
        System.out.println("+------------+----------+------------------------+-----------------+-----------+-----------------+");
        System.out.println("| Loai SP    | Ma SP    | Ten San Pham           | Gia Goc         | Giam Gia  | Gia Sau Giam    |");
        System.out.println("+------------+----------+------------------------+-----------------+-----------+-----------------+");
        for (SanPhamThoiTrang product : dsSanPham.getDanhSach()) {
            double discountedPrice = product.calculateAfterDiscount(10);
            String fName = SanPhamThoiTrang.formatCell(product.getTenSP(), 22);
            System.out.printf("| %-10s | %-8s | %-22s | %15s |    10%%    | %15s |\n",
                    product.getType(), product.getMaSP(), fName,
                    SanPhamThoiTrang.formatTien(product.getGiaBan()),
                    SanPhamThoiTrang.formatTien(discountedPrice));
        }
        System.out.println("+------------+----------+------------------------+-----------------+-----------+-----------------+");
        System.out.println("Luu y: Muc giam gia se tu dong duoc ap dung khi xuat hoa don.\n");
    }

    public boolean addVIPOrStaff(Nguoi person) {
        boolean ok = dsKhachHang.them(person);
        if (ok) {
            saveDataIntoFile();
        }
        return ok;
    }

    public Nguoi getVIPOrStaffByPhone(String phone) {
        return dsKhachHang.timKiemTheoSDT(phone);
    }

    public boolean removeVIPOrStaff(String phone) {
        boolean ok = dsKhachHang.xoaTheoSDT(phone);
        if (ok) {
            saveDataIntoFile();
        }
        return ok;
    }

    public boolean updateVIPOrStaff(String phone, String newName) {
        boolean ok = dsKhachHang.sua(phone, newName);
        if (ok) {
            saveDataIntoFile();
        }
        return ok;
    }

    public void displayAllSuppliers() {
        System.out.println("\n=== DANH SACH NHA CUNG CAP ===");
        System.out.println("+------------+---------------------------+--------------------------------+--------------+");
        System.out.println("| Ma NCC     | Ten Nha Cung Cap          | Dia Chi                        | So Dien Thoai|");
        System.out.println("+------------+---------------------------+--------------------------------+--------------+");
        for (NhaCungCap ncc : dsNhaCungCap) {
            ncc.hienThiThongTin();
        }
        System.out.println("+------------+---------------------------+--------------------------------+--------------+\n");
    }
}
