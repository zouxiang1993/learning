package org.zx.learn.mockito.sec06;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: 验证方法调用的顺序
 */
public class VerificationInOrderDemo {

    /*
    对顺序的验证是灵活的：你不需要逐个验证所有的交互，可以只验证你感兴趣的动作的顺序。
     */

    @Test
    public void test1() {
        List singleMock = mock(List.class);

        singleMock.add("was added first");
        singleMock.add("was added second");

        InOrder inOrder = inOrder(singleMock);

        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");
    }

    @Test
    public void test2(){
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        firstMock.add("was called first");
        secondMock.add("was called second");

        InOrder inOrder = inOrder(firstMock, secondMock);

        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
    }

}
