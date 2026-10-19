import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public abstract class SanPhamThoiTrang implements IGiamGia {
    private String Type;
    private String MaSP;
    private String TenSP;
    private double GiaSP;

    public SanPhamThoiTrang(String Type, String MaSP, String TenSP, double GiaSP) {
        this.Type = Type;
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.GiaSP = (GiaSP >= 0) ? GiaSP : 0;
    }

    public abstract String getStringSaveFile();
    public abstract String getThongTinRieng();

    public double VATcalculation() {
        return this.GiaSP * 0.1;
    }

    @Override
    public double calculateAfterDiscount(double discountPercentage) {
        return this.GiaSP - (this.GiaSP * discountPercentage / 100);
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

    public String getType() {
        return this.Type;
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

    private static String formatCell(String text, int maxLen) {
        if (text == null) return "";
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
