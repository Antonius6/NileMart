package org.route;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    void main(){


        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Scanner in = new Scanner(System.in);
            Map<Integer, ShoppingCart> shoppingCarts = new HashMap<>();

            int option;
            do {
                nileMartMenu();
                option = readInt(in);
                try {
                    switch (option) {
                        case 1 -> showProducts(context);
                        case 2 -> createCart(context, in, shoppingCarts);
                        case 3 -> addProductToCart(in, shoppingCarts);
                        case 4 -> showCart(shoppingCarts, in);
                        case 5 -> checkout(in, shoppingCarts, context);
                    }
                }catch (StoreException e){
                    System.out.println(e.getMessage());
                }

            }
            while (option != 0);


        }

    }

    static int readInt(Scanner in) {
        while (!in.hasNextInt()) {
            System.out.println("Invalid input. please enter a number ");
            in.next();
        }
        int value = in.nextInt();
        in.nextLine();
        return value;
    }

    static double readDouble(Scanner in) {
        while (!in.hasNextDouble()) {
            System.out.println("Invalid input. please enter a number ");
            in.next();
        }
        double value = in.nextDouble();
        in.nextLine();
        return value;
    }

    static String readString(Scanner in) {
        while (!in.hasNextLine()) {
            System.out.println("Invalid input. please enter a line ");
            in.next();
        }
        return in.nextLine();

    }

    static void nileMartMenu(){
        System.out.println("=========== NILEMART ============");
        System.out.println("1. Show products");
        System.out.println("2. Create a cart");
        System.out.println("3. Add a product to a cart");
        System.out.println("4. Show a cart");
        System.out.println("5. Check out a cart");
        System.out.println("0. Exit");
    }

    static void showProducts(AnnotationConfigApplicationContext context) {
        ProductService productService = context.getBean(ProductService.class);
        productService.finAll().forEach(System.out::println);
    }
    static void createCart(AnnotationConfigApplicationContext  context, Scanner in, Map<Integer, ShoppingCart> shoppingCarts ){
        System.out.println("Please enter CardId");
        int cardId = readInt(in);
        ShoppingCart cart = context.getBean(ShoppingCart.class);
        shoppingCarts.put(cardId, cart);

    }
    static void addProductToCart( Scanner in, Map<Integer, ShoppingCart> shoppingCarts){
        System.out.println("Please enter cardId");
        int cardId = readInt(in);
        if (!shoppingCarts.containsKey(cardId))
            throw new StoreException("Invalid cardId");
        System.out.println("Please enter skuProduct ");
        String skuProduct = readString(in);
        System.out.println("Please enter quantity ");
        int quantity = readInt(in);
        shoppingCarts.get(cardId).addItem(skuProduct, quantity);


    }
    static void showCart(Map<Integer, ShoppingCart> shoppingCarts, Scanner in) {
        System.out.println("Please enter cartId");
        int cardId = readInt(in);
        if (!shoppingCarts.containsKey(cardId))
            throw new StoreException("Invalid cartId");
        ShoppingCart cart = shoppingCarts.get(cardId);
        System.out.println("Cart Items: " + cart.getItems());
    }
    static void  checkout(Scanner in, Map<Integer, ShoppingCart> shoppingCarts,AnnotationConfigApplicationContext context){
        System.out.print("Enter cart ID to checkout: ");
        int checkoutCartId = readInt(in);
        ShoppingCart checkoutCart = shoppingCarts.get(checkoutCartId);
        if (checkoutCart == null)
            throw new StoreException("Cart not found");

        System.out.print("Enter Governorate (e.g., Giza, Cairo, Aswan): ");
        String govInput = readString(in);

        Governorate governorate = Governorate.fromText(govInput);
        var checkoutService = context.getBean(CheckoutService.class);
        checkoutCart.setCustomer(new Customer("Mona", "01012345678", "mona@example.com"));
        checkoutService.placeOrder(checkoutCart, governorate);
        System.out.println("Checkout completed successfully.");
    }
}
