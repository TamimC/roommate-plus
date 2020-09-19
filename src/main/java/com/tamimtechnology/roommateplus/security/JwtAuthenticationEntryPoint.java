package com.tamimtechnology.roommateplus.security;

import com.google.gson.Gson;
import com.tamimtechnology.roommateplus.exceptions.exceptionResponses.CustomExpiredJwtExceptionResponse;
import com.tamimtechnology.roommateplus.exceptions.exceptionResponses.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        if (httpServletRequest.getAttribute("expired") != null){
            httpServletResponse.getWriter().print(new Gson().toJson(new CustomExpiredJwtExceptionResponse("Your login has expired.")));
        }else{
            httpServletResponse.getWriter().print(new Gson().toJson(new InvalidLoginResponse()));
        }

    }
}
