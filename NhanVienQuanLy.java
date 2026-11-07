public class NhanVienQuanLy extends NhanVien {
    private double phuCapTrachNhiem;

    public NhanVienQuanLy() {
        super("", "", "");
        this.phuCapTrachNhiem = 0;
    }

    public NhanVienQuanLy(String name, String numberPhone, String staffID, double phuCapTrachNhiem) {
        super(name, numberPhone, staffID);
        this.phuCapTrachNhiem = (phuCapTrachNhiem >= 0) ? phuCapTrachNhiem : 0;
    }

    public double getPhuCapTrachNhiem() {
        return phuCapTrachNhiem;
    }

    public void setPhuCapTrachNhiem(double phuCapTrachNhiem) {
        if (phuCapTrachNhiem >= 0) {
            this.phuCapTrachNhiem = phuCapTrachNhiem;
        }
    }
}
