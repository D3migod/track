package classwork.authorization;


import classwork.authorization.exceptionPackage.authorizationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class authorization {
    private UserStore userStore;

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public void authorization() {
    }


    public void signUp() throws IOException {
        System.out.println("Enter login");
        String inputLogin = in.readLine();
        if (this.userStore.isUserExist(inputLogin)) {
            throw new authorizationException("User " + inputLogin + "already exists");
        }
        System.out.println("Enter password");
        String inputPassword = in.readLine();
        this.userStore.addUser(new User(inputLogin, inputPassword.hashCode()));
        System.out.println("Successful registration");
        System.out.println("Hi, " + inputLogin);
        userStore.writeIntoFile();
    }

    public void signIn() throws IOException {
        System.out.println("Enter login");
        String inputLogin = in.readLine();
        if ((this.userStore.getUser(inputLogin) != null)) {
            System.out.println("Enter password");
            String inputPassword = in.readLine();
            if (this.userStore.compareLoginPass(inputLogin, inputPassword.hashCode())) {
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
        try {
            System.out.println("Sign in (enter \"1\") or sign up (enter \"2\")?");
            String answer = in.readLine();
            if (answer.equals("1")) {
                this.signIn();
            } else if (answer.equals("2")) {
                this.signUp();
            } else {
                throw new authorizationException("Unexpected answer");
            }
        } catch (authorizationException e) {
            System.err.println(e.getMessage());
            chooseYourDestiny();
        }
    }


    public void authorize() throws IOException {
        this.userStore = new UserStore();
        if (userStore.isFileExist()) {
            userStore.readFromFile();
        }
        chooseYourDestiny();
    }

}
