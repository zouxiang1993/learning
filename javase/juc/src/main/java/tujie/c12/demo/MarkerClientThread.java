package tujie.c12.demo;

import tujie.c12.demo.activeobject.ActiveObject;
import tujie.c12.demo.activeobject.Result;

public class MarkerClientThread extends Thread {
    private final ActiveObject activeObject;
    private final char fillchar;

    public MarkerClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillchar = name.charAt(0);
    }

    public void run() {
        try {
            for (int i = 0; true; i++) {
                Result<String> result = activeObject.makeString(i, fillchar);
                Thread.sleep(10);
                String value = result.getResultValue();
                System.out.println(Thread.currentThread().getName() + ": value=" + value);
            }
        } catch (InterruptedException e) {

        }
    }
}
