import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class serverExample {

    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        System.out.println("----------------------------------");
        System.out.println("서버를 종료하려면 큐를 입력하고 엔터를 누르세요");
        System.out.println("----------------------------------");

        startServer();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String key = scanner.nextLine();
            if (key.toLowerCase().equals("q")) {
                break;
            }
        }
        scanner.close();
        stopServer();
    }
    private static void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {

        }
    }

    public static void startServer() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(50001);
                    System.out.println("[서버] 시작됨");

                    while (true) {
                        System.out.println("서버연결요청을 기다림");
                        Socket socket = serverSocket.accept();
                        InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
                        String clientIp = isa.getHostString();
                        System.out.println("서버" + clientIp + "의 연결요청을 수락함");
                        // 데이터 받기
                        InputStream is = socket.getInputStream();
                        byte[] bytes = new byte[1024];
                        int readByteCount = is.read(bytes);
                        String message = new String(bytes, 0, readByteCount, "UTF-8");
                        // 데이터보내기
                        OutputStream os = socket.getOutputStream ();
                        bytes = message.getBytes ("UTF-8");
                        os.write(bytes);
                        os.flush();
                        System.out.println("[서버] 받은 데이터를 다시 보냄: message");


                        socket.close();
                        System.out.println("서버" + clientIp + "의 연결을 끊음");

                    }

                } catch (IOException e) {
                    System.out.println("서버" + e.getMessage());
                }
            }
        };
        thread.start();
    }
}
