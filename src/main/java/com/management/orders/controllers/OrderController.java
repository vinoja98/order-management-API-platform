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
            // Validate token
            if (!jwtService.validateToken(token)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to create an order for this email");
            }

            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(orderService.createOrder(orderRequest,emailFromToken));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }
    }


    @GetMapping("list")
    public ResponseEntity<?> getOrders( @RequestParam int page,
                                        @RequestParam int size,
                                        @RequestHeader("Authorization") String authHeader
    ) {
        if(page<1 || size<1){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid page/size");
        }
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Validate token
            if (!jwtService.validateToken(token)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Not authorized");
            }

            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(orderService.getOrderHistory(emailFromToken,page,size));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }
    }

    @PutMapping("cancel")
    public ResponseEntity<?> cancelOrder(
            @RequestParam String reference,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Validate token
            if (!jwtService.validateToken(token)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to create an order for this email");
            }
            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(orderService.cancelOrder(emailFromToken,reference));

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