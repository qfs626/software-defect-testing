package com.bjtu.sdtest.controller;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
import com.bjtu.sdtest.pojo.table.User;
import com.bjtu.sdtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description> UserController
 *
 * @author 26802
 * @version 1.0
 * @ClassName UserController
 * @taskId
 * @see com.bjtu.sdtest.controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResp<User> login(String name, String password){
        if(name.isEmpty() || password.isEmpty())
            return BaseResp.failed(RespEnum.NAME_OR_PWD_NULL);
        return userService.login(name,password);
    }

    @PostMapping("/register")
    public BaseResp<String> register(String name, String password,String phone_number){
        if(name.isEmpty()||password.isEmpty()||phone_number.isEmpty()){
            return BaseResp.failed(RespEnum.NAME_OR_PWD_NULL);
        }
        User user =new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhoneNumber(phone_number);
        return userService.register(user);
    }

}
