import java.util.ArrayList;

public class HoaDon {
    private String billCode;
    private Nguoi person;
    private ArrayList<SanPhamThoiTrang> shoppingList;

    public HoaDon(String billCode, Nguoi person) {
        this.billCode = billCode;
        this.person = person;
        this.shoppingList = new ArrayList<>();
    }

    public void addProduct(SanPhamThoiTrang product) {
        shoppingList.add(product);
        System.out.println("Da them san pham: " + product.getTenSP() + " vao hoa don.");
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (SanPhamThoiTrang product : shoppingList) {
            double itemPrice = product.calculateAfterDiscount(10);
            totalPrice += itemPrice + product.VATcalculation();
        }

        if (this.person instanceof IGiamGiaKhachHang) {
            IGiamGiaKhachHang discountPerson = (IGiamGiaKhachHang) this.person;
            totalPrice = discountPerson.applyDiscount(totalPrice);
        }

        return totalPrice;
    }

    public void printBill() {
        System.out.println("\n+---------------------------------------------------------------------------------+");
        System.out.println("|                                HOA DON BAN HANG                                 |");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.printf("| Ma hoa don   : %-64s |\n", billCode);
        if (person != null) {
            System.out.printf("| Khach hang   : %-64s |\n", person.getName());
            System.out.printf("| So dien thoai: %-64s |\n", person.getNumberPhone());
            System.out.printf("| Doi tuong    : %-64s |\n", person.getClass().getSimpleName());
        } else {
            System.out.printf("| Khach hang   : %-64s |\n", "Khach vang lai");
        }
        System.out.println("+----+------------+----------+------------------------+---------------+-----------+");
        System.out.println("| STT| Loai SP    | Ma SP    | Ten San Pham           | Don Gia (VND) | VAT (10%) |");
        System.out.println("+----+------------+----------+------------------------+---------------+-----------+");
        int stt = 1;
        for (SanPhamThoiTrang product : shoppingList) {
            String fName = product.getTenSP();
            if (fName != null && fName.length() > 22) {
                fName = fName.substring(0, 19) + "...";
            }
            System.out.printf("| %-2d | %-10s | %-8s | %-22s | %13s | %9s |\n",
                    stt++, product.getType(), product.getMaSP(), fName,
                    SanPhamThoiTrang.formatSo(product.getGiaBan()),
                    SanPhamThoiTrang.formatSo(product.VATcalculation()));
        }
        System.out.println("+----+------------+----------+------------------------+---------------+-----------+");
        if (this.person instanceof IGiamGiaKhachHang) {
            System.out.printf("| Da ap dung giam gia cho doi tuong: %-44s |\n", this.person.getClass().getSimpleName());
            System.out.println("+---------------------------------------------------------------------------------+");
        }
        double finalTotal = calculateTotalPrice();
        System.out.printf("| TONG THANH TOAN (DA TINH VAT VA GIAM GIA): %36s |\n", SanPhamThoiTrang.formatTien(finalTotal));
        System.out.println("+---------------------------------------------------------------------------------+\n");

        for (SanPhamThoiTrang product : shoppingList) {
            if (product instanceof IBaoHanh) {
                IBaoHanh warrantyItem = (IBaoHanh) product;
                warrantyItem.printWarrantyCard();
            }
        }
    }
}
