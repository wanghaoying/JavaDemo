package com.example.securitydemo.exception;

import com.example.securitydemo.auth.controller.UserController;
import com.example.securitydemo.util.tools.CodeMsg;
import com.example.securitydemo.util.tools.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义一个全局处理类，用来处理所有类的一个公共异常
 * @author W.H
 */
@ControllerAdvice(assignableTypes = UserController.class)
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result handlerValidationExceptions(
            MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field,message);
        });

        String value = ex.getParameter().getMethod().getDeclaringClass()
                .getAnnotation(RequestMapping.class).value()[0];

        value = value +
                ex.getParameter()
                        .getMethodAnnotation(PostMapping.class).value()[0];
        System.out.println(value);

        return Result.error(CodeMsg.illegalArgument, errors);
    }
}
