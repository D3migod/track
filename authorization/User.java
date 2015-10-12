package classwork.authorization;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Булат on 12.10.2015.
 */
public class User implements Serializable {
    private String name;
    private Integer pass;

    public User(String name, Integer pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public String toString() {
        return name + "\t" + pass.toString() + "\t";
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(name);
        stream.writeObject(pass);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        pass = (Integer) stream.readObject();
    }

}
