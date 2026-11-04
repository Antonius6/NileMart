package org.route;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryProductRepository implements ProductRepository{

    private final HashMap<String, Product> store = new HashMap<>();

    @PostConstruct
    void saveProducts() {
        Product p1 = Product.builder()
                .name("Galaxy S25")
                .sku("PH-SAM-S25")
                .price(BigDecimal.valueOf(10000))
                .category("Phone")
                .isActive(true)
                .stockQuantity(8)
                .build();
        Product p2 = Product.builder()
                .name("ASUS TUF F16")
                .sku("LAP-ASUS-F16")
                .price(BigDecimal.valueOf(30000))
                .category("Laptop")
                .isActive(true)
                .stockQuantity(5)
                .build();
        Product p3 = Product.builder()
                .name("Smartphone X5 128GB")
                .sku("PH-X5-128")
                .category("Electronics")
                .price(BigDecimal.valueOf(16000.00))
                .stockQuantity(12)
                .isActive(true)
                .build();

        Product p4 = Product.builder()
                .name("Fast Charger 20W")
                .sku("ACC-CHG-20W")
                .category("Electronics")
                .price(BigDecimal.valueOf(650.00))
                .stockQuantity(40)
                .isActive(true)
                .build();

        Product p5 = Product.builder()
                .name("Wireless Earbuds")
                .sku("ACC-BUDS-6")
                .category("Electronics")
                .price(BigDecimal.valueOf(1100.00))
                .stockQuantity(8)
                .isActive(true)
                .build();

        Product p6 = Product.builder()
                .name("Cotton Galabeya")
                .sku("FSH-GAL-001")
                .category("Fashion")
                .price(BigDecimal.valueOf(900.00))
                .stockQuantity(25)
                .isActive(true)
                .build();

        Product p7 = Product.builder()
                .name("Non-stick Frying Pan 28cm")
                .sku("HOM-PAN-28")
                .category("Home & Kitchen")
                .price(BigDecimal.valueOf(1200.00))
                .stockQuantity(3)
                .isActive(true)
                .build();

        Product p8 = Product.builder()
                .name("Wooden Spice Rack")
                .sku("HOM-SPC-RCK")
                .category("Home & Kitchen")
                .price(BigDecimal.valueOf(750.00))
                .stockQuantity(10)
                .isActive(true)
                .build();

        Product p9 = Product.builder()
                .name("Sun Cream SPF50")
                .sku("BTY-SUN-50")
                .category("Beauty")
                .price(BigDecimal.valueOf(350.00))
                .stockQuantity(0)
                .isActive(true)
                .build();

        Product p10 = Product.builder()
                .name("The Cairo Trilogy")
                .sku("BK-CAI-TRI")
                .category("Books")
                .price(BigDecimal.valueOf(450.00))
                .stockQuantity(15)
                .isActive(true)
                .build();

        store.put(p1.getSku(), p1);
        store.put(p2.getSku(), p2);
        store.put(p3.getSku(), p3);
        store.put(p4.getSku(), p4);
        store.put(p5.getSku(), p5);
        store.put(p6.getSku(), p6);
        store.put(p7.getSku(), p7);
        store.put(p8.getSku(), p8);
        store.put(p9.getSku(), p9);
        store.put(p10.getSku(), p10);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(store.get(sku));
    }

    @Override
    public List<Product> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public void save(Product product) {
        store.put(product.getSku(), product);
    }

    @PreDestroy
    void close() {
        System.out.println("Catalog closed");
    }
}
