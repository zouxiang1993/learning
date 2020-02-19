package tujie.a;

import java.util.Date;

public class Single3 {
    private static class Holder {
        private static Single3 instance = new Single3();
    }

    private Date date = new Date();

    private Single3() {

    }

    public Date getDate() {
        return date;
    }

    public static Single3 getInstance() {
        return Holder.instance;
    }
}
