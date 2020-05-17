package github.javaguide.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 16:56:00
 */
public class HelloServer {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);

    public void start(int port) {
        try (ServerSocket server = new ServerSocket(port);) {
            Socket socket;
            while ((socket = server.accept()) != null) {
                logger.info("client connected");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
                    Message message = (Message) objectInputStream.readObject();
                    logger.info("server receive message:" + message.getContent());
                    message.setContent("new content");
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    logger.error("occur exception:", e);
                }
            }
        } catch (IOException e) {
            logger.error("occur IOException:", e);
        }
    }

    public static void main(String[] args) {
        HelloServer helloServer = new HelloServer();
        helloServer.start(6666);
    }
}

