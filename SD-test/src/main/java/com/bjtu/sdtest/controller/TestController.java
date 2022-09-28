package com.bjtu.sdtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description> TestController
 *
 * @author 26802
 * @version 1.0
 * @ClassName TestController
 * @taskId
 * @see com.bjtu.sdtest.controller
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String test(){
        return "hello!";
    }

}
