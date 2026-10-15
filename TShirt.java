public class TShirt extends SanPhamThoiTrang implements IDiscount {
    private String size;
    
    //CONSTRUCTOR
    public TShirt(String Type, String MaSP, String TenSP, double GiaSP, String size) {
        super(Type, MaSP, TenSP, GiaSP);
        if (size.equalsIgnoreCase("S") || size.equalsIgnoreCase("M") || size.equalsIgnoreCase("L") || size.equalsIgnoreCase("XL")) {
            this.size = size.toUpperCase();
        } else {
            System.out.println("Invalid size (" + size + "). Defaulting to size 'M'.");
            this.size = "M";
        }
    }

    //GETTER

    public String getSize() {
        return size;
    }

    //SETTER

    public void setSize(String size) {
        if (size.equals("S") || size.equals("M") || size.equals("L") || size.equals("XL")) {
            this.size = size;
        } else {
            System.out.println("Invalid size. Please enter S, M, L, or XL.");
        }
    }

    @Override
    public void HienThiThongTin() {
        super.HienThiThongTin();
        System.out.println("Size: " + this.size);
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
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.size;
    }

}
