package src.Service;


import src.pojo.User;
import src.utils.ResponseResult;

public interface LoginServcie {
    ResponseResult login(User user);

    ResponseResult logout();

}
