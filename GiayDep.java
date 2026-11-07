public class GiayDep extends SanPhamThoiTrang implements IBaoHanh {
    private int sizeGiay;

    public GiayDep() {
        super("GiayDep", "", "", 0);
        this.sizeGiay = 40;
    }

    public GiayDep(String Type, String MaSP, String TenSP, double GiaSP, int sizeGiay) {
        super(Type, MaSP, TenSP, GiaSP);
        this.sizeGiay = (sizeGiay >= 35 && sizeGiay <= 45) ? sizeGiay : 40;
    }

    public int getSizeGiay() {
        return sizeGiay;
    }

    public void setSizeGiay(int sizeGiay) {
        if (sizeGiay >= 35 && sizeGiay <= 45) {
            this.sizeGiay = sizeGiay;
        } else {
            System.out.println("Size giay khong hop le (tu 35 den 45).");
        }
    }

    @Override
    public String getThongTinRieng() {
        return "Size giay: " + this.sizeGiay;
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
        return 3;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.sizeGiay;
    }
}
