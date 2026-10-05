public class CostumerVIP extends Costumer implements IDiscount {
    
    private String cardCodeVIP;

    public CostumerVIP(String name, String numberPhone, String cardCodeVIP) {
        super(name, numberPhone, "VIP");
        this.cardCodeVIP = cardCodeVIP;
    }

    @Override
    public double calculateAfterDiscount(double price) {

        return price - (price * 0.2);
    }
}
