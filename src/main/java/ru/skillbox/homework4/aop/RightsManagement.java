package ru.skillbox.homework4.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RightsManagement {

    // todo: метод управления категориями и проверки права владения.
    @Before("@annotation(Catcher)")
    public  Boolean userRightsManagement() { // ?? JoinPoint joinPoint
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        System.out.println("Request: " + request.getAttribute("id"));
        System.out.println("RequestAttributes: " + requestAttributes);

        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        System.out.println(pathVariables);// ??
        return false;
    }
}
