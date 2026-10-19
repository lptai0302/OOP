public class AoThun extends SanPhamThoiTrang {
    private String size;

    public AoThun(String Type, String MaSP, String TenSP, double GiaSP, String size) {
        super(Type, MaSP, TenSP, GiaSP);
        if (size.equalsIgnoreCase("S") || size.equalsIgnoreCase("M") || size.equalsIgnoreCase("L") || size.equalsIgnoreCase("XL")) {
            this.size = size.toUpperCase();
        } else {
            System.out.println("Kich co khong hop le (" + size + "). Mac dinh chon co 'M'.");
            this.size = "M";
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size.equals("S") || size.equals("M") || size.equals("L") || size.equals("XL")) {
            this.size = size;
        } else {
            System.out.println("Kich co khong hop le. Vui long nhap S, M, L hoac XL.");
        }
    }

    @Override
    public String getThongTinRieng() {
        return "Size: " + this.size;
    }

    @Override
    public String getStringSaveFile() {
        return getType() + "," + getMaSP() + "," + getTenSP() + "," + getGiaBan() + "," + this.size;
    }
}
