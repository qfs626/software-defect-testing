package com.bjtu.sdtest.service;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.pojo.table.User;

/**
 * <Description> UserService
 *
 * @author 26802
 * @version 1.0
 * @ClassName UserService
 * @taskId
 * @see com.bjtu.sdtest.service
 */
public interface UserService {

    Integer register(User user);
    BaseResp<User> login(String name, String password);

}
