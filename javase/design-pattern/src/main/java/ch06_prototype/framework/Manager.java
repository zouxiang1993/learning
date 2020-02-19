package ch06_prototype.framework;

import java.util.HashMap;

public class Manager {
    private HashMap<String, Product> showcase = new HashMap();

    public void register(String name, Product product) {
        showcase.put(name, product);
    }

    public Product create(String protoname) {
        Product p = showcase.get(protoname);
        return p.createClone();
    }
}
