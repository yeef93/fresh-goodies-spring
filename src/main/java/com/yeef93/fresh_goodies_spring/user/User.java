package com.yeef93.fresh_goodies_spring.user;

import com.yeef93.fresh_goodies_spring.favorites.Entity.Favorite;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class User {
    private Long id;
    private String username;
    private List<Favorite> favorites;
}
