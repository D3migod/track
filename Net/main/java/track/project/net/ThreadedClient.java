package track.project.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.commands.result.ResultMessage;
import track.project.message.Message;
import track.project.message.SendMessage;
import track.project.message.request.ChatCreateMessage;
import track.project.message.request.ChatFindMessage;
import track.project.message.request.ChatHistoryMessage;
import track.project.message.request.ChatSendMessage;
import track.project.message.request.ExitMessage;
import track.project.message.request.HelpMessage;
import track.project.message.request.LoginMessage;
import track.project.message.request.RegisterMessage;
import track.project.message.request.UserInfoMessage;
import track.project.message.request.UserMessage;
import track.project.message.request.UserPassMessage;
import track.project.session.Session;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Булат on 04.11.2015.
 */


/**
 * Клиентская часть
 */
public class ThreadedClient implements MessageListener {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";
    static Logger log = LoggerFactory.getLogger(ThreadedClient.class);
    ConnectionHandler handler;
    Protocol protocol;

    public ThreadedClient(Protocol protocol) {
        this.protocol = protocol;
        try {
            Socket socket = new Socket(HOST, PORT);
            Session session = new Session();
            handler = new SocketConnectionHandler(protocol, session, socket);

            // Этот класс будет получать уведомления от socket handler
            handler.addListener(this);

            Thread socketHandler = new Thread(handler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            // exit, failed to open socket
        }
    }

    public static void main(String[] args) throws Exception {
        Protocol protocol = new StringProtocol();
        ThreadedClient client = new ThreadedClient(protocol);

        Scanner scanner = new Scanner(System.in);
        System.out.println("$");
        while (true) {
            String input = scanner.nextLine();
            client.processInput(input);
            if ("\\exit".equals(input)) {
                return;
            }
        }
    }

    public void processInput(String line) throws IOException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        switch (cmdType) {
            case "\\chat_create":
                if (tokens.length > 1) {
                    ChatCreateMessage chatCreateMessage = new ChatCreateMessage();
                    List<Long> participantIds = new LinkedList<>();
                    for (int i = 1; i < tokens.length; i++) {
                        participantIds.add(Long.valueOf(tokens[i]));
                    }
                    chatCreateMessage.setParticipantsIds(participantIds);
                    handler.send(chatCreateMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;

            case "\\login":
                if (tokens.length == 3) {
                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.setLogin(tokens[1]);
                    loginMessage.setPass(tokens[2]);
                    handler.send(loginMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\register":
                if (tokens.length == 3) {
                    RegisterMessage registerMessage = new RegisterMessage();
                    registerMessage.setLogin(tokens[1]);
                    registerMessage.setPass(tokens[2]);
                    handler.send(registerMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\chat_send":
                if (tokens.length > 2) {
                    if (tokens[1].matches("\\d+")) {
                        ChatSendMessage chatSendMessage = new ChatSendMessage();
                        chatSendMessage.setChatId(Long.valueOf(tokens[1]));
                        StringBuilder builder = new StringBuilder();
                        for (int i = 2; i < tokens.length; i++) {
                            builder.append(tokens[i] + " ");
                        }
                        chatSendMessage.setMessage(builder.toString());
                        handler.send(chatSendMessage);
                    } else {
                        logWrongArgumentsType(cmdType);
                    }
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\user":
                if (tokens.length == 2) {
                    UserMessage userMessage = new UserMessage();
                    userMessage.setNick(tokens[1]);
                    handler.send(userMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\user_info":
                if (tokens.length == 2) {
                    UserInfoMessage userInfoMessage = new UserInfoMessage();
                    if (tokens[1].matches("\\d+")) {
                        userInfoMessage.setUserId(Long.valueOf(tokens[1]));
                    } else {
                        logWrongArgumentsType(cmdType);
                    }
                    handler.send(userInfoMessage);
                } else if (tokens.length == 1) {
                    UserInfoMessage userInfoMessage = new UserInfoMessage();
                    handler.send(userInfoMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\user_pass":
                if (tokens.length == 3) {
                    UserPassMessage userPassMessage = new UserPassMessage();
                    userPassMessage.setOldPassword(tokens[1]);
                    userPassMessage.setNewPassword(tokens[2]);
                    handler.send(userPassMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\chat_history":
                if (tokens.length == 3) {
                    if (tokens[1].matches("\\d+") && tokens[2].matches("\\d+")) {
                        ChatHistoryMessage chatHistoryMessage = new ChatHistoryMessage();
                        chatHistoryMessage.setChatId(Long.valueOf(tokens[1]));
                        chatHistoryMessage.setMessageNumber(Integer.valueOf(tokens[2]));
                        handler.send(chatHistoryMessage);
                    } else {
                        logWrongArgumentsType(cmdType);
                    }
                } else if (tokens.length == 2) {
                    if (tokens[1].matches("\\d+")) {
                        ChatHistoryMessage chatHistoryMessage = new ChatHistoryMessage();
                        chatHistoryMessage.setChatId(Long.valueOf(tokens[1]));
                        handler.send(chatHistoryMessage);
                    } else {
                        logWrongArgumentsType(cmdType);
                    }
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\help":
                if (tokens.length == 1) {
                    HelpMessage helpMessage = new HelpMessage();
                    handler.send(helpMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\chat_find":
                if (tokens.length == 3) {
                    ChatFindMessage chatFindMessage = new ChatFindMessage();
                    chatFindMessage.setChatId(Long.valueOf(tokens[1]));
                    chatFindMessage.setSearchWord(tokens[2]);
                    handler.send(chatFindMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;
            case "\\exit":
                if (tokens.length == 1) {
                    ExitMessage exitMessage = new ExitMessage();
                    handler.send(exitMessage);
                } else {
                    logWrongArgumentsNumber(cmdType);
                }
                break;

            default:
                System.out.println("Invalid input: " + line);
        }


    }

    /**
     * Получено сообщение из handler, как обрабатывать
     */
    @Override
    public void onMessage(Session session, Message message) {
        // TODO: все таки лучше, если в ответ также будут приходить типизированные сообщения extends Message
        // для каждого конкретного случая. Иначе скоро будет много веток. А если нужно возвратить не один объект, а несколько
        // то вообще не будет работать

        // Также можно переиспользовать код CommandHandler на клиенте
        if (message instanceof ResultMessage) {
            ResultMessage resultMessage = (ResultMessage) message;
            Object returnedObject = resultMessage.getReturnedObject();
            log.info("Returned object: {}", returnedObject);
            if (returnedObject instanceof String) {
                System.out.printf("%s", (String) returnedObject + "\n");
            } else if (returnedObject instanceof List) {
                for (Object responseObject : (List) returnedObject) {
                    System.out.printf("%s", responseObject.toString() + "\n");
                }
            } else if (returnedObject == null) {
                log.info("Success");
            } else {
                System.out.printf("%s", "Something unfamiliar received\n");
                log.info("Unproper returned object class: {}", returnedObject.getClass());
            }
        } else if (message instanceof SendMessage) {
            System.out.printf("%s", ((SendMessage) message).getTimeMessage());
        }
    }

    private void logWrongArgumentsNumber(String cmdType) {
        log.info("Wrong number of arguments: {}", cmdType);
    }

    private void logWrongArgumentsType(String cmdType) {
        log.info("Wrong number of arguments: {}", cmdType);
    }

}
