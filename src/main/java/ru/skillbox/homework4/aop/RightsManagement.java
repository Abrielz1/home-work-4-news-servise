package ru.skillbox.homework4.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Before("execution(* ru.skillbox.homework4.news.controller.NewsController.updateNewsById(..)) && args(userId, ..)")
    public  Boolean userRightsManagement(@RequestParam("userId") Long userId) { // ??
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        System.out.println("Request: " + request.getAttribute("id"));
        System.out.println("RequestAttributes: " + requestAttributes);

        Long userCatchId = Long.parseLong(request.getAttribute("userId").toString());
        System.out.println("User caught id is: " + userCatchId);
        return false;
    }


    //        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        System.out.println(pathVariables);// ??
//    @Before("@annotation(Catcher)")
//    public  Boolean userRightsManagement(JoinPoint joinPoint) {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//
//        System.out.println("Request: " + request.getAttribute("id"));
//        System.out.println("RequestAttributes: " + requestAttributes);
//
//        Long userId = Long.parseLong(request.getAttribute("userId").toString());
//        System.out.println("User caught id is: " + userId);
//        return false;
//    }
}
