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

import classwork.authorization.exceptionPackage.authorizationException;

public class authorization {
    private Map<Integer, Integer> loginPasswordMap;
    private String fileName = "userData.ser";
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public void authorization() {
    }

    public void writeIntoFile() throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(this.fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.loginPasswordMap);
        out.close();
        fileOut.close();
    }

    public void readFromFile() throws IOException {
        FileInputStream fileIn = new FileInputStream(this.fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            this.loginPasswordMap = (HashMap<Integer, Integer>) in.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        in.close();
        fileIn.close();
    }

    public void signUp() throws IOException {
        System.out.println("Enter login");
        String inputLogin = in.readLine();
        if (this.loginPasswordMap.containsKey(inputLogin)) {
            throw new authorizationException("User " + inputLogin + "already exists");
        }
        System.out.println("Enter password");
        String inputPassword = in.readLine();
        this.loginPasswordMap.put(inputLogin.hashCode(), inputPassword.hashCode());
        System.out.println("Successful registration");
        System.out.println("Hi, " + inputLogin);
        writeIntoFile();
    }

    public void signIn() throws IOException {
        System.out.println("Enter login");
        String inputLogin = in.readLine();
        if ((this.loginPasswordMap.get(inputLogin.hashCode()) != null)) {
            System.out.println("Enter password");
            String inputPassword = in.readLine();
            if (this.loginPasswordMap.get(inputLogin.hashCode()).equals(inputPassword.hashCode())) {
                System.out.println("Successful authorization");
                System.out.println("Hi, " + inputLogin);
            } else {
                throw new authorizationException("Wrong password");
            }
        } else {
            throw new authorizationException("No such user");
        }
    }

    public void chooseYourDestiny() throws IOException {
        System.out.println("Sign in (enter \"1\") or sign up (enter \"2\")?");
        String answer = in.readLine();
        if (answer.equals("1")) {
            this.signIn();
        } else if (answer.equals("2")) {
            this.signUp();
        } else {
            System.err.println("Wrong answer. Enter \"1\" or \"2\" (without quotes)");
        }
    }

    public void authorize() throws IOException {
        this.loginPasswordMap = new HashMap<>();
        File f = new File(this.fileName);
        if (f.exists() && !f.isDirectory()) {
            readFromFile();
        }

        try {
            chooseYourDestiny();
        } catch (authorizationException e) {
            System.err.println(e.getMessage());
            chooseYourDestiny();
        }

    }
}
