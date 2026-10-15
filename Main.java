import java.util.*;

public class Main {
    public static void main(String[] args) {
        ManageStore store = new ManageStore();
        Scanner scanner = new Scanner(System.in);

        store.loadDataFromFile("products.txt");

        while (true) {
            System.out.println("<-----------------Fashion Store Management----------------->");
            System.out.println("1. Add product");
            System.out.println("2. Delete product");
            System.out.println("3. Search product");
            System.out.println("4. Update product");
            System.out.println("5. Display all products");
            System.out.println("6. Run promotion");
            System.out.println("7. Add VIP customer || Staff");
            System.out.println("8. Process the payment");
            System.out.println("0. Save and exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
    
                switch (choice) {
                    case 1:
                        System.out.println("Enter product type (AoKhoac / Jeans / Accessory / TShirt): ");
                        String type = scanner.nextLine();
                        
                        // THUỘC TÍNH CHUNG
                        System.out.println("Enter product code: ");
                        String code = scanner.nextLine();
                        if (store.getProductByCode(code) != null) {
                            System.out.println("Error: Product code already exists!");
                            break;
                        }
                        System.out.println("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.println("Enter product price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        //THUỘC TÍNH RIÊNG
                        if (type.equalsIgnoreCase("AoKhoac")) {
                            System.out.println("Does it have a hat? (true/false): ");
                            boolean hasHat = scanner.nextBoolean();
                            scanner.nextLine();
                            store.addProduct(new AoKhoac("AoKhoac", code, name, price, hasHat));
                            System.out.println("Added item");
                        } else if (type.equalsIgnoreCase("Jeans")) {
                            System.out.println("Enter style: ");
                            String style = scanner.nextLine();
                            store.addProduct(new Jeans("Jeans", code, name, price, style));
                            System.out.println("Added item");
                        } else if (type.equalsIgnoreCase("Accessory")) {
                            System.out.println("Enter Material: ");
                            String material = scanner.nextLine();
                            store.addProduct(new Accessory("Accessory", code, name, price, material));
                            System.out.println("Added item");
                        } else if (type.equalsIgnoreCase("TShirt")) {
                            System.out.println("Enter Size: ");
                            String sz = scanner.nextLine();
                            store.addProduct(new TShirt("TShirt", code, name, price, sz));
                            System.out.println("Added item");
                        } else {
                            System.out.println("Invalid product type.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter product code to delete: ");
                        String deleteCode = scanner.nextLine();
                        store.removeProduct(deleteCode);
                        break;
                    case 3:
                        System.out.println("Enter product code to search: ");
                        String searchCode = scanner.nextLine();
                        store.searchProducts(searchCode);
                        break;
                    case 4:
                        System.out.println("Enter product code to update: ");
                        String updateCode = scanner.nextLine();
                        System.out.println("Enter new product price: ");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();
                        store.updatePrice(updateCode, newPrice);
                        break;
                    case 5:
                        System.out.println("Displaying all products: ");
                        store.displayAllProducts();
                        break;
                    case 6:
                        store.runAPromotion();
                        System.out.println("Promotion applied");
                        break;
                    case 7:
                        System.out.println("1. Add VIP Customer | 2. Add Staff: ");
                        int cType = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter name: ");
                        String cName = scanner.nextLine();
                        System.out.println("Enter phone number: ");
                        String cPhone = scanner.nextLine();
                        if (cType == 2) {
                            System.out.println("Enter Staff ID: ");
                            String staffId = scanner.nextLine();
                            store.addCostumerVIP(new Staff(cName, cPhone, staffId));
                            System.out.println("Staff added.");
                        } else {
                            System.out.println("Enter VIP card code: ");
                            String vipCardCode = scanner.nextLine();
                            store.addCostumerVIP(new CostumerVIP(cName, cPhone, vipCardCode));
                            System.out.println("VIP customer added.");
                        }
                        break;
                    case 8:
                        System.out.println("PROCESSING PAYMENT");
                        ArrayList<SanPhamThoiTrang> cart = new ArrayList<>();

                        while (true) {
                            System.out.println("Enter product code to add (or '0' to stop): ");
                            String pCode = scanner.nextLine();
                            if (pCode.equals("0")) {
                                break;
                            }
                            SanPhamThoiTrang product = store.getProductByCode(pCode);
                            if (product != null) {
                                cart.add(product);
                                System.out.println("Added " + product.getTenSP() + " to the cart.");
                            } else {
                                System.out.println("Product not found.");
                            }

                        }
                        if (cart.isEmpty()) {
                            System.out.println("No products in the cart. Exiting payment process.");
                            break;
                        }
                        System.out.print("Does customer have VIP account? Enter phone number (or '0' to skip): ");
                        String phone = scanner.nextLine();
                        Costumer currentCustomer = null;

                        if(!phone.equals("0")) {
                            currentCustomer = store.getVIPByPhone(phone);
                            if (currentCustomer == null) {
                                System.out.println("No VIP customer found with that phone number.");
                            }
                        }

                        String newBillCode = "B" + System.currentTimeMillis();
                        Bill newbill = new Bill(newBillCode, currentCustomer);
                        for (SanPhamThoiTrang product : cart) {
                            newbill.addProduct(product);
                        }
                        newbill.printBill();
                        break;
                    case 0:
                        store.saveDataIntoFile();
                        System.out.println("Data saved. Exiting the program.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
