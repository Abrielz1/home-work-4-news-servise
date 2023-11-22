package ru.skillbox.homework4.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RightsManagement {

    @Before("@annotation(Catcher)")
    public  Boolean userRightsManagement() {

        return false;
    }
}
