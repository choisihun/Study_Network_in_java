package exam05;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class NewsServer {
    private static  DatagramSocket datagramSocket;
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println("서버를 종료하려면 q를 눌러주세요----");
        System.out.println("---------------------------");

        startServer();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String key = scanner.nextLine();

            if(key.toLowerCase().equals("q")); {
                break;
            }

        }

        scanner.close();
        stopServer();

    }

    private static void stopServer() {
        datagramSocket.close();
        executorService.shutdownNow();
        System.out.println("서버] 종료됨");
    }

    private static void startServer() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    datagramSocket = new DatagramSocket(50001);
                    System.out.println("서버] 시작됨");

                    while (true) {
                        DatagramPacket receivepacket = new DatagramPacket(new byte[1024], 1024);

                        System.out.println("클라이언트의 희망 뉴스 종류를 얻기 위해 대기중");

                        datagramSocket.receive(receivepacket);

                        executorService.execute(()->{
                            try {
                                String newkind = new String(receivepacket.getData(),0, receivepacket.getLength(), "UTF-8");

                                SocketAddress socketAddress = receivepacket.getSocketAddress();

                                for(int i = 0;i<=100;i++) {
                                    String data = newkind+"뉴스"+i;

                                    byte[] bytes = data.getBytes("UTF-8");
                                    DatagramPacket sendPacket = new DatagramPacket(bytes,0,bytes.length, socketAddress);

                                    datagramSocket.send(sendPacket);

                                    Thread.sleep(1000);

                                }

                            } catch (Exception e) {
                                System.out.println("서버] : "+e.getMessage());
                            }
                        });
                    }
                } catch (Exception e) {
                    System.out.println("서버] : "+e.getMessage());
                }

            }
        };
        thread.start();
    }
}
