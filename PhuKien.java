public class PhuKien extends SanPhamThoiTrang implements IBaoHanh {
    private String Material;

    public PhuKien(String Type, String MaSP, String TenSP, double GiaSP, String Material) {
        super(Type, MaSP, TenSP, GiaSP);
        this.Material = Material;
    }

    public String getMaterial() {
        return Material;
    }

    @Override
    public String getThongTinRieng() {
        return "Chat lieu: " + this.Material;
    }

    @Override
    public void printWarrantyCard() {
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   PHIEU BAO HANH                      |");
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("| Ten san pham      : %-33s |\n", getTenSP());
        System.out.printf("| Ma san pham       : %-33s |\n", getMaSP());
        System.out.printf("| Thoi han bao hanh : %-33s |\n", getWarrantyPeriod() + " thang");
        System.out.println("+-------------------------------------------------------+");
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
