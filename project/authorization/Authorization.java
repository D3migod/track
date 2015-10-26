package track.project.authorization;


import track.project.session.Session;
import track.project.session.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Authorization {
    private UserStore userStore;
    private Session session;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public Authorization(UserStore userStore, Session session) {
        this.userStore = userStore;
        this.session = session;
    }


    public void signUp() {
        try {
            System.out.println("Enter login");
            String inputLogin = in.readLine();
            if (userStore.isUserExist(inputLogin)) {
                authorizationError("User " + inputLogin + " already exists");
            }
            System.out.println("Enter password");
            String inputPassword = in.readLine();
            User currentUser = new User(inputLogin, new Password(inputPassword));
            userStore.addUser(currentUser);
            System.out.println("Successful registration");
            session.setSessionUser(currentUser);
            return;
        } catch (IOException e) {
            System.err.println("Error reading line during signing up: " + e.getMessage());
        }
    }

    public void signUp(String inputLogin, String inputPassword) {
        if (userStore.isUserExist(inputLogin)) {
            authorizationError("User " + inputLogin + " already exists");
        }
        User currentUser = new User(inputLogin, new Password(inputPassword));
        userStore.addUser(currentUser);
        System.out.println("Successful registration");
        session.setSessionUser(currentUser);
        return;
    }

    public void signIn() {
        try {
            System.out.println("Enter login");
            String inputLogin = in.readLine();
            if (userStore.getUser(inputLogin) != null) {
                System.out.println("Enter password");
                String inputPassword = in.readLine();
                if (userStore.compareLoginPass(inputLogin, inputPassword)) {
                    System.out.println("Successful authorization");
                    session.setSessionUser(userStore.getUser(inputLogin));
                    return;
                } else {
                    authorizationError("Wrong password");
                }
            } else {
                authorizationError("No such user");
            }
        } catch (IOException e) {
            System.err.println("Error reading line during signing in: " + e.getMessage());
        }
    }

    public void signIn(String inputLogin, String inputPassword) {
        if (userStore.getUser(inputLogin) != null) {
            if (userStore.compareLoginPass(inputLogin, inputPassword)) {
                System.out.println("Successful authorization");
                session.setSessionUser(userStore.getUser(inputLogin));
                return;
            } else {
                System.out.println("Wrong password");
                return;
            }
        } else {
            System.out.println("No such user");
            return;
        }
    }

    public void authorizationError(String error) {
        System.out.println(error);
        authorize();
    }

    public void authorize() {
        try {
            System.out.println("Sign in (enter \"1\"), sign up (enter \"2\") or continue (enter \"3\")?");
            String answer = in.readLine();
            if (answer.equals("1")) {
                signIn();
            } else if (answer.equals("2")) {
                signUp();
            } else if (answer.equals("3")) {
                return;
            } else {
                authorizationError("Unexpected answer");
            }
        } catch (IOException e) {
            System.err.println("Error reading line during authorization: " + e.getMessage());
        }
    }

}
