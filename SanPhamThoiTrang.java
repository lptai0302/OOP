public abstract class SanPhamThoiTrang {
    private String Type;
    private String MaSP;
    private String TenSP;
    private double GiaSP;

    public static int quantityOfProducts = 0;

    //CONSTRUCTOR
    public SanPhamThoiTrang(String Type, String MaSP, String TenSP, double GiaSP) {
        this.Type = Type;
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.GiaSP = (GiaSP >= 0) ? GiaSP : 0;

        SanPhamThoiTrang.quantityOfProducts++;
    }

    //ABSTRACT METHOD
    public abstract double VATcalculation();

    public abstract String getStringSaveFile();


    //GETTER
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

    //SETTER
    public boolean setGiaBan(double GiaBanMoi) {
        if (GiaBanMoi >= 0) {
            this.GiaSP = GiaBanMoi;
            return true;
        } else {
            System.out.println("The selling price must be greater than or equal to 0");
            return false;
        }
    }

    public void HienThiThongTin() {
        System.out.println("Product Code: " + MaSP + " | " + "Product Name: " + TenSP + " | " + "Product Price: " + GiaSP);
    }

    public void statisticsProducts() {
        System.out.println("Total number of products in the warehouse: " + SanPhamThoiTrang.quantityOfProducts);
    }

}
