package org.zx.learn.mockito.sec12;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: No Description
 */
public class VoidMethodDemo {
    @Test
    public void test1() {
        /*
        对于返回值为void的方法, 如 List.clear()
        不能写成 when(mockList).clear.thenXxx(..)，因为编译器不支持对void后面调用方法
        这时可以写成doXxx的形式。
         */
        List mockList = mock(List.class);
        doThrow(new RuntimeException()).when(mockList).clear();

        mockList.clear();
    }
}
