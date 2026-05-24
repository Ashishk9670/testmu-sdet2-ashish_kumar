package models;

import java.util.List;

public record Order(Integer id, Integer userId, List<CartItem> products, String status) {
}
