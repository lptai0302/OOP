import java.io.*;
import java.util.ArrayList;

public class QuanLyCuaHang {
    private ArrayList<SanPhamThoiTrang> wareHouse;
    private ArrayList<Nguoi> listVIPAndStaff;

    public QuanLyCuaHang() {
        this.wareHouse = new ArrayList<>();
        this.listVIPAndStaff = new ArrayList<>();
    }

    public String taoMaSPTuDong(String type) {
        String prefix = "SP";
        if (type.equalsIgnoreCase("AoKhoac")) {
            prefix = "AK";
        } else if (type.equalsIgnoreCase("QuanJean") || type.equalsIgnoreCase("Jeans")) {
            prefix = "QJ";
        } else if (type.equalsIgnoreCase("AoThun") || type.equalsIgnoreCase("TShirt")) {
            prefix = "AT";
        } else if (type.equalsIgnoreCase("PhuKien") || type.equalsIgnoreCase("Accessory")) {
            prefix = "PK";
        }

        int maxId = 0;
        for (SanPhamThoiTrang p : wareHouse) {
            if (p.getMaSP() != null && p.getMaSP().toUpperCase().startsWith(prefix)) {
                String numPart = p.getMaSP().substring(prefix.length());
                try {
                    int id = Integer.parseInt(numPart);
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("%s%03d", prefix, maxId + 1);
    }

    public void addProduct(SanPhamThoiTrang product) {
        if (getProductByCode(product.getMaSP()) != null) {
            System.out.println("San pham co ma " + product.getMaSP() + " da ton tai!");
            return;
        }
        wareHouse.add(product);
        saveDataIntoFile();
    }

    public void removeProduct(String MaSP) {
        for (int i = 0; i < wareHouse.size(); i++) {
            if (wareHouse.get(i).getMaSP().equalsIgnoreCase(MaSP)) {
                wareHouse.remove(i);
                saveDataIntoFile();
                System.out.println("Da xoa san pham co ma: " + MaSP);
                return;
            }
        }
        System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
    }

    public void displayAllProducts() {
        if (wareHouse.isEmpty()) {
            System.out.println("Kho hang hien khong co san pham nao.");
            return;
        }
        System.out.println("\n=== DANH SACH SAN PHAM TRONG KHO ===");
        SanPhamThoiTrang.inTieuDeBang();
        for (SanPhamThoiTrang product : wareHouse) {
            product.inDongBang();
        }
        SanPhamThoiTrang.inChanBang();
        System.out.println("Tong so san pham trong kho: " + wareHouse.size() + "\n");
    }

    public void searchProducts(String MaSP) {
        for (SanPhamThoiTrang product : wareHouse) {
            if (product.getMaSP().equalsIgnoreCase(MaSP)) {
                System.out.println("\nKet qua tim kiem:");
                SanPhamThoiTrang.inTieuDeBang();
                product.inDongBang();
                SanPhamThoiTrang.inChanBang();
                System.out.println();
                return;
            }
        }
        System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
    }

    public SanPhamThoiTrang getProductByCode(String MaSP) {
        for (SanPhamThoiTrang product : wareHouse) {
            if (product.getMaSP().equalsIgnoreCase(MaSP)) {
                return product;
            }
        }
        return null;
    }

    public void saveDataIntoFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {
            for (SanPhamThoiTrang product : wareHouse) {
                writer.write(product.getStringSaveFile());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi luu du lieu vao file.");
        }
    }

    public void loadDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("Canh bao: Bo qua dong khong hop le " + lineNumber + ": " + line);
                    continue;
                }

                String type = parts[0];
                String code = parts[1];
                String name = parts[2];
                double price;
                try {
                    price = Double.parseDouble(parts[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Canh bao: Gia khong hop le o dong " + lineNumber + ". Bo qua.");
                    continue;
                }

                switch (type) {
                    case "AoKhoac":
                        boolean haveHat = Boolean.parseBoolean(parts[4]);
                        wareHouse.add(new AoKhoac(type, code, name, price, haveHat));
                        break;
                    case "QuanJean":
                    case "Jeans":
                        String style = parts[4];
                        wareHouse.add(new QuanJean(type, code, name, price, style));
                        break;
                    case "AoThun":
                    case "TShirt":
                        String size = parts[4];
                        wareHouse.add(new AoThun(type, code, name, price, size));
                        break;
                    case "PhuKien":
                    case "Accessory":
                        String material = parts[4];
                        wareHouse.add(new PhuKien(type, code, name, price, material));
                        break;
                    default:
                        System.out.println("Loai san pham khong xac dinh: " + type);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi doc du lieu tu file.");
        }
    }

    public void updatePrice(String MaSP, Double newPrice) {
        for (SanPhamThoiTrang product : wareHouse) {
            if (product.getMaSP().equalsIgnoreCase(MaSP)) {
                boolean isUpdated = product.setGiaBan(newPrice);
                if (isUpdated) {
                    saveDataIntoFile();
                    System.out.println("Da cap nhat gia san pham ma " + MaSP + " thanh " + SanPhamThoiTrang.formatTien(newPrice));
                } else {
                    System.out.println("Cap nhat gia that bai.");
                }
                return;
            }
        }
        System.out.println("Khong tim thay san pham co ma " + MaSP + ".");
    }

    public void runAPromotion() {
        if (wareHouse.isEmpty()) {
            System.out.println("Kho hang hien khong co san pham nao.");
            return;
        }
        System.out.println("\n=== CHUONG TRINH KHUYEN MAI: XEM TRUOC GIAM GIA 10% ===");
        System.out.println("+------------+----------+------------------------+-----------------+-----------+-----------------+");
        System.out.println("| Loai SP    | Ma SP    | Ten San Pham           | Gia Goc         | Giam Gia  | Gia Sau Giam    |");
        System.out.println("+------------+----------+------------------------+-----------------+-----------+-----------------+");
        for (SanPhamThoiTrang product : wareHouse) {
            double discountedPrice = product.calculateAfterDiscount(10);
            String fName = product.getTenSP();
            if (fName != null && fName.length() > 22) {
                fName = fName.substring(0, 19) + "...";
            }
            System.out.printf("| %-10s | %-8s | %-22s | %15s |    10%%    | %15s |\n",
                    product.getType(), product.getMaSP(), fName,
                    SanPhamThoiTrang.formatTien(product.getGiaBan()),
                    SanPhamThoiTrang.formatTien(discountedPrice));
        }
        System.out.println("+------------+----------+------------------------+-----------------+-----------+-----------------+");
        System.out.println("Luu y: Muc giam gia se tu dong duoc ap dung khi xuat hoa don.\n");
    }

    public void addVIPOrStaff(Nguoi person) {
        listVIPAndStaff.add(person);
    }

    public Nguoi getVIPOrStaffByPhone(String phone) {
        for (Nguoi p : listVIPAndStaff) {
            if (p.getNumberPhone().equals(phone)) {
                return p;
            }
        }
        return null;
    }
}
