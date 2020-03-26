package org.zx.learn.mockito.sec07;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: 从未交互
 */
public class NeverInteractionDemo {
    @Test
    public void test1() {
        List mockList = mock(List.class);
        Object mockObj = mock(Object.class);
        verifyNoInteractions(mockList, mockObj);

        mockList.add(mockObj);

        // 这里会报错，因为二者已经有交互。
        verifyNoInteractions(mockList, mockObj);
    }
}
