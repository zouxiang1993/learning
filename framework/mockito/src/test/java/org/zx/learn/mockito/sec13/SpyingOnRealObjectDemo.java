package org.zx.learn.mockito.sec13;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: No Description
 */
public class SpyingOnRealObjectDemo {
    @Test
    public void test1() {
        /*
        你可以为真实对象创建间谍(spy)，当你使用间谍时，会调用真实对象的方法，
        除非你为间谍设置了stub
         */
        List list = new LinkedList();
        List spy = spy(list);

        when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

        System.out.println(spy.get(0));

        System.out.println(spy.size());
    }

    @Test
    public void test2() {
        /*
        有时候，用when(..)来为间谍设置stub是无法实现的，这时可以考虑用doReturn | doThrow 等方法
         */
        List list = new LinkedList();
        List spy = spy(list);

        // 由于spy.get(0)会先调用真实对象的方法，会抛出IndexOutOfBoundsException
        // 所以这是不正确的。
        when(spy.get(0)).thenReturn("foo");

        doReturn("foo").when(spy).get(0);
    }

}
