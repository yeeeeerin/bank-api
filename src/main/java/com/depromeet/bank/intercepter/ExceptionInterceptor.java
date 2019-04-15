package com.depromeet.bank.intercepter;

import com.depromeet.bank.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class ExceptionInterceptor extends HandlerInterceptorAdapter {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.getWriter()
                .write(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(ResponseDto.of(HttpStatus.NOT_ACCEPTABLE, "No have resource!!")));
        super.preHandle(request, response, handler);
        return false;
    }

}
