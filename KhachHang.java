public class KhachHang extends Nguoi {
    private String membershipType;

    public KhachHang(String name, String numberPhone, String membershipType) {
        super(name, numberPhone);
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return membershipType;
    }
}
