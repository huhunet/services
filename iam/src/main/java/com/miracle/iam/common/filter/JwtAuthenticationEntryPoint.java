package com.miracle.iam.common.filter;

import com.alibaba.fastjson.JSON;
import com.miracle.iam.domain.vo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        log.info("认证失败：" + e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorResponse.UNAUTHORIZED)
                .errorInfo(e.getMessage()).build();
        printWriter.write(JSON.toJSONString(errorResponse));
        printWriter.flush();
    }
}
