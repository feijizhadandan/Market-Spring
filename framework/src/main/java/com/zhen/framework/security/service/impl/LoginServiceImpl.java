package com.zhen.framework.security.service.impl;

import com.zhen.common.domain.AjaxResult;
import com.zhen.common.utils.RedisUtil;
import com.zhen.framework.security.domain.LoginUser;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 系统用户登录登出服务类
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public AjaxResult login(User user) {
        // 通过 AuthenticationManager(在SecurityConfig中注入容器) 进行用户身份认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        // 该方法会调用UserDetailServiceImpl 中的 loadUserByUsername方法
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 认证没通过：给出对应提示
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("账号或密码错误,登录失败");
        }

        // 认证通过：从认证结果中中将登录信息取出
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        

        // 将结果封装成map返回
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenService.handleToken(loginUser));
        return AjaxResult.success(tokenMap);
    }

    /**
     * 注销
     * @return
     */
    @Override
    public AjaxResult logout(String token) {

        // 先获取LoginUser
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        
        tokenService.deleteFromCache(loginUser);
        
        return AjaxResult.success("注销成功");

    }
}
