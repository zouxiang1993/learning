package tujie.a;

import java.util.Date;

public class Single1 {
    private static Single1 instance = null;
    private Date date = new Date();

    private Single1() {

    }

    public Date getDate() {
        return this.date;
    }

    public static synchronized Single1 getInstance() {
        if (instance == null) {
            instance = new Single1();
        }
        return instance;
    }
}
