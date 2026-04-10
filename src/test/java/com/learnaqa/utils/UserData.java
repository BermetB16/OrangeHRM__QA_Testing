package com.learnaqa.utils;

import com.learnaqa.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Admin", "admin123"));
        users.add(new User("Admin", "wrongpassword"));
        return users;
    }
}
