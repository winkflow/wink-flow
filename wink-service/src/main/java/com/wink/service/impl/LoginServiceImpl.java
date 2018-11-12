package com.wink.service.impl;

import com.wink.dao.UserDao;
import com.wink.dao.UserPermissionDao;
import com.wink.dto.UserDTO;
import com.wink.dto.UserPermissionDTO;
import com.wink.model.User;
import com.wink.model.UserPermission;
import com.wink.service.LoginService;
import com.wink.support.BeanUtil;
import io.wink.tool.annotation.RpcProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RpcProvider
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserPermissionDao permissionDao;

    @Override
    public UserDTO findUserByName(String userName) {
        final User find = new User();
        find.setUserName(userName);
        final UserDTO userDTO = new UserDTO();
        final User user = this.userDao.find(find);
        if (user == null)
            return null;
        BeanUtils.copyProperties(user, userDTO);
        //query permission
        UserPermission permissionFind = new UserPermission();
        permissionFind.setUserId(userDTO.getId());
        final List<UserPermission> permissions = permissionDao.list(permissionFind);
        final List<UserPermissionDTO> userPermissionDTOS = BeanUtil.copyList(permissions, UserPermissionDTO.class);
        userDTO.setPermissions(userPermissionDTOS);
        return userDTO;
    }
}
