package org.route;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class ShippingQuoteService {

    private final ShippingCalculator defaultShippingCalculator;
    private final FreeShippingCalculator freeShippingCalculator;
    private final BigDecimal threshold;
    public ShippingQuoteService(ShippingCalculator shippingCalculator,
                                @Qualifier("freeShipping") FreeShippingCalculator freeShippingCalculator,
                                @Value("${store.free-shipping-threshold}")BigDecimal threshold)
    {
        this.defaultShippingCalculator = shippingCalculator;
        this.freeShippingCalculator = freeShippingCalculator;
        this.threshold = threshold;
    }


    public BigDecimal calculate(Governorate g,BigDecimal subtotal) {
        if(g.getZone().isFreeShipping() &&subtotal.compareTo(threshold)>=0)
            return freeShippingCalculator.calculate(g);
        else
            return defaultShippingCalculator.calculate(g);
    }






}

