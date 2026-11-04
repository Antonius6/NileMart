package org.route;

public record Customer(String fullName,String phone,String email) {
    public Customer {
        if (fullName == null || fullName.trim().isBlank()) {
            throw new StoreException("Customer full name cannot be null or blank");
        }
        if (phone == null || phone.trim().isBlank()) {
            throw new StoreException("Customer phone cannot be null or blank");
        }

    }
}
