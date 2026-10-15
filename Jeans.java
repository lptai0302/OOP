public class Jeans extends SanPhamThoiTrang implements IDiscount {
    private String Style;

    //CONSTRUCTOR
    public Jeans(String Type, String MaSP, String TenSP, double GiaSP, String Style) {
        super(Type, MaSP, TenSP, GiaSP);
        this.Style = Style;
    }

    //GETTER
    public String getStyle() {
        return Style;
    }

    @Override
    public void HienThiThongTin() {
        super.HienThiThongTin();
        System.out.println("Style: " + this.Style);
        System.out.println("---------------------------");
    }

    @Override
    public double VATcalculation() {
        return getGiaBan() * 0.1;
    }

    // @Override
    // public double calculateAfterDiscount(double price) {
    //     double basePrice = getGiaBan();
    //     return basePrice - (basePrice * 0.1);
    // }

    @Override
    public double calculateAfterDiscount(double discountPercentage) {
        double basePrice = getGiaBan();
        return basePrice - (basePrice * discountPercentage / 100);
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.Style;
    }
}
