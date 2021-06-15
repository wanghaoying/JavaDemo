package com.example.securitydemo.util.tools;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 对结果信息的封装类。
 * 结果包括三个部分：状态码、状态码描述、返回数据
 *
 * @author W.H
 */
@Data
public class Result<T> {
    @Positive
    private int code;
    @NotNull
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success() {
        return new Result<T>(200, "成功");
    }
    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "成功", data);
    }
    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error() {
        return new Result<T>(400, "失败");
    }
    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);

    }
    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg, T data){
        return new Result<T>(codeMsg.getCode(), codeMsg.getMsg(), data);
    }


    public Result() {}
    private Result(T data) {
        this.data = data;
    }
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private Result(int code, String Msg, T data) {
        this.code = code;
        this.msg = Msg;
        this.data = data;
    }
    private Result(CodeMsg statusMsg) {
        if (statusMsg != null) {
            this.code = statusMsg.getCode();
            this.msg = statusMsg.getMsg();
        }
    }
}
