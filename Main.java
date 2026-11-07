import java.util.*;

public class Main {
    private static void choNhanEnter(Scanner scanner) {
        System.out.print("\nNhan Enter de quay lai menu...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        QuanLyCuaHang store = new QuanLyCuaHang();
        Scanner scanner = new Scanner(System.in);

        store.loadDataFromFile("products.txt");

        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            System.out.println("<-----------------Quan Ly Cua Hang Thoi Trang----------------->");
            System.out.println("1. Them san pham");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Tim kiem san pham");
            System.out.println("4. Sua thong tin san pham");
            System.out.println("5. Hien thi tat ca san pham");
            System.out.println("6. Xem chuong trinh khuyen mai");
            System.out.println("7. Quan ly Khach hang / Nhan vien");
            System.out.println("8. Thanh toan hoa don & In phieu bao hanh");
            System.out.println("9. Xem danh sach Nha cung cap");
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
                        System.out.println("Nhap loai san pham (AoKhoac / QuanJean / PhuKien / AoThun / VayDam / GiayDep): ");
                        String type = scanner.nextLine().trim();
                        if (!type.equalsIgnoreCase("AoKhoac") && !type.equalsIgnoreCase("QuanJean")
                                && !type.equalsIgnoreCase("Jeans") && !type.equalsIgnoreCase("PhuKien")
                                && !type.equalsIgnoreCase("Accessory") && !type.equalsIgnoreCase("AoThun")
                                && !type.equalsIgnoreCase("TShirt") && !type.equalsIgnoreCase("VayDam")
                                && !type.equalsIgnoreCase("GiayDep")) {
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
                                System.out.println("Nhap kieu dang (Skinny/Baggy/OngRong...): ");
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
                            String sz;
                            while (true) {
                                System.out.println("Nhap kich co (S/M/L/XL): ");
                                sz = scanner.nextLine().trim();
                                if (sz.equalsIgnoreCase("S") || sz.equalsIgnoreCase("M") || sz.equalsIgnoreCase("L") || sz.equalsIgnoreCase("XL")) {
                                    break;
                                }
                                System.out.println("Kich co khong hop le! Vui long chi nhap S, M, L hoac XL.");
                            }
                            store.addProduct(new AoThun("AoThun", code, name, price, sz));
                            System.out.println("Da them ao thun thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("VayDam")) {
                            String chatLieu;
                            while (true) {
                                System.out.println("Nhap chat lieu vai (Voan/Lua/Cotton...): ");
                                chatLieu = scanner.nextLine().trim().replace(",", " ");
                                if (!chatLieu.isEmpty()) {
                                    break;
                                }
                                System.out.println("Chat lieu vai khong duoc de trong!");
                            }
                            store.addProduct(new VayDam("VayDam", code, name, price, chatLieu));
                            System.out.println("Da them vay dam thanh cong. Ma SP: " + code);
                        } else if (type.equalsIgnoreCase("GiayDep")) {
                            int sizeGiay;
                            while (true) {
                                System.out.println("Nhap size giay (35 - 45): ");
                                try {
                                    sizeGiay = Integer.parseInt(scanner.nextLine().trim());
                                    if (sizeGiay >= 35 && sizeGiay <= 45) {
                                        break;
                                    }
                                    System.out.println("Size giay phai tu 35 den 45!");
                                } catch (NumberFormatException e) {
                                    System.out.println("Size khong hop le, vui long nhap so!");
                                }
                            }
                            store.addProduct(new GiayDep("GiayDep", code, name, price, sizeGiay));
                            System.out.println("Da them giay dep thanh cong. Ma SP: " + code);
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
                            System.out.println("1. Tim kiem theo ma san pham");
                            System.out.println("2. Tim kiem theo ten san pham");
                            System.out.print("Nhap lua chon (1-2, hoac 0 de thoat): ");
                            String sOpt = scanner.nextLine().trim();
                            if (sOpt.equals("0")) {
                                break;
                            }
                            if (sOpt.equals("1")) {
                                System.out.println("Nhap ma san pham can tim: ");
                                String searchCode = scanner.nextLine().trim();
                                store.searchProducts(searchCode);
                            } else if (sOpt.equals("2")) {
                                System.out.println("Nhap tu khoa ten san pham can tim: ");
                                String searchName = scanner.nextLine().trim();
                                store.searchProductsByName(searchName);
                            } else {
                                System.out.println("Lua chon khong hop le.");
                            }
                            System.out.print("Ban co muon tim tiep khong? (1: Tim tiep / 0: Quay lai menu): ");
                            String opt = scanner.nextLine().trim();
                            if (opt.equals("0") || opt.equalsIgnoreCase("n")) {
                                break;
                            }
                        }
                        choNhanEnter(scanner);
                        break;
                    case 4:
                        System.out.println("Nhap ma san pham can sua: ");
                        String updateCode = scanner.nextLine().trim();
                        SanPhamThoiTrang oldSp = store.getProductByCode(updateCode);
                        if (oldSp == null) {
                            System.out.println("Khong tim thay san pham co ma " + updateCode + ".");
                            choNhanEnter(scanner);
                            break;
                        }

                        System.out.println("Thong tin hien tai: " + oldSp.getTenSP() + " | Gia: " + SanPhamThoiTrang.formatTien(oldSp.getGiaBan()) + " | " + oldSp.getThongTinRieng());
                        System.out.println("1. Sua ten san pham | 2. Sua gia ban | 3. Sua ca ten va gia | 4. Sua thuoc tinh rieng: ");
                        String editOpt = scanner.nextLine().trim();

                        String newName = oldSp.getTenSP();
                        double newPrice = oldSp.getGiaBan();

                        if (!editOpt.equals("1") && !editOpt.equals("2") && !editOpt.equals("3") && !editOpt.equals("4")) {
                            System.out.println("Lua chon khong hop le.");
                            choNhanEnter(scanner);
                            break;
                        }

                        if (editOpt.equals("1") || editOpt.equals("3")) {
                            System.out.println("Nhap ten moi (nhan Enter neu muon giu nguyen): ");
                            String inpName = scanner.nextLine().trim().replace(",", " ");
                            if (!inpName.isEmpty()) {
                                newName = inpName;
                            }
                        }

                        if (editOpt.equals("2") || editOpt.equals("3")) {
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
                        }

                        if (editOpt.equals("4")) {
                            if (oldSp instanceof AoKhoac) {
                                System.out.println("Ao co non khong? (1: Co / 0: Khong): ");
                                String hatInput = scanner.nextLine().trim().toLowerCase();
                                boolean hasHat = hatInput.equals("1") || hatInput.equals("co")
                                        || hatInput.equals("true") || hatInput.equals("y");
                                ((AoKhoac) oldSp).setHasHat(hasHat);
                            } else if (oldSp instanceof QuanJean) {
                                System.out.println("Nhap kieu dang moi: ");
                                String st = scanner.nextLine().trim().replace(",", " ");
                                if (!st.isEmpty()) {
                                    ((QuanJean) oldSp).setStyle(st);
                                }
                            } else if (oldSp instanceof PhuKien) {
                                System.out.println("Nhap chat lieu moi: ");
                                String mat = scanner.nextLine().trim().replace(",", " ");
                                if (!mat.isEmpty()) {
                                    ((PhuKien) oldSp).setMaterial(mat);
                                }
                            } else if (oldSp instanceof AoThun) {
                                System.out.println("Nhap kich co moi (S/M/L/XL): ");
                                String sz = scanner.nextLine().trim();
                                ((AoThun) oldSp).setSize(sz);
                            } else if (oldSp instanceof VayDam) {
                                System.out.println("Nhap chat lieu vai moi: ");
                                String cl = scanner.nextLine().trim().replace(",", " ");
                                if (!cl.isEmpty()) {
                                    ((VayDam) oldSp).setChatLieuVai(cl);
                                }
                            } else if (oldSp instanceof GiayDep) {
                                System.out.println("Nhap size giay moi (35 - 45): ");
                                try {
                                    int sg = Integer.parseInt(scanner.nextLine().trim());
                                    ((GiayDep) oldSp).setSizeGiay(sg);
                                } catch (NumberFormatException ignored) {}
                            }
                            store.saveDataIntoFile();
                            System.out.println("Da cap nhat thuoc tinh rieng cua san pham " + updateCode + " thanh cong!");
                        } else {
                            store.updateProduct(updateCode, newName, newPrice);
                        }
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
                        System.out.println("--- QUAN LY KHACH HANG & NHAN VIEN ---");
                        System.out.println("1. Them Khach hang VIP (Giam 20%)");
                        System.out.println("2. Them Khach hang Than thiet (Giam 10%)");
                        System.out.println("3. Them Nhan vien thu ngan (Giam 50%)");
                        System.out.println("4. Them Nhan vien quan ly (Giam 50% + Phu cap)");
                        System.out.println("5. Xem danh sach tat ca Khach hang & Nhan vien");
                        System.out.println("6. Sua ten Khach hang / Nhan vien (theo SDT)");
                        System.out.println("7. Xoa Khach hang / Nhan vien (theo SDT)");
                        System.out.println("8. Tim kiem Khach hang / Nhan vien (theo SDT)");
                        System.out.print("Nhap lua chon cua ban (1-8): ");
                        String cTypeStr = scanner.nextLine().trim();

                        if (cTypeStr.equals("5")) {
                            store.getDsKhachHang().hienThiDanhSach();
                            choNhanEnter(scanner);
                            break;
                        }

                        if (cTypeStr.equals("6")) {
                            System.out.println("Nhap so dien thoai can sua: ");
                            String ph = scanner.nextLine().trim();
                            Nguoi p = store.getVIPOrStaffByPhone(ph);
                            if (p == null) {
                                System.out.println("Khong tim thay nguoi dung co so dien thoai: " + ph);
                            } else {
                                System.out.println("Ten hien tai: " + p.getName());
                                System.out.println("Nhap ten moi: ");
                                String newN = scanner.nextLine().trim().replace(",", " ");
                                if (!newN.isEmpty()) {
                                    store.updateVIPOrStaff(ph, newN);
                                    System.out.println("Da cap nhat ten thanh cong!");
                                } else {
                                    System.out.println("Ten khong duoc de trong!");
                                }
                            }
                            choNhanEnter(scanner);
                            break;
                        }

                        if (cTypeStr.equals("7")) {
                            System.out.println("Nhap so dien thoai can xoa: ");
                            String ph = scanner.nextLine().trim();
                            if (store.removeVIPOrStaff(ph)) {
                                System.out.println("Da xoa nguoi dung co so dien thoai: " + ph);
                            } else {
                                System.out.println("Khong tim thay nguoi dung co so dien thoai: " + ph);
                            }
                            choNhanEnter(scanner);
                            break;
                        }

                        if (cTypeStr.equals("8")) {
                            System.out.println("Nhap so dien thoai can tim: ");
                            String ph = scanner.nextLine().trim();
                            Nguoi p = store.getVIPOrStaffByPhone(ph);
                            if (p != null) {
                                System.out.println("\nKet qua tim kiem nguoi dung:");
                                System.out.println("+--------------------+---------------+---------------------+--------------------+");
                                System.out.println("| Ho va Ten          | So Dien Thoai | Loai Doi Tuong      | Ma The / Ma NV     |");
                                System.out.println("+--------------------+---------------+---------------------+--------------------+");
                                String extra = "-";
                                if (p instanceof KhachHangVIP) {
                                    extra = ((KhachHangVIP) p).getCardCodeVIP();
                                } else if (p instanceof KhachHangThanThiet) {
                                    extra = "Diem: " + ((KhachHangThanThiet) p).getDiemTichLuy();
                                } else if (p instanceof NhanVienQuanLy) {
                                    extra = ((NhanVienQuanLy) p).getStaffID() + " (QL)";
                                } else if (p instanceof NhanVien) {
                                    extra = ((NhanVien) p).getStaffID();
                                }
                                System.out.printf("| %-18s | %-13s | %-19s | %-18s |\n",
                                        SanPhamThoiTrang.formatCell(p.getName(), 18),
                                        p.getNumberPhone(),
                                        p.getClass().getSimpleName(),
                                        extra);
                                System.out.println("+--------------------+---------------+---------------------+--------------------+\n");
                            } else {
                                System.out.println("Khong tim thay nguoi dung co so dien thoai: " + ph);
                            }
                            choNhanEnter(scanner);
                            break;
                        }

                        if (!cTypeStr.equals("1") && !cTypeStr.equals("2") && !cTypeStr.equals("3") && !cTypeStr.equals("4")) {
                            System.out.println("Lua chon khong hop le!");
                            choNhanEnter(scanner);
                            break;
                        }

                        String cName;
                        while (true) {
                            System.out.println("Nhap ho ten: ");
                            cName = scanner.nextLine().trim().replace(",", " ");
                            if (!cName.isEmpty()) {
                                break;
                            }
                            System.out.println("Ho ten khong duoc de trong!");
                        }

                        String cPhone;
                        while (true) {
                            System.out.println("Nhap so dien thoai (9-11 chu so): ");
                            cPhone = scanner.nextLine().trim().replace(",", "");
                            if (cPhone.isEmpty()) {
                                System.out.println("So dien thoai khong duoc de trong!");
                                continue;
                            }
                            if (!cPhone.matches("^0[0-9]{8,10}$")) {
                                System.out.println("So dien thoai khong hop le (phai bat dau bang 0 va co 9-11 chu so)!");
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

                        if (cTypeStr.equals("1")) {
                            System.out.println("Nhap ma the VIP: ");
                            String vipCardCode = scanner.nextLine().trim().replace(",", " ");
                            store.addVIPOrStaff(new KhachHangVIP(cName, cPhone, vipCardCode));
                            System.out.println("Da them khach hang VIP thanh cong.");
                        } else if (cTypeStr.equals("2")) {
                            System.out.println("Nhap diem tich luy ban dau: ");
                            int diem = 0;
                            try {
                                diem = Integer.parseInt(scanner.nextLine().trim());
                            } catch (NumberFormatException ignored) {}
                            store.addVIPOrStaff(new KhachHangThanThiet(cName, cPhone, diem));
                            System.out.println("Da them khach hang Than thiet thanh cong.");
                        } else if (cTypeStr.equals("3")) {
                            System.out.println("Nhap ma nhan vien: ");
                            String staffId = scanner.nextLine().trim().replace(",", " ");
                            store.addVIPOrStaff(new NhanVien(cName, cPhone, staffId));
                            System.out.println("Da them nhan vien thu ngan thanh cong.");
                        } else if (cTypeStr.equals("4")) {
                            System.out.println("Nhap ma nhan vien quan ly: ");
                            String staffId = scanner.nextLine().trim().replace(",", " ");
                            System.out.println("Nhap phu cap trach nhiem (VND): ");
                            double phuCap = 0;
                            try {
                                phuCap = Double.parseDouble(scanner.nextLine().trim());
                            } catch (NumberFormatException ignored) {}
                            store.addVIPOrStaff(new NhanVienQuanLy(cName, cPhone, staffId, phuCap));
                            System.out.println("Da them nhan vien quan ly thanh cong.");
                        }
                        choNhanEnter(scanner);
                        break;
                    case 8:
                        System.out.println("--- TIEN HANH THANH TOAN ---");
                        String newBillCode = "HD" + System.currentTimeMillis();
                        HoaDon newbill = new HoaDon(newBillCode, null);

                        while (true) {
                            System.out.println("Nhap ma san pham muon mua (hoac nhap '0' de dung): ");
                            String pCode = scanner.nextLine().trim();
                            if (pCode.equals("0")) {
                                break;
                            }
                            SanPhamThoiTrang product = store.getProductByCode(pCode);
                            if (product != null) {
                                System.out.println("Nhap so luong muon mua (Enter de mac dinh 1): ");
                                String slStr = scanner.nextLine().trim();
                                int sl = 1;
                                try {
                                    if (!slStr.isEmpty()) {
                                        int parsed = Integer.parseInt(slStr);
                                        if (parsed > 0) {
                                            sl = parsed;
                                        } else {
                                            System.out.println("So luong phai lon hon 0. Mac dinh chon 1.");
                                        }
                                    }
                                } catch (NumberFormatException ignored) {
                                    System.out.println("So luong khong hop le. Mac dinh chon 1.");
                                }
                                newbill.addProduct(product, sl);
                            } else {
                                System.out.println("Khong tim thay san pham co ma " + pCode + ".");
                            }
                        }
                        if (newbill.getChiTietList().isEmpty()) {
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
                                System.out.println("Khong tim thay VIP/Nhan vien voi so dien thoai nay. Tinh theo khach vang lai.");
                            }
                        }

                        newbill.setPerson(currentPerson);
                        newbill.printBill();
                        newbill.xuatHoaDonRaFile("hoadon.txt");
                        System.out.println("--> Da tu dong luu lich su hoa don vao file: hoadon.txt");
                        choNhanEnter(scanner);
                        break;
                    case 9:
                        store.displayAllSuppliers();
                        choNhanEnter(scanner);
                        break;
                    case 0:
                        store.saveDataIntoFile();
                        System.out.println("Da luu du lieu an toan. Thoat chuong trinh.");
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
