package org.jxh.project.jmeoptimize.api;

import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.userDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired private userDao userDao;

    @RequestMapping("/loginUser")
    JSONObject login(@RequestParam("name")String name,@RequestParam("password")String password){
        return null;
    }
    @RequestMapping("/deleteUser")
    JSONObject deleteUser(@RequestParam("name")String name){
        return null;
    }
    @RequestMapping("/addUser")
    JSONObject addUser(@RequestParam("name")String name,@RequestParam("password")String password,@RequestParam("role")String role){
        return null;
    }
    @RequestMapping("/updatePassword")
    JSONObject updatePassword(@RequestParam("name")String name,@RequestParam("password")String password){
        return null;
    }
    @RequestMapping("/resettingPassword")
    JSONObject resettingPassword(@RequestParam("name")String name){
        return null;
    }
}
