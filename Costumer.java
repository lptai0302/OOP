public class Costumer extends Person {
    private String membershipType;
    
    public Costumer(String name, String numberPhone, String membershipType) {
        super(name, numberPhone);
        this.membershipType = membershipType;
    }

    //GETTER
    public String getMembershipType() {
        return membershipType;
    }

}
