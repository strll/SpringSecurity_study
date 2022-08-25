package src.handler;


import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import src.utils.ResponseResult;
import src.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//请求失败
/**
 * 	如果是认证过程中出现的异常会被封装成AuthenticationException然后调用
 * AuthenticationEntryPoint对象的方法去进行异常处理。
 *
 * 如果是授权过程中出现的异常会被封装成AccessDeniedException
 * 然后调用**AccessDeniedHandler**对象的方法去进行异常处理。
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}