package com.depromeet.bank.intercepter;

import com.depromeet.bank.exception.UnauthorizedException;
import com.depromeet.bank.utils.JwtFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final JwtFactory jwtFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info(request.getHeader("authorization"));

        String token = request.getHeader("authorization");
        Long id = jwtFactory.getMemberId(token)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));

        request.setAttribute("id", id);

        return super.preHandle(request, response, handler);
    }

}
