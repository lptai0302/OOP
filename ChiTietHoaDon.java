public class ChiTietHoaDon {
    private SanPhamThoiTrang sanPham;
    private int soLuong;

    public ChiTietHoaDon() {
        this.sanPham = null;
        this.soLuong = 1;
    }

    public ChiTietHoaDon(SanPhamThoiTrang sanPham, int soLuong) {
        this.sanPham = sanPham;
        this.soLuong = (soLuong > 0) ? soLuong : 1;
    }

    public SanPhamThoiTrang getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPhamThoiTrang sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong > 0) {
            this.soLuong = soLuong;
        }
    }

    public void tangSoLuong(int amount) {
        if (amount > 0) {
            this.soLuong += amount;
        }
    }

    public double getDonGia() {
        return (sanPham != null) ? sanPham.getGiaBan() : 0;
    }

    public double getTienVAT() {
        return (sanPham != null) ? (sanPham.VATcalculation() * soLuong) : 0;
    }

    public double tinhThanhTienSauGiam(double phanTramGiamGia) {
        if (sanPham == null) return 0;
        double giaSauGiam1SP = sanPham.calculateAfterDiscount(phanTramGiamGia);
        return (giaSauGiam1SP + sanPham.VATcalculation()) * soLuong;
    }

    public double tinhThanhTienGoc() {
        if (sanPham == null) return 0;
        return (sanPham.getGiaBan() + sanPham.VATcalculation()) * soLuong;
    }
}
