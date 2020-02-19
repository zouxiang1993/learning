package beanfactory.demo1;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class BeanFactoryTest {
    public static void main(String[] args) {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beanFactoryText.xml"));
        MyTestBean myTestBean = (MyTestBean) beanFactory.getBean("myTestBean");

        String str = myTestBean.getTestStr();
        System.out.println(str);
    }
}
