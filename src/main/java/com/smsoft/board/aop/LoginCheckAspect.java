package com.smsoft.board.aop;

import com.smsoft.board.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2
@Order(Ordered.LOWEST_PRECEDENCE)
@Aspect
@Component
public class LoginCheckAspect {
    @Around("@annotation(com.smsoft.board.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = (HttpSession) ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();

        String id = null;

        String userType = loginCheck.type().toString();
        switch (userType) {
            case "ADMIN": {
                id = SessionUtil.getLoginAdminId(session);
                break;
            }
            case "USER": {
                id = SessionUtil.getLoginMemberId(session);
            }
        }

        if (id == null) {
            log.debug(proceedingJoinPoint.toString() + "user ID : " + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "id check please") {};
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if (proceedingJoinPoint.getArgs() != null) modifiedArgs[0] = id;

        return proceedingJoinPoint.proceed(modifiedArgs);
    }

}
