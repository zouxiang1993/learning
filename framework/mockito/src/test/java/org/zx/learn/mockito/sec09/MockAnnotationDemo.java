package org.zx.learn.mockito.sec09;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Author: zouxiang
 * Date: 2019/12/10
 * Description: No Description
 */
public class MockAnnotationDemo {
    @Mock
    private Object obj;

    @Before
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() {
        System.out.println(obj.toString());
    }
}
