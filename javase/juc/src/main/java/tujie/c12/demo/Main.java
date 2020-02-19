package tujie.c12.demo;

import tujie.c12.demo.activeobject.ActiveObject;
import tujie.c12.demo.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();

        new MarkerClientThread("Alice", activeObject).start();
        new MarkerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }
}
