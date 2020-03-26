package org.zx.learn.mockito.sec03;

import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Author: zouxiang
 * Date: 2019/12/9
 * Description: No Description
 */
public class ArgumentMatchersDemo {

    @Test
    public void test1() {
        List mockedList = mock(List.class);

        // 使用内嵌的 anyInt() 参数匹配器
        when(mockedList.get(anyInt())).thenReturn("element");

        // 使用自定义的匹配器
        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        //下面的语句会打印 "element"
        System.out.println(mockedList.get(999));

        //也可以使用参数匹配器来验证行为
        verify(mockedList).get(anyInt());

        //argument matchers can also be written as Java 8 Lambdas
        verify(mockedList).add(argThat((someString -> someString.getClass().getName().length() > 5)));
    }

    // 如果使用参数匹配器，那么所有的参数都必须使用。
    @Test
    public void test2() {
        Map<Integer, String> mock = mock(Map.class);

        mock.put(5, "aaa");
        mock.put(6, "bbb");

        // 下面语句是正确的。
        verify(mock).put(anyInt(), eq("aaa"));

        // 下面语句是错误的，因为第二个参数没有使用参数匹配器。
        verify(mock).put(anyInt(), "bbb");
    }

    private ArgumentMatcher<Object> isValid() {
        return o -> {
            return true;
        };
    }
}
