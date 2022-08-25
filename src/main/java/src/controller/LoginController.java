package src.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import src.Service.LoginServcie;
import src.Service.impl.LoginServiceImpl;
import src.pojo.User;
import src.utils.ResponseResult;

@RestController
public class LoginController {

    @Autowired
    private LoginServiceImpl loginServcie;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        //登录
        return loginServcie.login(user);
    }

    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginServcie.logout();
    }
}
