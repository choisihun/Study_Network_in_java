package exam07;


import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    String chatName;

    public void connect() throws IOException {

        socket = new Socket("10.80.162.166", 50001);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("클라이언트] 서버에 연결 됨");
    }

    public void receve() {
        Thread thread = new Thread(()-> {
            try {
                while (true) {
                    String json = dis.readUTF();
                    JSONObject root = new JSONObject(json);
                    String clientlp = root.getString("clientlp");
                    String chatName = root.getString("chatName");
                    String message = root.getString("message");
                    System.out.println("<" + chatName + "@" + clientlp + message);

                }
            } catch (IOException e) {
                System.out.println("클라이언트] 서버에 연결 끊김");
                System.exit(0);
            }
        });
        thread.start();
    }
    public void send(String json)throws IOException {
        dos.writeUTF(json);
        dos.flush();
    }

    public void unconnect() throws IOException {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            ChatClient chatClient = new ChatClient();
            Scanner scanner = new Scanner(System.in);
            chatClient.connect();
            System.out.println("대화명 입력: ");
            chatClient.chatName = scanner.nextLine();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", "incoming");
            jsonObject.put("data", chatClient.chatName);
            String json = jsonObject.toString();
            System.out.println("----------------------------------");
            System.out.println("보낼 메시지를 입력하고 엔터");
            System.out.println("채팅을 종료하려면 q 또는 Q를 입력하고 엔터");
            System.out.println("----------------------------------");

            while (true) {
                String message = scanner.nextLine();
                if(message.toLowerCase().equals("q")) {
                    break;
                } else {
                    jsonObject.put("command", "incoming");
                    jsonObject.put("data", chatClient.chatName);
                    json = jsonObject.toString();
                    chatClient.send(json);
                }
            }
        } catch (IOException e) {
            System.out.println("서버 연결 안 됨");

        }
        System.out.println("클라이언트 종료");









    }
}
