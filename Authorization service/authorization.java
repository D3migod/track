package classwork.authorization;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class authorization {
    private static Map<Integer, Integer> loginPasswordMap;
    private static String fileName = "userData.ser";

    public static void writeIntoFile() throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(loginPasswordMap);
        out.close();
        fileOut.close();
    }

    public static void readFromFile() throws IOException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            loginPasswordMap = (HashMap<Integer, Integer>) in.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        in.close();
        fileIn.close();
    }

    public static void authorize() throws IOException {
        loginPasswordMap = new HashMap<>();
        File f = new File(fileName);
        if(f.exists() && !f.isDirectory()) {
            readFromFile();
        }
        System.out.println("Sign in");
        System.out.println("enter login");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputLogin = in.readLine();
        System.out.println("enter password");
        String inputPassword = in.readLine();
        if ((loginPasswordMap.get(inputLogin.hashCode()) != null)) {
            if (loginPasswordMap.get(inputLogin.hashCode()).equals(inputPassword.hashCode())) {
                System.out.println("successful authorization");
            } else {
                System.out.println("wrong password. Please, try again");
            }

        } else {
            System.out.println("no such user. Sign up");
            System.out.println("enter login");
            inputLogin = in.readLine();
            System.out.println("enter password");
            inputPassword = in.readLine();
            loginPasswordMap.put(inputLogin.hashCode(), inputPassword.hashCode());
            System.out.println("successful registration");
            writeIntoFile();
        }
    }
}
