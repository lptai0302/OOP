import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HoaDon {
    private static int tongSoHoaDon = 0;

    private String billCode;
    private Nguoi person;
    private ArrayList<ChiTietHoaDon> chiTietList;

    public HoaDon() {
        this("HD" + System.currentTimeMillis(), null);
    }

    public HoaDon(String billCode, Nguoi person) {
        this.billCode = billCode;
        this.person = person;
        this.chiTietList = new ArrayList<>();
        tongSoHoaDon++;
    }

    public static int getTongSoHoaDon() {
        return tongSoHoaDon;
    }

    public String getBillCode() {
        return billCode;
    }

    public Nguoi getPerson() {
        return person;
    }

    public void setPerson(Nguoi person) {
        this.person = person;
    }

    public ArrayList<ChiTietHoaDon> getChiTietList() {
        return chiTietList;
    }

    public void addProduct(SanPhamThoiTrang product) {
        addProduct(product, 1);
    }

    public void addProduct(SanPhamThoiTrang product, int soLuong) {
        if (product == null) {
            System.out.println("Loi: Khong the them san pham rong vao hoa don!");
            return;
        }
        if (soLuong <= 0) {
            soLuong = 1;
        }
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getSanPham() != null && ct.getSanPham().getMaSP().equalsIgnoreCase(product.getMaSP())) {
                ct.tangSoLuong(soLuong);
                System.out.println("Da tang so luong san pham: " + product.getTenSP() + " (Tong: " + ct.getSoLuong() + ")");
                return;
            }
        }
        chiTietList.add(new ChiTietHoaDon(product, soLuong));
        System.out.println("Da them san pham: " + product.getTenSP() + " (SL: " + soLuong + ") vao hoa don.");
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct != null) {
                totalPrice += ct.tinhThanhTienSauGiam(10);
            }
        }

        if (this.person instanceof IGiamGiaKhachHang) {
            IGiamGiaKhachHang discountPerson = (IGiamGiaKhachHang) this.person;
            totalPrice = discountPerson.applyDiscount(totalPrice);
        }

        return totalPrice;
    }

    public void printBill() {
        System.out.println("\n+---------------------------------------------------------------------------------------------+");
        System.out.println("|                                      HOA DON BAN HANG                                       |");
        System.out.println("+---------------------------------------------------------------------------------------------+");
        System.out.printf("| Ma hoa don   : %-76s |\n", SanPhamThoiTrang.formatCell(billCode, 76));
        if (person != null) {
            System.out.printf("| Khach hang   : %-76s |\n", SanPhamThoiTrang.formatCell(person.getName(), 76));
            System.out.printf("| So dien thoai: %-76s |\n", SanPhamThoiTrang.formatCell(person.getNumberPhone(), 76));
            System.out.printf("| Doi tuong    : %-76s |\n", SanPhamThoiTrang.formatCell(person.getClass().getSimpleName(), 76));
        } else {
            System.out.printf("| Khach hang   : %-76s |\n", "Khach vang lai");
        }
        System.out.println("+----+------------+----------+--------------------------+-----+---------------+---------------+");
        System.out.println("| STT| Loai SP    | Ma SP    | Ten San Pham             | SL  | Don Gia (VND) | VAT (10%)     |");
        System.out.println("+----+------------+----------+--------------------------+-----+---------------+---------------+");
        int stt = 1;
        for (ChiTietHoaDon ct : chiTietList) {
            SanPhamThoiTrang product = ct.getSanPham();
            if (product == null) continue;
            String fName = SanPhamThoiTrang.formatCell(product.getTenSP(), 24);
            System.out.printf("| %-2d | %-10s | %-8s | %-24s | %-3d | %13s | %13s |\n",
                    stt++, product.getType(), product.getMaSP(), fName, ct.getSoLuong(),
                    SanPhamThoiTrang.formatSo(product.getGiaBan()),
                    SanPhamThoiTrang.formatSo(ct.getTienVAT()));
        }
        System.out.println("+----+------------+----------+--------------------------+-----+---------------+---------------+");
        if (this.person instanceof IGiamGiaKhachHang) {
            System.out.printf("| Da ap dung giam gia cho doi tuong: %-56s |\n", this.person.getClass().getSimpleName());
            System.out.println("+---------------------------------------------------------------------------------------------+");
        }
        double finalTotal = calculateTotalPrice();
        System.out.printf("| TONG THANH TOAN (DA TINH VAT VA GIAM GIA): %48s |\n", SanPhamThoiTrang.formatTien(finalTotal));
        System.out.println("+---------------------------------------------------------------------------------------------+\n");

        for (ChiTietHoaDon ct : chiTietList) {
            SanPhamThoiTrang product = ct.getSanPham();
            if (product instanceof IBaoHanh) {
                IBaoHanh warrantyItem = (IBaoHanh) product;
                warrantyItem.printWarrantyCard();
            }
        }
    }

    public void xuatHoaDonRaFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), StandardCharsets.UTF_8))) {
            writer.write("=================================================");
            writer.newLine();
            writer.write("MA HOA DON: " + billCode);
            writer.newLine();
            writer.write("KHACH HANG: " + ((person != null) ? person.getName() : "Khach vang lai"));
            writer.newLine();
            writer.write("SO DIEN THOAI: " + ((person != null) ? person.getNumberPhone() : "N/A"));
            writer.newLine();
            writer.write("DOI TUONG: " + ((person != null) ? person.getClass().getSimpleName() : "Khach vang lai"));
            writer.newLine();
            writer.write("CHI TIET SAN PHAM:");
            writer.newLine();
            for (ChiTietHoaDon ct : chiTietList) {
                if (ct.getSanPham() != null) {
                    writer.write(String.format("  - [%s] %s | SL: %d | Don gia: %s",
                            ct.getSanPham().getMaSP(), ct.getSanPham().getTenSP(),
                            ct.getSoLuong(), SanPhamThoiTrang.formatTien(ct.getSanPham().getGiaBan())));
                    writer.newLine();
                }
            }
            writer.write("TONG THANH TOAN: " + SanPhamThoiTrang.formatTien(calculateTotalPrice()));
            writer.newLine();
            writer.write("=================================================");
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Loi khi luu hoa don vao file: " + e.getMessage());
        }
    }
}
