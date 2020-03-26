package org.zx.learn.mockito.sec01;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Author: zouxiang
 * Date: 2019/12/9
 * Description: No Description
 */
public class VerifyBehaviorDemo {
    // 一旦mock对象被创建，它会记录所有的交互。然后你可以选择性的验证你感兴趣的交互动作。
    @Test
    public void test1() {
        // 创建一个mock对象
        List mockedList = mock(List.class);

        // 使用mock对象
        mockedList.add("one");
        mockedList.clear();

        // 验证行为
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
