package classwork.authorization;

/**
 * Created by ����� on 06.10.2015.
 */

public class main {
    public static void main(String[] args) {
        try {
            authorization instance = new authorization();
            instance.authorize();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
