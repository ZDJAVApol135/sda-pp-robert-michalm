package com.sda;

import com.sda.dao.UsersDAO;
import com.sda.model.User;

public class Main {

    public static void main(String[] args) {
        UsersDAO usersDAO = new UsersDAO();

        User user = new User();
        user.setUsername("root");

        usersDAO.create(user);

    }

}
