package org.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final PricingService pricingService;

    public ProductService(@Qualifier("inMemoryProductRepository") ProductRepository repository,
                          PricingService pricingService) {
        this.repository = repository;
        this.pricingService = pricingService;
    }

    public BigDecimal getPrice(String sku, int qty) {
        Product product = repository.findBySku(sku)
                .filter(Product::isActive)
                .orElseThrow(() -> new RuntimeException("Product isn't available."));
        return pricingService.getPrice(product, qty);
    }

    public List<Product> finAll() {
        return repository.findAll();
    }

    public Optional<Product> findBySku(String sku) {
        if (repository.findBySku(sku).isPresent())
            return repository.findBySku(sku);
        else
            throw new StoreException("Product with sku " + sku + " not found");
    }

    public void reduceStock(String sku, int qty) {
        Optional<Product> product = findBySku(sku);
               int stock=product.get().getStockQuantity();

        if (stock < qty) {
            throw new StoreException("Quantity exceeds stock for product: " + sku);
        }
        product.get().setStockQuantity(stock - qty);
        repository.save(product.orElse(null));
    }

}
