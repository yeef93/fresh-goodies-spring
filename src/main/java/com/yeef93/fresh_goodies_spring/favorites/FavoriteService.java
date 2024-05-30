package com.yeef93.fresh_goodies_spring.favorites;

import com.yeef93.fresh_goodies_spring.products.Product;
import com.yeef93.fresh_goodies_spring.user.User;
import com.yeef93.fresh_goodies_spring.user.UserService;
import jakarta.annotation.PostConstruct;
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
    private  final UserService userService;


    public FavoriteService(UserService userService) {
        this.userService = userService;
    }

    public List<Favorite> getfavoritemsByUserId(Long userId) {
        List<Favorite> userFavorites = new ArrayList<>();
        for (Favorite favorite : favorites) {
            if (favorite.getUser() != null && favorite.getUser().getId() != null && favorite.getUser().getId().equals(userId)) {
                userFavorites.add(favorite);
            }
        }
        return userFavorites;
    }

    public void addFavorite(Long userId, Long productId) {
        Optional<User> userOpt = users.stream().filter(user -> user.getId().equals(userId)).findFirst();
        Optional<Product> productOpt = products.stream().filter(product -> product.getId() == (productId)).findFirst();

        if (userOpt.isPresent() && productOpt.isPresent()) {
            Favorite favorite = new Favorite(favoriteIdCounter++, userOpt.get(), productOpt.get());
            favorites.add(favorite);
            userOpt.get().getFavorites().add(favorite);
        } else {
            throw new RuntimeException("User or Product not found");
        }
    }
}
