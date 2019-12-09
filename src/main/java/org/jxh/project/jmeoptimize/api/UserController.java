package org.jxh.project.jmeoptimize.api;

import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.userDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired private userDao userDao;

    @GetMapping("/loginUser")
    JSONObject login(@RequestParam("name")String name,@RequestParam("password")String password){
        return null;
    }
    @GetMapping("/deleteUser")
    JSONObject deleteUser(@RequestParam("name")String name){
        return null;
    }
    @GetMapping("/addUser")
    JSONObject addUser(@RequestParam("name")String name,@RequestParam("password")String password,@RequestParam("role")String role){
        return null;
    }
    @GetMapping("/updatePassword")
    JSONObject updatePassword(@RequestParam("name")String name,@RequestParam("password")String password){
        return null;
    }
    @RequestMapping("/resettingPassword")
    JSONObject resettingPassword(@RequestParam("name")String name){
        return null;
    }
}
