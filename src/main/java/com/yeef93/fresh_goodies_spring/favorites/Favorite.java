package com.yeef93.fresh_goodies_spring.favorites;

import com.yeef93.fresh_goodies_spring.products.Product;
import com.yeef93.fresh_goodies_spring.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Favorite {
    private Long id;
    private User user;
    private Product product;
}
