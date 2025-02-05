package com.kato.challengecart.controller;

import com.kato.challengecart.controller.request.CalculatePaymentOptionsDto;
import com.kato.challengecart.controller.response.PaymentOptionsDto;
import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.User;
import com.kato.challengecart.service.PaymentsOptionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentsOptionsController {

    private final PaymentsOptionsService paymentsOptionsService;

    public PaymentsOptionsController(PaymentsOptionsService paymentsOptionsService) {
        this.paymentsOptionsService = paymentsOptionsService;
    }

    @PostMapping("/options/calculate")
    public ResponseEntity<PaymentOptionsDto> calculatePaymentOptions(@RequestBody CalculatePaymentOptionsDto request) {
        User user = request.user().toDomain();
        ShoppingCart shoppingCart = request.shoppingCart().toDomain();

        // talvez eu mude as services para ser a service pela categoria
        // aqui eu implemento um simple facory para pegar a service correta

        PaymentOptionsDto payments = paymentsOptionsService.calculatePaymentOptions(user, shoppingCart);
        return ResponseEntity.ok().body(payments);
    }
}
