package com.management.orders.controllers;

import com.management.orders.components.OrderRequest;
import com.management.orders.services.JwtService;
import com.management.orders.services.OrderService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/order")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("create")
    public ResponseEntity<?> insertFromBody(
            @Valid @RequestBody OrderRequest orderRequest,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Validate token
            if (jwtService.validateToken(token)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Invalid token"));
            }

            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(orderService.createOrder(orderRequest, emailFromToken));

        } catch (ExpiredJwtException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Expired token"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid token"));
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> getOrders(@RequestParam int page,
                                       @RequestParam int size,
                                       @RequestHeader("Authorization") String authHeader) {
        if (page < 1 || size < 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Invalid page/size"));
        }
        // Extract token from Authorization header
        String token = authHeader.replace("Bearer ", "");

        try {
            // Validate token
            if (jwtService.validateToken(token)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Invalid token"));
            }

            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(orderService.getOrderHistory(emailFromToken, page, size));

        } catch (ExpiredJwtException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Expired token"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid token"));
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
            if (jwtService.validateToken(token)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Invalid token"));
            }
            // Get email from token
            String emailFromToken = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(orderService.cancelOrder(emailFromToken, reference));

        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Expired token"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid token"));
        }
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}
