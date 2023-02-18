package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exeption.NotFoundException;
import com.sda.exeption.UsernameConflictException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class UsersService {

    private final UsersDAO usersDAO;
    private final UserMapper userMapper;

    public List<UserDTO> findAll(){
//        List<User> users = usersDAO.findAll();

//        List<UserDTO> userDTOS = new ArrayList<>();
//
//        for (User user : users) {
//            UserDTO dto = userMapper.map(user);
//            userDTOS.add(dto);
//        }
//        return userDTOS;

//        return usersDAO.findAll().stream()
//                .map(user -> userMapper.map(user))
//                .toList();
//
//        Function<User, UserDTO> mapFunction = new Function<>() {
//
//            @Override
//            public UserDTO apply(User user) {
//                return userMapper.map(user);
//            }
//        };
//
//        Function<User, UserDTO> lambdaFunction = userMapper::map;

        return usersDAO.findAll().stream()
                .map(user -> userMapper.map(user))
                .toList();
    }

//    public UserDTO findByUsername(String username) {
//
//        User user = usersDAO.findByUsername(username);
//
//        if (user == null) {
//            String massage = "User with username: '%s' not found".formatted(username);
//            throw new NotFoundException(massage);
//        }
//
//        UserDTO userDTO = userMapper.map(user);
//        return userDTO;
//    }

    public UserDTO findByUsername(String username) {
        User user = usersDAO.findByUsername(username);
        boolean userNotExists = user == null;
        throwNotFoundExceptionIfTrue(username, userNotExists);
        UserDTO userDTO = userMapper.map(user);
        return userDTO;
    }

    public void deleteByUsername(String username) {
        boolean deleted = usersDAO.delete(username);
        throwNotFoundExceptionIfTrue(username, !deleted);
    }

    private void throwNotFoundExceptionIfTrue(String username, boolean condition) {
        if (condition) {
            String message = "User with username: '%s' not found!".formatted(username);
            throw new NotFoundException(message);
        }
    }

    public void create(User user) {
        boolean exists = usersDAO.exists(user.getUsername());
        if (exists) {
            String message = "User: '%s' already exists".formatted(user);
            throw new UsernameConflictException(message);
        }
        usersDAO.create(user);
    }

    public UserDTO update(User user, String username) {
        if (user.getUsername().equals(username)) {
            throw new UsernameConflictException("Usernames dose not match!");
        }
        boolean exists = usersDAO.exists(username);
        if (!exists) {
            String message = "User with username: '%s' not found".formatted(username);
            throw new NotFoundException(message);
        }
        User updatedUser = usersDAO.update(user);
        UserDTO userDTO = userMapper.map(updatedUser);
        return userDTO;
    }
}
