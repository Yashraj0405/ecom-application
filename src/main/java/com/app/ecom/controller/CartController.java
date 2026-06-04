package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.entity.CartItem;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader ("X-User-ID") String userId,@RequestBody CartItemRequest request) {
        if(!cartService.addToCart(userId, request)) return ResponseEntity.badRequest().body("Product Out of Stock or User Not Found or Product Not Found");
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader ("X-User-ID") String userId,@PathVariable Long productId) {
        return cartService.deleteFromCart(userId, productId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public  ResponseEntity<List<CartItem>> getCartItems(@RequestHeader ("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}
