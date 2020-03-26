package org.zx.learn.mockito.sec05;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: No Description
 */
public class StubbingVoidMethodWithExceptionDemo {
    @Test
    public void test1() {
        List mockedList = mock(List.class);

        doThrow(new RuntimeException("哈哈异常")).when(mockedList).clear();

        mockedList.clear();
    }
}
