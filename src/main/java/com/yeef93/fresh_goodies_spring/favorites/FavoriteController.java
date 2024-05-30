package com.yeef93.fresh_goodies_spring.favorites;

import com.yeef93.fresh_goodies_spring.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<Response<List<Favorite>>> getFavorites(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.getfavoritemsByUserId(userId);
        if (favorites.isEmpty()) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "No favorite products found for user with ID " + userId);
        }
        return Response.successfulResponse("Favorite products fetched", favorites);
    }

    @PostMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<Response<String>> addFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            favoriteService.addFavorite(userId, productId);
            return Response.successfulResponse("Product added to favorites");
        } catch (RuntimeException e) {
            return Response.failedResponse(400, e.getMessage());
        }
    }
}
