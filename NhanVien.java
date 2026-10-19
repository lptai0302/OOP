public class NhanVien extends Nguoi implements IGiamGiaKhachHang {
    private String staffID;

    public NhanVien(String name, String numberPhone, String staffID) {
        super(name, numberPhone);
        this.staffID = staffID;
    }

    public String getStaffID() {
        return staffID;
    }

    @Override
    public double applyDiscount(double totalPrice) {
        return totalPrice - (totalPrice * 0.5);
    }
}
