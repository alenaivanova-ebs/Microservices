package com.ep.customer;

import com.ep.clients.fraud.FraudCheckResponse;
import com.ep.clients.fraud.FraudClient;
import com.ep.clients.notification.NotificationClient;
import com.ep.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient) {
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
        this.notificationClient = notificationClient;
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        //todo: verify
        //todo: check if fraudster
        customerRepository.save(customer);

        /* communication between services without using FraudClient
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );
         */

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        //todo: send notification
        notificationClient.sendNotification(new NotificationRequest(customer.getId(), customer.getEmail(), String.format("Hi %s", customer.getFirstName())));
    }

}
