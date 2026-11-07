public class KhachHang extends Nguoi {
    private String membershipType;

    public KhachHang() {
        super("", "");
        this.membershipType = "Thuong";
    }

    public KhachHang(String name, String numberPhone, String membershipType) {
        super(name, numberPhone);
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
