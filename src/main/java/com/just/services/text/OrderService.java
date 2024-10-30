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
import java.time.format.DateTimeParseException;
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
                // Log da linha atual para depuração
                System.out.println("Processing line: " + line);

                try {
                    // Garantindo que apenas caracteres numéricos sejam lidos
                    int userId = Integer.parseInt(line.substring(0, 10).replaceAll("\\D", ""));
                    String userName = line.substring(16, 23).trim();
                    int orderId = Integer.parseInt(line.substring(52, 61).replaceAll("\\D", ""));
                    int productId = Integer.parseInt(line.substring(63, 72).replaceAll("\\D", ""));

                    // Ajuste: Garantir que o valor esteja limpo antes do parsing
                    String valueString = line.substring(78, 83).trim().replace(",", ".").replaceAll(" ", "");
                    BigDecimal value = new BigDecimal(valueString);

                    // Certifique-se de que a substring da data está correta
                    String dateString = line.substring(87, 96).trim(); // Aqui, a data deve ter 8 caracteres

                    // Verificação do tamanho da data
                    if (dateString.length() != 8) {
                        throw new IllegalArgumentException("Data em formato inválido: " + dateString);
                    }

                    LocalDate date = LocalDate.parse(dateString, formatter);

                    // Criação ou atualização de objetos User e Order
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

                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
                    throw e; // Re-throw para que o erro seja visível no log e status HTTP 500
                }
            }
        }

        List<User> users = new ArrayList<>(userMap.values());
        userRepository.saveAll(users);
        return users;
    }


}

