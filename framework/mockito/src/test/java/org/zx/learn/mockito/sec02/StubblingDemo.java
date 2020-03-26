package org.zx.learn.mockito.sec02;

import org.junit.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/9
 * Description: No Description
 */
public class StubblingDemo {
    /*
    1. 默认情况下,对于一切有返回值的方法，mock对象会返回 null、基础数据类型/基础数据类型的封装类型、
        或一个空集合。例如: 对于int/Integer返回0，对于boolean/Boolean返回false
    2. 存根是可以被覆盖的。例如: 通用的存根可以放在setup方法中, 可以在测试方法中将其覆盖。
        注意: 太多的存根覆盖行为可能是一种潜在的代码坏味道。
    3. 一旦设置了存根, 该方法会一直返回固定的值，而不管方法被调用了多少次
    4. 当你为同一个方法使用相同参数设置了多次存根，最后设置的存根是最重要的。
        例如: 当你使用参数匹配器(argument matchers)时
     */
    @Test
    public void test1() {
        // 你可以mock一个具体的类，而不仅仅是mock一个接口
        LinkedList mockedList = mock(LinkedList.class);

        // 设置存根(stubbing)
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0);
    }

}
