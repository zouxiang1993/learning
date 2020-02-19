package tujie.a;

import java.util.Date;

public class Single2 {
    private static Single2 instance = null;
    private Date date = new Date();

    private Single2() {

    }

    public Date getDate() {
        return date;
    }

    public static Single2 getInstance() {
        if (instance == null) {
            synchronized (Single2.class) {
                if (instance == null) {
                    instance = new Single2();
                }
            }
        }
        return instance;
    }
}
