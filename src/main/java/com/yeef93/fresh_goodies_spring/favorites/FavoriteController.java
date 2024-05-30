package com.yeef93.fresh_goodies_spring.favorites;

import com.yeef93.fresh_goodies_spring.responses.Response;
import com.yeef93.fresh_goodies_spring.user.UserDTO;
import com.yeef93.fresh_goodies_spring.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class FavoriteController {

    private FavoriteService favoriteService;
    private UserService userService;

    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @GetMapping("/favorites/{userId}")
    public ResponseEntity<Response<UserDTO>> getUserFavorites(@PathVariable Long userId) {
        UserDTO userFavorites = favoriteService.getfavoritemsByUserId(userId);
        if (userFavorites == null) {  // Assuming getfavoritemsByUserId returns null if no favorites are found
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "User favorites not found");
        }
        else if (userFavorites.getFavorites().isEmpty()) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        return Response.successfulResponse("Favorite products fetched", userFavorites);
    }


    @PostMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<Response<String>> addFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            favoriteService.addFavorite(userId, productId);
            return Response.successfulResponse("Product added to favorites");
        } catch (RuntimeException e) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
