package com.bjtu.sdtest.controller;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
import com.bjtu.sdtest.pojo.table.User;
import com.bjtu.sdtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/login/{username}/{password}")
    public BaseResp<User> login(@PathVariable("username")String name,
                                @PathVariable("password")String password){
        if(name.isEmpty() || password.isEmpty())
            return BaseResp.failed(RespEnum.NAME_OR_PWD_NULL);
        return userService.login(name,password);
    }

    @PostMapping("/register/{username}/{password}")
    public BaseResp<String> register(@PathVariable("username")String name,
                                     @PathVariable("password")String password){
        if(name.isEmpty()||password.isEmpty()){
            return BaseResp.failed(RespEnum.NAME_OR_PWD_NULL);
        }
        User user =new User();
        user.setName(name);
        user.setPassword(password);
        return userService.register(user);
    }

}
