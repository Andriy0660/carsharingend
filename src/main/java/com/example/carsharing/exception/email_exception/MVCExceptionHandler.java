package com.example.carsharing.exception.email_exception;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class MVCExceptionHandler {
    @ExceptionHandler
    public ModelAndView handleException(EmailVerificationRuntimeException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("EmailResponsePage");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }

}
