public class VayDam extends SanPhamThoiTrang {
    private String chatLieuVai;

    public VayDam() {
        super("VayDam", "", "", 0);
        this.chatLieuVai = "Voan";
    }

    public VayDam(String Type, String MaSP, String TenSP, double GiaSP, String chatLieuVai) {
        super(Type, MaSP, TenSP, GiaSP);
        this.chatLieuVai = (chatLieuVai != null && !chatLieuVai.trim().isEmpty()) ? chatLieuVai.trim().replace(",", " ") : "Voan";
    }

    public String getChatLieuVai() {
        return chatLieuVai;
    }

    public void setChatLieuVai(String chatLieuVai) {
        if (chatLieuVai != null && !chatLieuVai.trim().isEmpty()) {
            this.chatLieuVai = chatLieuVai.trim().replace(",", " ");
        }
    }

    @Override
    public String getThongTinRieng() {
        return "Chat lieu: " + this.chatLieuVai;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.chatLieuVai;
    }
}
