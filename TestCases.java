import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TestCases {
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    private static void assertTrue(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("  [PASS] " + testName);
        } else {
            failedTests++;
            System.out.println("  [FAIL] " + testName);
        }
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        totalTests++;
        if (Objects.equals(expected, actual)) {
            passedTests++;
            System.out.println("  [PASS] " + testName);
        } else {
            failedTests++;
            System.out.println("  [FAIL] " + testName + " - Expected: " + expected + ", Got: " + actual);
        }
    }

    private static void assertEqualsDouble(String testName, double expected, double actual) {
        totalTests++;
        if (Math.abs(expected - actual) < 0.001) {
            passedTests++;
            System.out.println("  [PASS] " + testName);
        } else {
            failedTests++;
            System.out.println("  [FAIL] " + testName + " - Expected: " + expected + ", Got: " + actual);
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     KIEM THU TOAN DIEN DO AN OOP BAN HANG       ");
        System.out.println("=================================================");

        byte[] backupProd = null;
        byte[] backupUsers = null;
        try {
            if (Files.exists(Path.of("products.txt"))) {
                backupProd = Files.readAllBytes(Path.of("products.txt"));
            }
            if (Files.exists(Path.of("users.txt"))) {
                backupUsers = Files.readAllBytes(Path.of("users.txt"));
            }
        } catch (Exception ignored) {}

        try {
            testProductHierarchy();
            testStaticAttributesAndMethods();
            testNewProductsVayDamAndGiayDep();
            testCustomerAndStaffHierarchy();
            testChiTietHoaDonAndMultiQuantity();
            testDanhSachSanPhamClassArray();
            testDanhSachKhachHangClassArray();
            testFullStoreCRUDAndWorkflow();
            testWarrantyPolymorphism();
            testRobustnessAndEdgeCases();
        } finally {
            try {
                if (backupProd != null) Files.write(Path.of("products.txt"), backupProd);
                if (backupUsers != null) Files.write(Path.of("users.txt"), backupUsers);
                Files.deleteIfExists(Path.of("test_temp.txt"));
                Files.deleteIfExists(Path.of("test_users_corrupt.txt"));
            } catch (Exception ignored) {}
        }

        System.out.println("\n=================================================");
        System.out.printf("  KET QUA: TONG SO %d TESTCASE | THANH CONG: %d | THAT BAI: %d\n",
                totalTests, passedTests, failedTests);
        System.out.println("=================================================");
    }

    private static void testProductHierarchy() {
        System.out.println("\n--- 1. Kiem thu Ke thua SanPhamThoiTrang & Constructor ---");
        AoKhoac ak = new AoKhoac("AoKhoac", "AK001", "Ao Gio The Thao", 500000, true);
        assertEquals("AoKhoac MaSP", "AK001", ak.getMaSP());
        assertEqualsDouble("AoKhoac GiaBan", 500000, ak.getGiaBan());
        assertEqualsDouble("AoKhoac VAT (10%)", 50000, ak.VATcalculation());
        assertEqualsDouble("AoKhoac Giam 10%", 450000, ak.calculateAfterDiscount(10));

        AoThun at = new AoThun("AoThun", "AT001", "Ao Polo", 300000, "l");
        assertEquals("AoThun size lowercase chuẩn hóa thành 'L'", "L", at.getSize());

        QuanJean qj = new QuanJean("QuanJean", "QJ001", "Jean Slimfit", 450000, "Slim");
        assertEquals("QuanJean Style", "Slim", qj.getStyle());

        PhuKien pk = new PhuKien("PhuKien", "PK001", "Day Nit Da", 200000, "Da Bo");
        assertEquals("PhuKien Chat Lieu", "Da Bo", pk.getMaterial());
    }

    private static void testStaticAttributesAndMethods() {
        System.out.println("\n--- 2. Kiem thu Thuoc tinh static & Phuong thuc static ---");
        assertEqualsDouble("Static TI_LE_VAT la 0.1 (10%)", 0.1, SanPhamThoiTrang.TI_LE_VAT);
        assertTrue("Static getTongSoLuongSanPhamDaTao() > 0", SanPhamThoiTrang.getTongSoLuongSanPhamDaTao() > 0);
        assertEquals("Static formatTien(1500000)", "1.500.000 VND", SanPhamThoiTrang.formatTien(1500000));
        assertEquals("Static formatSo(1500000)", "1.500.000", SanPhamThoiTrang.formatSo(1500000));
        assertEquals("Static formatCell truncate", "Ten san pham dai qu...", SanPhamThoiTrang.formatCell("Ten san pham dai qua 22 ky tu", 22));
    }

    private static void testNewProductsVayDamAndGiayDep() {
        System.out.println("\n--- 3. Kiem thu San pham moi VayDam & GiayDep ---");
        VayDam vd = new VayDam("VayDam", "VD001", "Vay Da Hoi", 850000, "Lua Satin");
        assertEquals("VayDam Chat lieu vai", "Lua Satin", vd.getChatLieuVai());
        assertEquals("VayDam Thong tin rieng", "Chat lieu: Lua Satin", vd.getThongTinRieng());

        GiayDep gd = new GiayDep("GiayDep", "GD001", "Giay Sneaker", 1200000, 42);
        assertEquals("GiayDep Size", 42, gd.getSizeGiay());
        assertEquals("GiayDep Bao hanh 3 thang", 3, gd.getWarrantyPeriod());
        assertTrue("GiayDep implements IBaoHanh", gd instanceof IBaoHanh);
    }

    private static void testCustomerAndStaffHierarchy() {
        System.out.println("\n--- 4. Kiem thu Nguoi, KhachHang, KhachHangVIP, ThanThiet, NhanVien, QuanLy ---");
        KhachHangVIP vip = new KhachHangVIP("Nguyen Van A", "0912345678", "VIP888");
        assertEqualsDouble("KhachHangVIP giam 20% cua 1.000.000 = 800.000", 800000, vip.applyDiscount(1000000));

        KhachHangThanThiet tt = new KhachHangThanThiet("Le Thi B", "0922222222", 150);
        assertEqualsDouble("KhachHangThanThiet giam 10% cua 1.000.000 = 900.000", 900000, tt.applyDiscount(1000000));
        assertEquals("Diem tich luy", 150, tt.getDiemTichLuy());

        NhanVien nv = new NhanVien("Pham Thu Ngan", "0933333333", "NV01");
        assertEqualsDouble("NhanVien giam 50% cua 1.000.000 = 500.000", 500000, nv.applyDiscount(1000000));

        NhanVienQuanLy ql = new NhanVienQuanLy("Hoang Quan Ly", "0944444444", "QL01", 3000000);
        assertEqualsDouble("NhanVienQuanLy giam 50%", 500000, ql.applyDiscount(1000000));
        assertEqualsDouble("Phu cap trach nhiem", 3000000, ql.getPhuCapTrachNhiem());
    }

    private static void testChiTietHoaDonAndMultiQuantity() {
        System.out.println("\n--- 5. Kiem thu ChiTietHoaDon & Mua nhieu so luong ---");
        AoKhoac ak = new AoKhoac("AoKhoac", "AK001", "Adidas", 1800000, false);
        ChiTietHoaDon ct = new ChiTietHoaDon(ak, 2);
        assertEquals("So luong chi tiet", 2, ct.getSoLuong());
        assertEqualsDouble("Don gia 1 SP", 1800000, ct.getDonGia());
        assertEqualsDouble("VAT 2 SP", 360000, ct.getTienVAT());
        assertEqualsDouble("Thanh tien sau giam 10%", 3600000, ct.tinhThanhTienSauGiam(10));

        HoaDon hd = new HoaDon("HD_TEST", null);
        hd.addProduct(ak, 1);
        hd.addProduct(ak, 2);
        assertEquals("So dong trong bill", 1, hd.getChiTietList().size());
        assertEquals("So luong duoc cong don thanh 3", 3, hd.getChiTietList().get(0).getSoLuong());
    }

    private static void testDanhSachSanPhamClassArray() {
        System.out.println("\n--- 6. Kiem thu Lop mang DanhSachSanPham (CRUD + File) ---");
        DanhSachSanPham ds = new DanhSachSanPham();
        ds.them(new AoKhoac("AoKhoac", "AK001", "Ao Khoac Gio", 500000, false));
        ds.them(new VayDam("VayDam", "VD001", "Dam Da Hoi", 900000, "Voan"));

        assertEquals("So luong SP trong danh sach", 2, ds.getSoLuong());
        assertTrue("Tim thay AK001", ds.timKiem("ak001") != null);

        ds.sua("AK001", "Ao Khoac Gio Mua Dong", 550000);
        assertEquals("Ten sau khi sua", "Ao Khoac Gio Mua Dong", ds.timKiem("AK001").getTenSP());
        assertEqualsDouble("Gia sau khi sua", 550000, ds.timKiem("AK001").getGiaBan());

        assertEquals("Sinh ma tiep theo cho AoKhoac", "AK002", ds.taoMaTuDong("AoKhoac"));
        assertEquals("Sinh ma tiep theo cho VayDam", "VD002", ds.taoMaTuDong("VayDam"));
        assertEquals("Sinh ma tiep theo cho GiayDep", "GD001", ds.taoMaTuDong("GiayDep"));

        ds.xoa("AK001");
        assertEquals("So luong sau khi xoa", 1, ds.getSoLuong());
    }

    private static void testDanhSachKhachHangClassArray() {
        System.out.println("\n--- 7. Kiem thu Lop mang DanhSachKhachHang (CRUD) ---");
        DanhSachKhachHang dsKH = new DanhSachKhachHang();
        dsKH.them(new KhachHangVIP("Nguyen VIP", "0911111111", "VIP01"));
        dsKH.them(new NhanVien("Tran Staff", "0922222222", "NV01"));

        assertEquals("So luong nguoi dung", 2, dsKH.getDanhSachNguoi().size());
        assertTrue("Tim thay theo SDT", dsKH.timKiemTheoSDT("0911111111") != null);
        assertTrue("Chan them trung SDT", !dsKH.them(new KhachHangVIP("Trung", "0911111111", "VIP02")));

        assertTrue("Sua ten nguoi dung theo SDT", dsKH.sua("0911111111", "Nguyen VIP Moi"));
        assertEquals("Ten moi sau khi sua", "Nguyen VIP Moi", dsKH.timKiemTheoSDT("0911111111").getName());

        assertTrue("Xoa nguoi dung theo SDT", dsKH.xoaTheoSDT("0922222222"));
        assertEquals("So luong sau khi xoa", 1, dsKH.getDanhSachNguoi().size());
    }

    private static void testFullStoreCRUDAndWorkflow() {
        System.out.println("\n--- 8. Kiem thu Tong the QuanLyCuaHang & Xuat File Hoa Don ---");
        QuanLyCuaHang store = new QuanLyCuaHang();
        store.addProduct(new AoKhoac("AoKhoac", "AK100", "Ao Adidas X", 1800000, true));
        store.addVIPOrStaff(new KhachHangVIP("Khach VIP Test", "0988888888", "VIP999"));

        assertEquals("Tim kiem san pham theo ten", 1, store.getDsSanPham().timKiemTheoTen("Adidas").size());

        AoKhoac spAK = (AoKhoac) store.getProductByCode("AK100");
        spAK.setHasHat(false);
        assertTrue("Sua thuoc tinh rieng hasHat thanh cong", !spAK.isHasHat());

        assertTrue("Cap nhat thong tin khach hang thanh cong", store.updateVIPOrStaff("0988888888", "Khach VIP Doi Ten"));
        assertEquals("Ten khach hang sau cap nhat", "Khach VIP Doi Ten", store.getVIPOrStaffByPhone("0988888888").getName());

        HoaDon hd = new HoaDon("HD999", store.getVIPOrStaffByPhone("0988888888"));
        hd.addProduct(store.getProductByCode("AK100"), 1);
        double total = hd.calculateTotalPrice();
        assertEqualsDouble("VIP Total = 1.440.000 VND", 1440000, total);

        hd.xuatHoaDonRaFile("hoadon.txt");
        assertTrue("File hoadon.txt duoc tao thanh cong", Files.exists(Path.of("hoadon.txt")));

        assertTrue("Xoa khach hang thanh cong", store.removeVIPOrStaff("0988888888"));
        assertTrue("Khong con tim thay khach hang sau khi xoa", store.getVIPOrStaffByPhone("0988888888") == null);

        assertTrue("Co it nhat 3 nha cung cap san co", store.getDsNhaCungCap().size() >= 3);
    }

    private static void testWarrantyPolymorphism() {
        System.out.println("\n--- 9. Kiem thu Da hinh Bao hanh (IBaoHanh) ---");
        AoKhoac ak = new AoKhoac();
        PhuKien pk = new PhuKien();
        GiayDep gd = new GiayDep();
        AoThun at = new AoThun();

        assertTrue("AoKhoac la IBaoHanh", ak instanceof IBaoHanh);
        assertTrue("PhuKien la IBaoHanh", pk instanceof IBaoHanh);
        assertTrue("GiayDep la IBaoHanh", gd instanceof IBaoHanh);
        assertTrue("AoThun khong phai IBaoHanh", !(at instanceof IBaoHanh));

        assertEquals("AoKhoac bao hanh 12 thang", 12, ak.getWarrantyPeriod());
        assertEquals("PhuKien bao hanh 6 thang", 6, pk.getWarrantyPeriod());
        assertEquals("GiayDep bao hanh 3 thang", 3, gd.getWarrantyPeriod());
    }

    private static void testRobustnessAndEdgeCases() {
        System.out.println("\n--- 10. Kiem thu Do ben vung & Xu ly Ngoai le (Robustness) ---");
        AoKhoac ak = new AoKhoac();
        ak.setTenSP("Ao, Khoac, Mua, Dong");
        assertEquals("setTenSP loai bo dau phay", "Ao  Khoac  Mua  Dong", ak.getTenSP());

        ChiTietHoaDon ctNeg = new ChiTietHoaDon(ak, -5);
        assertEquals("ChiTietHoaDon so luong am tu dong ve 1", 1, ctNeg.getSoLuong());

        HoaDon hd = new HoaDon("HD_ROBUST", null);
        hd.addProduct(ak, 0);
        assertEquals("addProduct so luong 0 tu dong ve 1", 1, hd.getChiTietList().get(0).getSoLuong());

        String corruptFile = "test_users_corrupt.txt";
        try {
            String content = "VIP,Khach A,0901,VIP1\n"
                    + "ThanThiet,Khach B,0902,not_a_number\n"
                    + "NhanVien,Staff C,0903,NV1\n";
            Files.writeString(Path.of(corruptFile), content);
            DanhSachKhachHang ds = new DanhSachKhachHang();
            ds.docTuFile(corruptFile);
            assertTrue("Dong hop le 1 duoc nap", ds.timKiemTheoSDT("0901") != null);
            assertTrue("Dong loi khong chan dong hop le sau do", ds.timKiemTheoSDT("0903") != null);
        } catch (Exception e) {
            assertTrue("Doc file loi nem ngoai le", false);
        } finally {
            try {
                Files.deleteIfExists(Path.of(corruptFile));
            } catch (Exception ignored) {}
        }

        assertEquals("formatTien 0 VND", "0 VND", SanPhamThoiTrang.formatTien(0));
        assertEquals("formatSo 0", "0", SanPhamThoiTrang.formatSo(0));
        assertEquals("formatCell null an toan", "", SanPhamThoiTrang.formatCell(null, 20));

        QuanJean qj = new QuanJean("QuanJean", "QJ99", "Quan", 100000, "Ong, Rong, Baggy");
        assertTrue("QuanJean Style loai bo dau phay", !qj.getStyle().contains(","));

        PhuKien pk = new PhuKien("PhuKien", "PK99", "Vi", 100000, "Da, Bo, That");
        assertTrue("PhuKien Material loai bo dau phay", !pk.getMaterial().contains(","));

        VayDam vd = new VayDam("VayDam", "VD99", "Vay", 100000, "Voan, Lua, Cat");
        assertTrue("VayDam chat lieu vai loai bo dau phay", !vd.getChatLieuVai().contains(","));

        int countBefore = HoaDon.getTongSoHoaDon();
        new HoaDon("HD_TEST_COUNT", null);
        assertEquals("tongSoHoaDon tang dung 1 don vi", countBefore + 1, HoaDon.getTongSoHoaDon());

        String utf8TestFile = "test_utf8_temp.txt";
        try {
            DanhSachSanPham dsUtf8 = new DanhSachSanPham();
            dsUtf8.them(new AoKhoac("AoKhoac", "AK888", "Áo gió mùa đông ấm áp", 450000, true));
            dsUtf8.ghiVaoFile(utf8TestFile);

            DanhSachSanPham dsLoad = new DanhSachSanPham();
            dsLoad.docTuFile(utf8TestFile);
            SanPhamThoiTrang spUtf8 = dsLoad.timKiem("AK888");
            assertTrue("UTF-8 tieng Viet doc ghi chuan xac", spUtf8 != null && spUtf8.getTenSP().equals("Áo gió mùa đông ấm áp"));
        } catch (Exception e) {
            assertTrue("Loi kiem tra UTF-8", false);
        } finally {
            try {
                Files.deleteIfExists(Path.of(utf8TestFile));
            } catch (Exception ignored) {}
        }
    }
}
