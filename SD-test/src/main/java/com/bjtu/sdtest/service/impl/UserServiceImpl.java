package com.bjtu.sdtest.service.impl;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
import com.bjtu.sdtest.mapper.UserMapper;
import com.bjtu.sdtest.pojo.table.User;
import com.bjtu.sdtest.pojo.table.UserExample;
import com.bjtu.sdtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <Description> UserServiceImpl
 *
 * @author 26802
 * @version 1.0
 * @ClassName UserServiceImpl
 * @taskId
 * @see com.bjtu.sdtest.service.impl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public  BaseResp<String>  register(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            userMapper.insert(user);
            return BaseResp.success("success");
        } else {
            return BaseResp.failed(RespEnum.USERNAME_HAS_EXISTED);
        }
    }
    @Override
    public BaseResp<User> login(String name, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty())
            return BaseResp.failed(RespEnum.USER_NOT_EXIT);
        if (users.get(0).getPassword().equals(password)){
            return BaseResp.success(users.get(0));
        }
        return BaseResp.failed(RespEnum.ACCOUNT_INVALID);
    }
}
