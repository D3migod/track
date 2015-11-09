package track.project.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.authorization.UserStore;
import track.project.authorization.UserStoreStub;
import track.project.commands.ChatCreateCommand;
import track.project.commands.ChatFindCommand;
import track.project.commands.ChatHistoryCommand;
import track.project.commands.ChatSendCommand;
import track.project.commands.Command;
import track.project.commands.CommandHandler;
import track.project.commands.CommandType;
import track.project.commands.ExitCommand;
import track.project.commands.HelpCommand;
import track.project.commands.LoginCommand;
import track.project.commands.RegisterCommand;
import track.project.commands.UserCommand;
import track.project.commands.UserInfoCommand;
import track.project.commands.UserPassCommand;
import track.project.message.MessageStore;
import track.project.message.MessageStoreStub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Булат on 04.11.2015.
 */
public class ThreadedServer {

    public static final int PORT = 19000;
    static Logger log = LoggerFactory.getLogger(ThreadedServer.class);
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;


    public ThreadedServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) {
        try {
            this.protocol = protocol;
            this.sessionManager = sessionManager;
            this.commandHandler = commandHandler;
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Protocol protocol = new StringProtocol();
        SessionManager sessionManager = new SessionManager();

        UserStore userStore = new UserStoreStub();
        MessageStore messageStore = new MessageStoreStub();

        Map<CommandType, Command> commands = new HashMap<>();
        commands.put(CommandType.CHAT_CREATE, new ChatCreateCommand(userStore, messageStore));
        commands.put(CommandType.CHAT_FIND, new ChatFindCommand(messageStore));
        commands.put(CommandType.CHAT_HISTORY, new ChatHistoryCommand(messageStore));
        commands.put(CommandType.CHAT_SEND, new ChatSendCommand(messageStore));
        commands.put(CommandType.USER_EXIT, new ExitCommand(messageStore, userStore));
        commands.put(CommandType.USER_HELP, new HelpCommand(commands));
        commands.put(CommandType.USER_LOGIN, new LoginCommand(userStore, sessionManager));
        commands.put(CommandType.USER_REGISTER, new RegisterCommand(userStore, sessionManager));
        commands.put(CommandType.USER_USER, new UserCommand());
        commands.put(CommandType.USER_INFO, new UserInfoCommand(userStore));
        commands.put(CommandType.USER_PASS, new UserPassCommand());
        CommandHandler handler = new CommandHandler(commands);
        ThreadedServer server = new ThreadedServer(protocol, sessionManager, handler);

        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {
        log.info("Started, waiting for connection");

        isRunning = true;
        while (isRunning) {
            Socket socket = sSocket.accept();
            log.info("Accepted. " + socket.getInetAddress());

            ConnectionHandler handler = new SocketConnectionHandler(protocol, sessionManager.createSession(), socket);
            handler.addListener(commandHandler);

            handlers.put(internalCounter.incrementAndGet(), handler);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    public void stopServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }
}
