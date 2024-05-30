package com.yeef93.fresh_goodies_spring.user;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    @PostConstruct
    public void initData() {
        User user1 = new User(1L, "user1", new ArrayList<>());
        User user2 = new User(2L, "user2", new ArrayList<>());

        addUser(user1);
        addUser(user2);
    }
}