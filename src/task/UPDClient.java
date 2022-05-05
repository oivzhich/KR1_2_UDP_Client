package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/***
 * Разработать приложение на основе UDP-соединения, позволяющее осуществлять взаимодействие клиента и сервера
 * по совместному решению задач обработки информации.
 * Приложение должно располагать возможностью передачи и модифицирования получаемых (передаваемых) данных.
 * Возможности клиента: передать серверу исходные параметры (вводятся с клавиатуры) для расчета значения функции,
 * а также получить расчетное значение функции.
 * Возможности сервера: по полученным от клиента исходным параметрам рассчитать значение функции,
 * передать клиенту расчетное значение функции, а также сохранить исходные параметры и значение функции в файл.
 */
public class UPDClient {

    private static void sendToServer() {
        try {
            String clientMessage = stdin.readLine();
            System.out.println("Вы ввели: " + clientMessage);
            DatagramPacket datagramPacket = new DatagramPacket(clientMessage.getBytes(), clientMessage.length(), ip, 3000);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DatagramSocket datagramSocket;
    private static InetAddress ip;
    private static BufferedReader stdin;

    public static void main(String[] args) throws Exception {
        System.out.println("Подключение к серверу....");
        datagramSocket = new DatagramSocket();
        ip = InetAddress.getByName("127.0.0.1");

        //создание буферизированного символьного потока ввода
        stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Введите значение x:");
        sendToServer();

        System.out.println("Введите значение y:");
        sendToServer();

        System.out.println("Введите значение z:");
        sendToServer();

        //получаем сообщение от сервера
        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(response);
        String serverMessage = new String(buffer, 0, response.getLength());
        System.out.printf("Результат вычислений: %s", serverMessage);

        datagramSocket.close();
    }
}
