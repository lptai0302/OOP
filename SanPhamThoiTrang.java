import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public abstract class SanPhamThoiTrang implements IGiamGia {
    public static final double TI_LE_VAT = 0.10;
    private static int tongSoLuongSanPhamDaTao = 0;

    private String Type;
    private String MaSP;
    private String TenSP;
    private double GiaSP;

    public SanPhamThoiTrang() {
        this("ChuaPhanLoai", "", "", 0);
    }

    public SanPhamThoiTrang(String Type, String MaSP, String TenSP, double GiaSP) {
        this.Type = (Type != null) ? Type : "ChuaPhanLoai";
        this.MaSP = (MaSP != null) ? MaSP : "";
        this.TenSP = (TenSP != null) ? TenSP : "";
        this.GiaSP = (GiaSP >= 0) ? GiaSP : 0;
        tongSoLuongSanPhamDaTao++;
    }

    public abstract String getStringSaveFile();
    public abstract String getThongTinRieng();

    public double VATcalculation() {
        return this.GiaSP * TI_LE_VAT;
    }

    @Override
    public double calculateAfterDiscount(double discountPercentage) {
        return this.GiaSP - (this.GiaSP * discountPercentage / 100);
    }

    public static int getTongSoLuongSanPhamDaTao() {
        return tongSoLuongSanPhamDaTao;
    }

    public double getGiaBan() {
        return this.GiaSP;
    }

    public String getMaSP() {
        return this.MaSP;
    }

    public String getTenSP() {
        return this.TenSP;
    }

    public void setTenSP(String TenSP) {
        if (TenSP != null && !TenSP.trim().isEmpty()) {
            this.TenSP = TenSP.trim().replace(",", " ");
        }
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String Type) {
        if (Type != null && !Type.trim().isEmpty()) {
            this.Type = Type.trim();
        }
    }

    public boolean setGiaBan(double GiaBanMoi) {
        if (GiaBanMoi >= 0) {
            this.GiaSP = GiaBanMoi;
            return true;
        } else {
            System.out.println("Gia ban phai lon hon hoac bang 0!");
            return false;
        }
    }

    public static String formatTien(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        return df.format(Math.round(amount)) + " VND";
    }

    public static String formatSo(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        return df.format(Math.round(amount));
    }

    public static String formatCell(String text, int maxLen) {
        if (text == null) return "";
        if (maxLen <= 3) return (text.length() > maxLen) ? text.substring(0, maxLen) : text;
        if (text.length() > maxLen) {
            return text.substring(0, maxLen - 3) + "...";
        }
        return text;
    }

    public static void inTieuDeBang() {
        System.out.println("+------------+----------+------------------------+-----------------+----------------------+");
        System.out.println("| Loai SP    | Ma SP    | Ten San Pham           | Gia Ban         | Thong Tin Rieng      |");
        System.out.println("+------------+----------+------------------------+-----------------+----------------------+");
    }

    public static void inChanBang() {
        System.out.println("+------------+----------+------------------------+-----------------+----------------------+");
    }

    public void inDongBang() {
        String fName = formatCell(TenSP, 22);
        String fInfo = formatCell(getThongTinRieng(), 20);
        System.out.printf("| %-10s | %-8s | %-22s | %15s | %-20s |\n",
                Type, MaSP, fName, formatTien(GiaSP), fInfo);
    }

    public void HienThiThongTin() {
        inTieuDeBang();
        inDongBang();
        inChanBang();
    }
}
