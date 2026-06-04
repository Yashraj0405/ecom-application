package com.app.ecom.service;

import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.entity.*;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) return Optional.empty();

        Optional<User> userOpt = userRepository.findById(Long.parseLong(userId));
        if (userOpt.isEmpty()) return  Optional.empty();

        User user = userOpt.get();

        BigDecimal totalAmount = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems = cartItems.stream()
                        .map(item -> new OrderItem(
                                null,
                                item.getProduct(),
                                item.getQuantity(),
                                item.getPrice(),
                                order
                        )).toList();

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderItems().stream().map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                )).toList(),
                order.getCreatedAt()
        );
    }


}
