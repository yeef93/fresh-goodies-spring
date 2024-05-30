package com.yeef93.fresh_goodies_spring.favorites;

import com.yeef93.fresh_goodies_spring.favorites.DTO.FavoriteProductDTO;
import com.yeef93.fresh_goodies_spring.favorites.Entity.Favorite;
import com.yeef93.fresh_goodies_spring.products.Product;
import com.yeef93.fresh_goodies_spring.products.service.ProductService;
import com.yeef93.fresh_goodies_spring.user.User;
import com.yeef93.fresh_goodies_spring.user.UserDTO;
import com.yeef93.fresh_goodies_spring.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    private List<Favorite> favorites = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private Long favoriteIdCounter = 1L;
    private final UserService userService;
    private final ProductService productService;

    public FavoriteService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
        initializeData(); // Call initializeData from constructor
    }

    // Method to initialize initial data
    private void initializeData() {
        // Initialize users and products if needed (assuming userService provides these)
        users.addAll(userService.getUsers());
        products.addAll(productService.getProducts());

        // Add some initial favorites
        addFavorite(1L, 1L);
        addFavorite(2L, 1L);
    }

    public UserDTO getfavoritemsByUserId(Long userId) {
        List<FavoriteProductDTO> userFavoriteProducts = new ArrayList<>();
        String username = null;
        for (Favorite favorite : favorites) {
            if (favorite.getUser() != null && favorite.getUser().getId() != null && favorite.getUser().getId().equals(userId)) {
                if (username == null) {
                    username = favorite.getUser().getUsername();
                }
                userFavoriteProducts.add(new FavoriteProductDTO(favorite.getId()));
            }
        }

        return new UserDTO(userId, username, userFavoriteProducts);
    }

    public void addFavorite(Long userId, Long productId) {
        Optional<User> userOpt = users.stream().filter(user -> user.getId().equals(userId)).findFirst();
        Optional<Product> productOpt = products.stream().filter(product -> product.getId() == productId).findFirst();
        if (userOpt.isPresent() && productOpt.isPresent()) {
            Favorite favorite = new Favorite(favoriteIdCounter++, userOpt.get(), productOpt.get());
            favorites.add(favorite);
            userOpt.get().getFavorites().add(favorite);
        } else {
            throw new RuntimeException("User or Product not found");
        }
    }
}
