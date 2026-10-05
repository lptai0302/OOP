import java.io.*;
import java.util.ArrayList;

public class ManageStore {
    private ArrayList<SanPhamThoiTrang> wareHouse;

    private ArrayList<CostumerVIP> listCostumerVIP;

    public ManageStore() {
        this.wareHouse = new ArrayList<>();
        this.listCostumerVIP = new ArrayList<>();
    }

    public void addProduct(SanPhamThoiTrang product) {
        wareHouse.add(product);
    }

    public void displayAllProducts() {
        if (wareHouse.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        for (SanPhamThoiTrang product : wareHouse) {
            product.HienThiThongTin();
        }
    }

    public void removeProduct(String MaSP) {
        for (int i = 0; i < wareHouse.size(); i++) {
            if (wareHouse.get(i).getMaSP().equals(MaSP)) {
                wareHouse.remove(i);
                System.out.println("Deleted product with code: " + MaSP);
                return;
            }
        }
        System.out.println("Product with code " + MaSP + " not found.");
    }

    public void searchProducts(String MaSP) {
        for (SanPhamThoiTrang product : wareHouse) {
            if (product.getMaSP().equals(MaSP)) {
                product.HienThiThongTin();
                return;
            }
        }
        System.out.println("Product with code " + MaSP + " not found.");
    }

    public SanPhamThoiTrang getProductByCode(String MaSP) {
    for (SanPhamThoiTrang product : wareHouse) {
        if (product.getMaSP().equals(MaSP)) {
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
            System.out.println("Successful Save");
        } catch (IOException e) {
            System.out.println("Error occurred while saving data to file.");
        }
    }

    public void loadDataFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String Line;

            while ((Line = reader.readLine()) != null) {
                String[] parts = Line.split(",");
                
                String type = parts[0];
                String code = parts[1];
                String name = parts[2];
                double price = Double.parseDouble(parts[3]);
                switch (type) {
                    case "AoKhoac":
                        boolean haveHat = Boolean.parseBoolean(parts[4]);
                        AoKhoac jacket = new AoKhoac(type, code, name, price, haveHat);
                        wareHouse.add(jacket);
                        break;
                    case "Jeans":
                        String style = parts[4];
                        Jeans jeans = new Jeans(type, code, name, price, style);
                        wareHouse.add(jeans);
                        break;
                    default:
                        System.out.println("Unknown product type: " + type);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while loading data from file.");
        }

    }

    public void updatePrice(String MaSP, Double newPrice) {
        for (SanPhamThoiTrang product : wareHouse) {
            if(product.getMaSP().equals(MaSP)) {
                boolean isUpdated = product.setGiaBan(newPrice);
                if (isUpdated) {
                    System.out.println("Updated price of product with code " + MaSP + " to " + newPrice);
                } else {
                    System.out.println("Failed to update price.");
                }
            }
        }
        System.out.println("Product with code " + MaSP + " not found.");
    }

    // public void discountProgram() {
    //     System.out.println("Discount Program: ");
    //     for (SanPhamThoiTrang product : wareHouse) {
    //         if (product instanceof IDiscount) {
    //             IDiscount discountProduct = (IDiscount) product;
    //             //GIẢM 10%
    //             double newPrice = discountProduct.calculateAfterDiscount(10);
    //             System.out.println("Product Code: " + product.getMaSP() + " | Original Price: " + product.getGiaBan() + " | Price after 10% discount: " + newPrice);
    //         } else {
    //             System.out.println("Product Code: " + product.getMaSP() + " | Price: " + product.getGiaBan() + " | No discount available.");
    //         }
    //     }
    // }

    public void addCostumerVIP(CostumerVIP vip) {
        listCostumerVIP.add(vip);
    }

    public CostumerVIP getVIPByPhone(String phone) {
        for (CostumerVIP vip : listCostumerVIP) {
            if (vip.getNumberPhone().equals(phone)) {
                return vip;
            }
        }
        return null;
    }

    public void runAPromotion() {
        ArrayList<IDiscount> discountProducts = new ArrayList<>();

        for (SanPhamThoiTrang product : wareHouse) {
            if (product instanceof IDiscount) {
                discountProducts.add((IDiscount) product);
            }
        }

        for (IDiscount object : discountProducts) {
            double newPrice = object.calculateAfterDiscount(10);
        }
        System.out.println("Discounted the object on the list");
    }
}
