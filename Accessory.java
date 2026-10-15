public class Accessory extends SanPhamThoiTrang implements IWarranty, IDiscount {
    private String Material;

    //CONSTRUCTOR
    public Accessory(String Type, String MaSP, String TenSP, double GiaSP, String Material) {
        super(Type, MaSP, TenSP, GiaSP);
        this.Material = Material;
    }

    //GETTER
    public String getMaterial() {
        return Material;
    }

    @Override
    public void HienThiThongTin() {
        super.HienThiThongTin();
        System.out.println("Material: " + this.Material);
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
        return 6;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.Material;
    }

}
