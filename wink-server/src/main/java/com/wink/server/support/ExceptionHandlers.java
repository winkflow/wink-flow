package com.wink.server.support;

import com.wink.support.BizException;
import com.wink.support.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ExceptionHandlers {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response serverExceptionHandler(BizException ex) {
        final String errorCode = ex.getMessage();
        return Response.fail(errorCode, messageSource.getMessage(errorCode, null, Locale.CHINA));
    }
}