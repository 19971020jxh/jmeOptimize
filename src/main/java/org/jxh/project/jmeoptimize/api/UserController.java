package org.jxh.project.jmeoptimize.api;

import com.alibaba.fastjson.JSONObject;
import org.jxh.project.jmeoptimize.dao.userDao;
import org.jxh.project.jmeoptimize.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired private userDao userDao;

    @GetMapping("/loginUser")
    JSONObject login(@RequestParam("name")String name, @RequestParam("password")String password){
        JSONObject rs=new JSONObject();
        user  user=userDao.login(name,password);
        if(user!=null){
            rs.put("user",user);
            rs.put("login","T");
        }
        return rs;
    }
    @GetMapping("/user/delete")
    JSONObject deleteUser(@RequestParam("id")int id){
         userDao.deleteUser(id);
        return null;
    }
    @GetMapping("/user/add")
    JSONObject addUser(@RequestParam("name")String name,@RequestParam("role")String role){
        JSONObject rs=new JSONObject();
        if(userDao.existUser(name)==0){
         userDao.addUser(name,role);
        }else{
         rs.put("status","账号已存在!");
        }
        return rs;
    }
    @GetMapping("/updatePassword")
    JSONObject updatePassword(@RequestParam("id")int id,@RequestParam("password")String password){
        userDao.updatePassword(id,password);
        return null;
    }
    @RequestMapping("/resettingPassword")
    JSONObject resettingPassword(@RequestParam("name")String name){
        return null;
    }

    @RequestMapping("/user/init")
    JSONObject init(){
        JSONObject rs=new JSONObject();
        rs.put("userList",userDao.init());
        return  rs;
    }


}
