public class AoKhoac extends SanPhamThoiTrang implements IDiscount, IWarranty {
    private boolean hasHat;

    //CONSTRUCTOR
    public AoKhoac(String Type, String MaSP, String TenSP, double GiaSP, boolean hasHat) {
        //gọi đến class cha của constructor
        super(Type, MaSP, TenSP, GiaSP);
        this.hasHat = hasHat;
    }

    public boolean isHasHat() {
        return hasHat;
    }

    public void setHasHat(boolean hasHat) {
        this.hasHat = hasHat;
    }

    @Override
    public void HienThiThongTin() {
        super.HienThiThongTin();

        String hasHatStr = this.hasHat ? "Yes" : "No";
        System.out.println("Has Hat: " + hasHatStr);
        System.out.println("---------------------------");
        
    }

    @Override
    public double VATcalculation() {
        return getGiaBan() * 0.1;
    }

    @Override
    public double calculateAfterDiscount(double discountPercentage) {
        double basePrice = getGiaBan();
        return basePrice - (basePrice * discountPercentage / 100);
    }

    @Override
    public void printWarrantyCard() {
        System.out.println("Warranty Card for " + getTenSP());
        System.out.println("Product Code: " + getMaSP());
        System.out.println("Warranty Period: " + getWarrantyPeriod() + " months");
        System.out.println("---------------------------");
    }

    @Override
    public int getWarrantyPeriod() {
        return 12;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.hasHat;
    }
}
