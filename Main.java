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
                String choiceStr = scanner.nextLine().trim();
                if (choiceStr.isEmpty()) {
                    continue;
                }
                int choice;
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    System.out.println("Du lieu nhap khong hop le. Vui long nhap so.");
                    choNhanEnter(scanner);
                    continue;
                }

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

                        String name;
                        while (true) {
                            System.out.println("Nhap ten san pham: ");
                            name = scanner.nextLine().trim().replace(",", " ");
                            if (!name.isEmpty()) {
                                break;
                            }
                            System.out.println("Ten san pham khong duoc de trong!");
                        }

                        double price;
                        while (true) {
                            System.out.println("Nhap gia san pham: ");
                            try {
                                price = Double.parseDouble(scanner.nextLine().trim());
                                if (price >= 0) {
                                    break;
                                }
                                System.out.println("Gia san pham phai lon hon hoac bang 0!");
                            } catch (NumberFormatException e) {
                                System.out.println("Gia khong hop le, vui long nhap so!");
                            }
                        }

                        if (type.equalsIgnoreCase("AoKhoac")) {
                            System.out.println("Ao co non khong? (1: Co / 0: Khong): ");
                            String hatInput = scanner.nextLine().trim().toLowerCase();
                            boolean hasHat = hatInput.equals("1") || hatInput.equals("co")
                                    || hatInput.equals("true") || hatInput.equals("y");
                            store.addProduct(new AoKhoac("AoKhoac", code, name, price, hasHat));
                            System.out.println("Da them ao khoac thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("QuanJean") || type.equalsIgnoreCase("Jeans")) {
                            String style;
                            while (true) {
                                System.out.println("Nhap kieu dang: ");
                                style = scanner.nextLine().trim().replace(",", " ");
                                if (!style.isEmpty()) {
                                    break;
                                }
                                System.out.println("Kieu dang khong duoc de trong!");
                            }
                            store.addProduct(new QuanJean("QuanJean", code, name, price, style));
                            System.out.println("Da them quan jean thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("PhuKien") || type.equalsIgnoreCase("Accessory")) {
                            String material;
                            while (true) {
                                System.out.println("Nhap chat lieu: ");
                                material = scanner.nextLine().trim().replace(",", " ");
                                if (!material.isEmpty()) {
                                    break;
                                }
                                System.out.println("Chat lieu khong duoc de trong!");
                            }
                            store.addProduct(new PhuKien("PhuKien", code, name, price, material));
                            System.out.println("Da them phu kien thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("AoThun") || type.equalsIgnoreCase("TShirt")) {
                            System.out.println("Nhap kich co (S/M/L/XL): ");
                            String sz = scanner.nextLine().trim();
                            store.addProduct(new AoThun("AoThun", code, name, price, sz));
                            System.out.println("Da them ao thun thanh cong. Ma SP: " + code);
                        }
                        choNhanEnter(scanner);
                        break;
                    case 2:
                        System.out.println("Nhap ma san pham can xoa: ");
                        String deleteCode = scanner.nextLine().trim();
                        store.removeProduct(deleteCode);
                        choNhanEnter(scanner);
                        break;
                    case 3:
                        while (true) {
                            System.out.println("Nhap ma san pham can tim: ");
                            String searchCode = scanner.nextLine().trim();
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
                        String updateCode = scanner.nextLine().trim();
                        if (store.getProductByCode(updateCode) == null) {
                            System.out.println("Khong tim thay san pham co ma " + updateCode + ".");
                            choNhanEnter(scanner);
                            break;
                        }
                        double newPrice;
                        while (true) {
                            System.out.println("Nhap gia moi: ");
                            try {
                                newPrice = Double.parseDouble(scanner.nextLine().trim());
                                if (newPrice >= 0) {
                                    break;
                                }
                                System.out.println("Gia san pham phai lon hon hoac bang 0!");
                            } catch (NumberFormatException e) {
                                System.out.println("Gia khong hop le, vui long nhap so!");
                            }
                        }
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
                        String cTypeStr = scanner.nextLine().trim();
                        int cType = cTypeStr.equals("2") ? 2 : 1;

                        String cName;
                        while (true) {
                            System.out.println("Nhap ho ten: ");
                            cName = scanner.nextLine().trim();
                            if (!cName.isEmpty()) {
                                break;
                            }
                            System.out.println("Ho ten khong duoc de trong!");
                        }

                        String cPhone;
                        while (true) {
                            System.out.println("Nhap so dien thoai: ");
                            cPhone = scanner.nextLine().trim();
                            if (cPhone.isEmpty()) {
                                System.out.println("So dien thoai khong duoc de trong!");
                                continue;
                            }
                            if (store.getVIPOrStaffByPhone(cPhone) != null) {
                                System.out.println("Loi: So dien thoai " + cPhone + " da ton tai trong he thong!");
                                cPhone = null;
                                break;
                            }
                            break;
                        }
                        if (cPhone == null) {
                            choNhanEnter(scanner);
                            break;
                        }

                        if (cType == 2) {
                            String staffId;
                            while (true) {
                                System.out.println("Nhap ma nhan vien: ");
                                staffId = scanner.nextLine().trim();
                                if (!staffId.isEmpty()) {
                                    break;
                                }
                                System.out.println("Ma nhan vien khong duoc de trong!");
                            }
                            store.addVIPOrStaff(new NhanVien(cName, cPhone, staffId));
                            System.out.println("Da them nhan vien thanh cong.");
                        } else {
                            String vipCardCode;
                            while (true) {
                                System.out.println("Nhap ma the VIP: ");
                                vipCardCode = scanner.nextLine().trim();
                                if (!vipCardCode.isEmpty()) {
                                    break;
                                }
                                System.out.println("Ma the VIP khong duoc de trong!");
                            }
                            store.addVIPOrStaff(new KhachHangVIP(cName, cPhone, vipCardCode));
                            System.out.println("Da them khach hang VIP thanh cong.");
                        }
                        choNhanEnter(scanner);
                        break;
                    case 8:
                        System.out.println("--- TIEN HANH THANH TOAN ---");
                        ArrayList<SanPhamThoiTrang> cart = new ArrayList<>();

                        while (true) {
                            System.out.println("Nhap ma san pham muon mua (hoac nhap '0' de dung): ");
                            String pCode = scanner.nextLine().trim();
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
                        String phone = scanner.nextLine().trim();
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
            } catch (Exception e) {
                System.out.println("Da xay ra loi: " + e.getMessage());
                choNhanEnter(scanner);
            }
        }
    }
}
