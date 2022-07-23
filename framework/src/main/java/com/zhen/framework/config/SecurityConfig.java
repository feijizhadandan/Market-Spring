package com.zhen.framework.config;

import com.zhen.framework.security.exceptionHandler.AccessDeniedHandlerImpl;
import com.zhen.framework.security.exceptionHandler.AuthenticationEntryPointImpl;
import com.zhen.framework.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * BCrypt 密码生成校验器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 将AuthenticationManager注入容器，用于用户身份认证
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 放行登录接口
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF
                .csrf().disable()
                // 不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口，允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 对于积木报表资源，允许匿名访问
                .antMatchers("/jmreport/**").anonymous()
                // 除了登录接口，都需要认证
                .anyRequest().authenticated();

        // 还要将JwtFilter注册到SpringSecurity中, 用于认证是否已登录
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        // 配置异常处理器
        http.exceptionHandling()
                // 配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 配置授权失败处理器
                .accessDeniedHandler(accessDeniedHandler);
    }
}
