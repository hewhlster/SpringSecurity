package org.fjh.security.authentication.filter;

import org.fjh.security.authentication.bean.JackAuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class ValidateVcodeFilter extends OncePerRequestFilter {

    @Autowired
    private JackAuthenticationFailureHandler jackAuthenticationFailureHandler;

    Logger logger = LoggerFactory.getLogger(ValidateVcodeFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();
        if(url.equalsIgnoreCase("/auth/login")){

            try {
                validateVcode(httpServletRequest);
            } catch (AuthenticationException e){
                jackAuthenticationFailureHandler.onAuthenticationFailure(
                        httpServletRequest,
                        httpServletResponse,
                        e);
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validateVcode(HttpServletRequest httpServletRequest)throws jjj{
        HttpSession session = httpServletRequest.getSession();
        String code = (String) session.getAttribute("vcode");
        if(null==code)
            throw  new jjj("验证码无效");
        String vcode = httpServletRequest.getParameter("vcode");
        if( null==vcode)
            throw new jjj("验证码不能为空");
        if( !code.equalsIgnoreCase(vcode)){
            throw new jjj("验证码不匹配");
        }
    }

    class jjj extends AuthenticationException {
        public jjj(String explanation) {
            super(explanation);
        }
    }
}
