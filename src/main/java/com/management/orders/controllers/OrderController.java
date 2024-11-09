package com.management.orders.controllers;

import com.management.orders.components.OrderRequest;
import com.management.orders.services.JwtService;
import com.management.orders.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("create")
    public ResponseEntity<?> insertFromBody(
            @RequestBody OrderRequest orderRequest,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);

            // Get email from request
            String emailFromRequest = orderRequest.getUserEmail();

            // Validate emails match
            if (!emailFromToken.equals(emailFromRequest)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to create an order for this email");
            }

            // If emails match, proceed with order creation
            return ResponseEntity.ok(orderService.createOrder(orderRequest));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }
    }


    @GetMapping("list")
    public ResponseEntity<?> getOrders( @RequestParam String email, @RequestParam int page,
                                        @RequestParam int size, @RequestHeader("Authorization") String authHeader
    ) {
        if(page<1 || size<1){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid page/size");
        }
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);

            // Validate emails match
            if (!emailFromToken.equals(email)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to create an order for this email");
            }

            // If emails match, proceed with order listing
            return ResponseEntity.ok(orderService.getOrderHistory(email,page,size));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }
    }

    @PutMapping("cancel")
    public ResponseEntity<?> cancelOrder(
            @RequestParam String email, @RequestParam String reference,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);

            // Validate emails match
            if (!emailFromToken.equals(email)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to create an order for this email");
            }

            // If emails match, proceed with order creation
            return ResponseEntity.ok(orderService.cancelOrder(email,reference));

        }
        catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }
    }
}