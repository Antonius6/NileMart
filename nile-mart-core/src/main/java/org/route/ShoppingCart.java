package org.route;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Scope("prototype")
@Setter
@Getter
public class ShoppingCart {

    private int cartNumber;
    private Customer customer;
    private final ProductService productService;
    private final Map<String, Integer> items = new HashMap<>();
    private final int maxQuantity;

    public ShoppingCart(ProductService productService,
                        @Value("${store.max-quantity-per-product}") int maxQuantityPerProduct) {
        this.productService = productService;
        this.maxQuantity = maxQuantityPerProduct;
    }

    public void addItem(String sku, int qty) {
        Optional<Product> product = productService.findBySku(sku);

        if (qty <= 0)
            throw new StoreException("Quantity must be greater than 0");

        if (product.get().getStockQuantity() <= 0)
            throw new StoreException("Product with sku " + sku + " is out of stock");


        int currentQtyInCart = items.getOrDefault(sku, 0);
        int totalRequestInCart = currentQtyInCart + qty;

        if (totalRequestInCart > maxQuantity)
            throw new StoreException("A line's quantity exceeds the maximum allowed quantity");

        if (totalRequestInCart > product.get().getStockQuantity())
            throw new StoreException("Requested quantity exceeds available stock");

        items.put(sku, totalRequestInCart);

    }

    public BigDecimal subtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> e : items.entrySet()) {
            subtotal = subtotal.add(productService.getPrice(e.getKey(), e.getValue()));
        }
        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }




}
