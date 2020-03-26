package org.zx.learn.mockito.sec04;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/9
 * Description: No Description
 */
public class VerifyOccursDemo {

    // 验证调用的次数
    @Test
    public void test1() {
        List mockedList = mock(List.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        // 下列两种验证是相同的，times(1)是默认值
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        // 指定调用次数的验证
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        // 以下验证会报错，因为调用了2次
        verify(mockedList).add("twice");

        // 从未发生, 也就是times(0)
        verify(mockedList, never()).add("never happened");

        // 最多/最少 调用次数
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }

}
