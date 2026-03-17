package busybee.customer.service;

import busybee.customer.model.CustomerRow;
import busybee.util.ValidationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerCreateService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // =========================
    // VALIDATION
    // =========================
    public String validateCustomer(String name, String phone, String email) {

        if (!ValidationUtils.isNotEmpty(name)) {
            return ValidationUtils.createRequiredFieldError("Customer name");
        }

        if (!ValidationUtils.isNotEmpty(phone)) {
            return ValidationUtils.createRequiredFieldError("Phone number");
        }

        if (!ValidationUtils.isValidPhoneNumber(phone)) {
            return ValidationUtils.createInvalidFormatError("Phone number");
        }

        if (!ValidationUtils.isNotEmpty(email)
                || !ValidationUtils.isValidEmail(email)) {
            return ValidationUtils.createInvalidFormatError("Email address");
        }

        return null; // hợp lệ
    }

    // =========================
    // CREATE
    // =========================
    public CustomerRow createCustomer(String name, String phone, String email,
                                      String address, String status) {

        String createdAt = LocalDateTime.now().format(FORMATTER);

        return new CustomerRow(
    0, // id tạm
    name,
    phone,
    email,
    address,
    status,
    createdAt
);
    }

    // =========================
    // UPDATE
    // =========================
    public void updateCustomer(CustomerRow customer,
                               String name, String phone, String email,
                               String address, String status) {

        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setStatus(status);

    }
}