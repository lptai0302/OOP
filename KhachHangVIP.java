public class KhachHangVIP extends KhachHang implements IGiamGiaKhachHang {
    private String cardCodeVIP;

    public KhachHangVIP(String name, String numberPhone, String cardCodeVIP) {
        super(name, numberPhone, "VIP");
        this.cardCodeVIP = cardCodeVIP;
    }

    public String getCardCodeVIP() {
        return cardCodeVIP;
    }

    @Override
    public double applyDiscount(double totalPrice) {
        return totalPrice - (totalPrice * 0.2);
    }
}
