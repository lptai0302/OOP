import java.util.*;

public class Main {
    private static void choNhanEnter(Scanner scanner) {
        System.out.print("\nNhan Enter de quay lai menu...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        QuanLyCuaHang store = new QuanLyCuaHang();
        Scanner scanner = new Scanner(System.in);

        store.loadDataFromFile("products.txt");

        while (true) {
            System.out.println("<-----------------Quan Ly Cua Hang Thoi Trang----------------->");
            System.out.println("1. Them san pham");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Tim kiem san pham");
            System.out.println("4. Cap nhat gia san pham");
            System.out.println("5. Hien thi tat ca san pham");
            System.out.println("6. Xem chuong trinh khuyen mai");
            System.out.println("7. Them khach hang VIP hoac Nhan vien");
            System.out.println("8. Thanh toan hoa don");
            System.out.println("0. Luu va thoat");
            System.out.print("Nhap lua chon cua ban: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Nhap loai san pham (AoKhoac / QuanJean / PhuKien / AoThun): ");
                        String type = scanner.nextLine().trim();
                        if (!type.equalsIgnoreCase("AoKhoac") && !type.equalsIgnoreCase("QuanJean")
                                && !type.equalsIgnoreCase("Jeans") && !type.equalsIgnoreCase("PhuKien")
                                && !type.equalsIgnoreCase("Accessory") && !type.equalsIgnoreCase("AoThun")
                                && !type.equalsIgnoreCase("TShirt")) {
                            System.out.println("Loai san pham khong hop le.");
                            choNhanEnter(scanner);
                            break;
                        }

                        String code = store.taoMaSPTuDong(type);
                        System.out.println("Ma san pham duoc tao tu dong: " + code);

                        System.out.println("Nhap ten san pham: ");
                        String name = scanner.nextLine();
                        System.out.println("Nhap gia san pham: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        if (type.equalsIgnoreCase("AoKhoac")) {
                            System.out.println("Ao co non khong? (true/false): ");
                            boolean hasHat = scanner.nextBoolean();
                            scanner.nextLine();
                            store.addProduct(new AoKhoac("AoKhoac", code, name, price, hasHat));
                            System.out.println("Da them ao khoac thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("QuanJean") || type.equalsIgnoreCase("Jeans")) {
                            System.out.println("Nhap kieu dang: ");
                            String style = scanner.nextLine();
                            store.addProduct(new QuanJean("QuanJean", code, name, price, style));
                            System.out.println("Da them quan jean thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("PhuKien") || type.equalsIgnoreCase("Accessory")) {
                            System.out.println("Nhap chat lieu: ");
                            String material = scanner.nextLine();
                            store.addProduct(new PhuKien("PhuKien", code, name, price, material));
                            System.out.println("Da them phu kien thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("AoThun") || type.equalsIgnoreCase("TShirt")) {
                            System.out.println("Nhap kich co (S/M/L/XL): ");
                            String sz = scanner.nextLine();
                            store.addProduct(new AoThun("AoThun", code, name, price, sz));
                            System.out.println("Da them ao thun thanh cong. Ma SP: " + code);
                        }
                        choNhanEnter(scanner);
                        break;
                    case 2:
                        System.out.println("Nhap ma san pham can xoa: ");
                        String deleteCode = scanner.nextLine();
                        store.removeProduct(deleteCode);
                        choNhanEnter(scanner);
                        break;
                    case 3:
                        while (true) {
                            System.out.println("Nhap ma san pham can tim: ");
                            String searchCode = scanner.nextLine();
                            store.searchProducts(searchCode);
                            System.out.print("Ban co muon tim tiep khong? (1: Tim tiep / 0: Quay lai menu): ");
                            String opt = scanner.nextLine().trim();
                            if (opt.equals("0") || opt.equalsIgnoreCase("n")) {
                                break;
                            }
                        }
                        choNhanEnter(scanner);
                        break;
                    case 4:
                        System.out.println("Nhap ma san pham can cap nhat: ");
                        String updateCode = scanner.nextLine();
                        System.out.println("Nhap gia moi: ");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();
                        store.updatePrice(updateCode, newPrice);
                        choNhanEnter(scanner);
                        break;
                    case 5:
                        System.out.println("Danh sach tat ca san pham: ");
                        store.displayAllProducts();
                        choNhanEnter(scanner);
                        break;
                    case 6:
                        store.runAPromotion();
                        choNhanEnter(scanner);
                        break;
                    case 7:
                        System.out.println("1. Them Khach hang VIP | 2. Them Nhan vien: ");
                        int cType = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Nhap ho ten: ");
                        String cName = scanner.nextLine();
                        System.out.println("Nhap so dien thoai: ");
                        String cPhone = scanner.nextLine();
                        if (cType == 2) {
                            System.out.println("Nhap ma nhan vien: ");
                            String staffId = scanner.nextLine();
                            store.addVIPOrStaff(new NhanVien(cName, cPhone, staffId));
                            System.out.println("Da them nhan vien thanh cong.");
                        } else {
                            System.out.println("Nhap ma the VIP: ");
                            String vipCardCode = scanner.nextLine();
                            store.addVIPOrStaff(new KhachHangVIP(cName, cPhone, vipCardCode));
                            System.out.println("Da them khách hang VIP thanh cong.");
                        }
                        choNhanEnter(scanner);
                        break;
                    case 8:
                        System.out.println("--- TIEN HANH THANH TOAN ---");
                        ArrayList<SanPhamThoiTrang> cart = new ArrayList<>();

                        while (true) {
                            System.out.println("Nhap ma san pham muon mua (hoac nhap '0' de dung): ");
                            String pCode = scanner.nextLine();
                            if (pCode.equals("0")) {
                                break;
                            }
                            SanPhamThoiTrang product = store.getProductByCode(pCode);
                            if (product != null) {
                                cart.add(product);
                                System.out.println("Da them " + product.getTenSP() + " vao gio hang.");
                            } else {
                                System.out.println("Khong tim thay san pham.");
                            }
                        }
                        if (cart.isEmpty()) {
                            System.out.println("Gio hang rong. Huy quy trinh thanh toan.");
                            choNhanEnter(scanner);
                            break;
                        }
                        System.out.print("Khach hang co tai khoan VIP/Nhan vien khong? Nhap SDT (hoac '0' neu la khach vang lai): ");
                        String phone = scanner.nextLine();
                        Nguoi currentPerson = null;

                        if (!phone.equals("0")) {
                            currentPerson = store.getVIPOrStaffByPhone(phone);
                            if (currentPerson == null) {
                                System.out.println("Khong tim thay VIP/Nhan vien voi so dien thoai nay.");
                            }
                        }

                        String newBillCode = "HD" + System.currentTimeMillis();
                        HoaDon newbill = new HoaDon(newBillCode, currentPerson);
                        for (SanPhamThoiTrang product : cart) {
                            newbill.addProduct(product);
                        }
                        newbill.printBill();
                        choNhanEnter(scanner);
                        break;
                    case 0:
                        store.saveDataIntoFile();
                        System.out.println("Da luu du lieu. Thoat chuong trinh.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Lua chon khong hop le. Vui long thu lai.");
                        choNhanEnter(scanner);
                }
            } catch (InputMismatchException e) {
                System.out.println("Du lieu nhap khong hop le. Vui long nhap so.");
                scanner.nextLine();
                choNhanEnter(scanner);
            }
        }
    }
}
