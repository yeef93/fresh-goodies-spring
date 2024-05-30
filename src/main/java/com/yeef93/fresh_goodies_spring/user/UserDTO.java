package com.yeef93.fresh_goodies_spring.user;

import com.yeef93.fresh_goodies_spring.favorites.DTO.FavoriteProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private List<FavoriteProductDTO> favorites;

    public UserDTO(Long id, String username, List<FavoriteProductDTO> favorites) {
        this.id = id;
        this.username = username;
        this.favorites = favorites;
    }
}
