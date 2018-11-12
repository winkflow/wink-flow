package com.wink.service;

import com.wink.dto.UserDTO;

public interface LoginService {
    UserDTO findUserByName(String userName);
}
