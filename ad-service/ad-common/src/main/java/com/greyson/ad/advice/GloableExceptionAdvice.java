package com.greyson.ad.advice;

import com.greyson.ad.exception.AdException;
import com.greyson.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author greyson
 * @time 2020/2/6 21:08
 */
@RestControllerAdvice
public class GloableExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdExcption(HttpServletRequest req,
                                                    AdException e) {
        CommonResponse<String> response = new CommonResponse<>(-1,"bussiness error");
        response.setData(e.getMessage());
        return response;
    }

}
