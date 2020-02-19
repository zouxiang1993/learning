package tujie.c12.demo.activeobject;

public interface ActiveObject {
    Result<String> makeString(int count, char fillchar);
    void displayString(String string);
}
