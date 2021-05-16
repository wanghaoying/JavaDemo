package org.example.aop;

// 我们期望能在div方法执行之前和之后，或者发生异常的时候，能执行一些日志相关的代码，
// 如果只是这一个方法，我们可以采用硬编码的方式来实现他，但是如果MathCalculator类
// 中的所有方法都想实现这个功能，或者其他的一些暂时还未确定的功能，这么做就不合适了，
// 采用动态代理的方式会更加适合我们的需求
public class MathCalculator {

    public Integer div(int i, int j){
        return i/j;
    }
}
