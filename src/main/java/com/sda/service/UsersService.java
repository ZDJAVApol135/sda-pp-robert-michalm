package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exeption.NotFoundException;
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
}
