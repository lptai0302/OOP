public class NhanVien extends Nguoi implements IGiamGiaKhachHang {
    private String staffID;

    public NhanVien() {
        super("", "");
        this.staffID = "";
    }

    public NhanVien(String name, String numberPhone, String staffID) {
        super(name, numberPhone);
        this.staffID = (staffID != null) ? staffID.trim().replace(",", " ") : "";
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        if (staffID != null) {
            this.staffID = staffID.trim().replace(",", " ");
        }
    }

    @Override
    public double applyDiscount(double totalPrice) {
        return totalPrice - (totalPrice * 0.5);
    }
}
