package org.zx.learn.mockito.sec08;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: No Description
 */
public class FindRedundantInvocationsDemo {

    @Test
    public void test1() {
        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");
        verifyNoMoreInteractions(mockedList);
    }
}
