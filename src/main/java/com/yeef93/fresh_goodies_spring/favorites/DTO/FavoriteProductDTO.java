package com.yeef93.fresh_goodies_spring.favorites.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FavoriteProductDTO {
    private Long id;

    public FavoriteProductDTO(Long id){
        this.id = id;
    }
}
