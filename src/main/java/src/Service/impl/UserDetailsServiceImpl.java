package src.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import src.mapper.UserMapper;
import src.mapper.menuMapper;
import src.pojo.LoginUser;
import src.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
@Service
//UserDetailsService 载用户特定数据的核心接口。里面定义了一个根据用户名查询用户信息的方法。
public class  UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private menuMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {      //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);

        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
      //  ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("admin","test"));
        return new LoginUser(user,mapper.selectPermsByUserId(user.getId()));
    }


}
