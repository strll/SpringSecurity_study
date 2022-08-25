package src.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import src.pojo.LoginUser;
import src.utils.JwtUtil;
import src.utils.RedisCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)){
            //如果没有token就放行
            filterChain.doFilter( httpServletRequest, httpServletResponse);
            return;
        }
        String subject = null;
        //解析token
        try {
            Claims claims = JwtUtil.parseJWT(token);
             subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从redis中获取用户信息
        String s="login:"+subject;
        LoginUser loginUser = redisCache.getCacheObject(s);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
       filterChain.doFilter( httpServletRequest, httpServletResponse);

    }
}
