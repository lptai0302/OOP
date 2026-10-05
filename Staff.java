public class Staff extends Costumer implements IDiscount {
    private String staffID;

    public Staff(String name, String numberPhone, String staffID) {
        super(name, numberPhone, "Staff");
        this.staffID = staffID;
    }

    @Override
    public double calculateAfterDiscount(double price) {

        return price - (price * 0.5);
    }

}
