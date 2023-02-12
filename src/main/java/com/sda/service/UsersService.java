package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.mapper.UserMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UsersService {

    private final UsersDAO usersDAO;
    private final UserMapper userMapper;

}
