package exam03;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient3 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 50001);
            System.out.println("클라이언트 연결 성공");

            // ㄷㅔ이터보내기
            String sendMessage = "나는 자바가 좋아";
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(sendMessage);
            dos.flush();
            System.out.println("[클라이언트] 데이터보냄:"+ sendMessage);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String receiveMessage = dis.readUTF();
            System.out.println("[클라이언트] 데이터받음"+ receiveMessage);
            socket.close();
            System.out.println("[클라이언트] 연결 끊음");
        } catch (UnknownHostException e) {
            System.out.println("UnknownException" + e.toString());
        } catch (IOException e) {
            System.out.println("IOException" + e.toString());
        }
    }
}
