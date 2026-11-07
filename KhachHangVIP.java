public class KhachHangVIP extends KhachHang implements IGiamGiaKhachHang {
    private String cardCodeVIP;

    public KhachHangVIP() {
        super("", "", "VIP");
        this.cardCodeVIP = "";
    }

    public KhachHangVIP(String name, String numberPhone, String cardCodeVIP) {
        super(name, numberPhone, "VIP");
        this.cardCodeVIP = (cardCodeVIP != null) ? cardCodeVIP.trim().replace(",", " ") : "";
    }

    public String getCardCodeVIP() {
        return cardCodeVIP;
    }

    public void setCardCodeVIP(String cardCodeVIP) {
        if (cardCodeVIP != null) {
            this.cardCodeVIP = cardCodeVIP.trim().replace(",", " ");
        }
    }

    @Override
    public double applyDiscount(double totalPrice) {
        return totalPrice - (totalPrice * 0.2);
    }
}
