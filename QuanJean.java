public class QuanJean extends SanPhamThoiTrang {
    private String Style;

    public QuanJean() {
        super("QuanJean", "", "", 0);
        this.Style = "Slim";
    }

    public QuanJean(String Type, String MaSP, String TenSP, double GiaSP, String Style) {
        super(Type, MaSP, TenSP, GiaSP);
        this.Style = (Style != null) ? Style.trim().replace(",", " ") : "Slim";
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String Style) {
        if (Style != null && !Style.trim().isEmpty()) {
            this.Style = Style.trim().replace(",", " ");
        }
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
