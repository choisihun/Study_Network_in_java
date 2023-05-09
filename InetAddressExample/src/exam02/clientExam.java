package exam02;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.OutputStream;

public class clientExam {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 50001);
            System.out.println("클라이언트 연결 성공");

            // ㄷㅔ이터보내기
            String sendMessage = "나는 자바가 좋아";
            OutputStream os = socket.getOutputStream();
            byte[] bytes = sendMessage.getBytes("UTF-8");
            os.write(bytes);
            os.flush();
            System.out.println("[클라이언트] 데이터보냄:"+ sendMessage);

            InputStream is = socket.getInputStream();
            bytes = new byte[1024];
            int readByteCouny = is.read(bytes);
            String receiveMessage = new String(bytes, 0, readByteCouny, "UTF-8");
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
