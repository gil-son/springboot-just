package com.just.services.text;

import com.just.entities.text.Order;
import com.just.entities.text.Product;
import com.just.entities.text.User;
import com.just.repositories.text.OrderRepository;
import com.just.repositories.text.ProductRepository;
import com.just.repositories.text.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<User> processFile(MultipartFile file) throws Exception {
        Map<Integer, User> userMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;

            while ((line = br.readLine()) != null) {
                int userId = Integer.parseInt(line.substring(0, 10).trim());
                String userName = line.substring(10, 55).trim();
                int orderId = Integer.parseInt(line.substring(55, 65).trim());
                int productId = Integer.parseInt(line.substring(65, 75).trim());
                BigDecimal value = new BigDecimal(line.substring(75, 87).trim());
                LocalDate date = LocalDate.parse(line.substring(87, 95).trim(), formatter);

                User user = userMap.computeIfAbsent(userId, id -> new User(userId, userName));
                Optional<Order> existingOrder = user.getOrders().stream().filter(o -> o.getOrderId() == orderId).findFirst();

                Order order;
                if (existingOrder.isPresent()) {
                    order = existingOrder.get();
                } else {
                    order = new Order(orderId, date);
                    user.addOrder(order);
                }

                Product product = new Product(productId, value);
                order.addProduct(product);
            }
        }

        List<User> users = new ArrayList<>(userMap.values());
        userRepository.saveAll(users);
        return users;
    }
}

