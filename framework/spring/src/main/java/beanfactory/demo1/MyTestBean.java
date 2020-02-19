package beanfactory.demo1;

public class MyTestBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public MyTestBean setTestStr(String testStr) {
        this.testStr = testStr;
        return this;
    }
}
