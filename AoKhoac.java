public class AoKhoac extends SanPhamThoiTrang implements IBaoHanh {
    private boolean hasHat;

    public AoKhoac(String Type, String MaSP, String TenSP, double GiaSP, boolean hasHat) {
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
    public String getThongTinRieng() {
        return "Co non: " + (this.hasHat ? "Co" : "Khong");
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
        return 12;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.hasHat;
    }
}
