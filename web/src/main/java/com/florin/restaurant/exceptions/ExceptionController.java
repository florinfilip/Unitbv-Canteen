package com.florin.restaurant.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(SignUpException.class)
    public RedirectView handle(SignUpException e, HttpServletRequest httpServletRequest){

       RedirectView redirectView=new RedirectView("/register");
        log.info(e.getMessage());
        FlashMap outputFlashMap= RequestContextUtils.getOutputFlashMap(httpServletRequest);
        if(outputFlashMap!=null){
            outputFlashMap.put("errorMessage",e.getMessage());
        }
        return redirectView;
    }
    @ExceptionHandler(ChangePasswordException.class)
    public RedirectView handle(ChangePasswordException e, HttpServletRequest httpServletRequest){
        RedirectView redirectView = new RedirectView("/settings");
        log.info(e.getMessage());
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(httpServletRequest);
        if(!Objects.isNull(outputFlashMap)){
            outputFlashMap.put("errorMessage",e.getMessage());
        }
        return redirectView;
    }
}

