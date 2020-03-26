package org.zx.learn.mockito.sec10;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: No Description
 */
public class ConsecutiveCallsDemo {
    @Test
    public void test1() {
        List<Integer> mockList = mock(List.class);

        // thenXxx()方法 必须连续调用, 如果不是连续的话。后面的设置会覆盖掉前面的设置。
        when(mockList.size()).thenReturn(1).thenThrow(new RuntimeException("啊啊啊"));

        // 第一次会返回1
        System.out.println(mockList.size());

        // 第二次会抛出异常
        System.out.println(mockList.size());

        // 简写形式
        when(mockList.size()).thenReturn(1, 2, 3);
    }
}
