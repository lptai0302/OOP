public class QuanJean extends SanPhamThoiTrang {
    private String Style;

    public QuanJean(String Type, String MaSP, String TenSP, double GiaSP, String Style) {
        super(Type, MaSP, TenSP, GiaSP);
        this.Style = Style;
    }

    public String getStyle() {
        return Style;
    }

    @Override
    public String getThongTinRieng() {
        return "Kieu: " + this.Style;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.Style;
    }
}
