import java.util.ArrayList;

public class Bill {
    private String billCode;
    private Costumer costumer;
    private ArrayList<SanPhamThoiTrang> shoppingList;

    public Bill(String billCode, Costumer customer) {
        this.billCode = billCode;
        this.costumer = customer;
        this.shoppingList = new ArrayList<>();
    }

    public void addProduct(SanPhamThoiTrang product) {
        shoppingList.add(product);
        System.out.println("Added product: " + product.getTenSP() + " to the bill.");
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (SanPhamThoiTrang product :shoppingList) {
            if (product instanceof IDiscount) {
                IDiscount discountProduct = (IDiscount) product;
                totalPrice += discountProduct.calculateAfterDiscount(10);
            } else {
                totalPrice += product.getGiaBan();
            }
        }
        if(this.costumer != null && this.costumer instanceof Staff) {
            Staff staff = (Staff) this.costumer;
            totalPrice = staff.calculateAfterDiscount(totalPrice);
            System.out.println("Applied Staff discount");
        } else
        
        if (this.costumer != null && this.costumer instanceof CostumerVIP) {
            CostumerVIP vip = (CostumerVIP) this.costumer;
            totalPrice = vip.calculateAfterDiscount(totalPrice);
            System.out.println("Applied VIP discount");
        } 

        return totalPrice;
    }

    public void printBill() {
        System.out.println("<------------------- BILL ------------------->");
        System.out.println("Bill Code: " + billCode);
        if (costumer != null) {
            System.out.println("Customer Name: " + costumer.getName());
            System.out.println("Customer Phone: " + costumer.getNumberPhone());
        } else {
            System.out.println("walk-in customers");
        }
        System.out.println("Products in the bill:");
        for (SanPhamThoiTrang product : shoppingList) {
            System.out.println("Product Name: " + product.getTenSP() + " | Product Price: " + product.getGiaBan());
        }

        System.out.println("<--------------------------------------------->");
        System.out.println("Total Price: " + calculateTotalPrice());
        System.out.println("<--------------------------------------------->");
    }
}
