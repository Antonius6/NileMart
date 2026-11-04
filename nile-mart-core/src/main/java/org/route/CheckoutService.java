package org.route;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final ProductService productService;
    private final ShippingQuoteService shippingQuoteService;
    private final TaxCalculator taxCalculator;
    private final OrderRegistry orderRegistry;
    private final List<NotificationSender> notificationSenders;

    public Order placeOrder(ShoppingCart cart, Governorate governorate) {
        if (cart.isEmpty()) throw new StoreException("Can't place order with empty cart");
        reCheck(cart);

        Map<String, Integer> items = cart.getItems();
        BigDecimal subtotal = cart.subtotal();
        BigDecimal shipping =  shippingQuoteService.calculate(governorate, subtotal);
        BigDecimal vat = taxCalculator.calculateVat(subtotal);
        BigDecimal total = subtotal.add(shipping).add(vat);

        reduceSock(items);

        String orderNumber = orderRegistry.generateOrderNumber();
        Order order = new Order(orderNumber,cart.getCustomer(),governorate,items,subtotal,shipping,vat,total);
        orderRegistry.registerOrder(order);
        String message = String.format(Locale.US,
                "Order %s confirmed. Total %,.2f EGP", orderNumber, total);
        notificationSenders.forEach(ns->ns.send(cart.getCustomer(),message));
        cart.clear();
        return order;
    }

    public void reCheck(ShoppingCart cart) {

        cart.getItems().forEach((sku,qty)->{
            Product product = productService.findBySku(sku).get();
            if (product.getStockQuantity() <= 0)
                throw new StoreException("Product with sku " + sku + " is out of stock");
            else if (qty > product.getStockQuantity())
                throw new StoreException("qty can not exceed stock quantity  the stock is" + product.getStockQuantity());
        });

    }



    public void reduceSock(Map<String, Integer> items) {
            items.forEach(productService::reduceStock);
    }

}
