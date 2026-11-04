package org.route;

import jakarta.annotation.PreDestroy;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRegistry {
    private final List<Order> orders = new ArrayList<>();
    private static int  counter = 1;

    public String generateOrderNumber(){

        return "NM-"+ LocalDate.now().
                format(DateTimeFormatter.ofPattern("yyyyMMdd"))
               +"-"+String.format("%05d", counter++);
    }

    public void registerOrder(Order order){
        orders.add(order);
    }
    @PreDestroy
    public void  totalRevenuePerDay(){

        BigDecimal total = BigDecimal.ZERO;
        for (Order order : orders) {
            total = total.add(order.total());
        }

        System.out.println("End of day: "+ orders.size()+" orders, revenue "+ total+" EGP");
    }

}
