package com.wink.service.login;

import com.wink.dto.UserDTO;
import com.wink.service.LoginService;
import com.wink.service.ServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginServiceTest extends ServiceBaseTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void findUserByName() {
        final UserDTO userDTO = this.loginService.findUserByName("admin");
        Assert.assertNotNull(userDTO);
        Assert.assertEquals("admin", userDTO.getUserName());
        prettyResult(userDTO);
    }

}
