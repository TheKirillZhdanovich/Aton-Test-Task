package Friends;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FriendsTask {

    // Очередь реплик. Она будет выступать монитором
    static List<String> strings = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws FileNotFoundException {

        // Создание потоков
        RoleThread ChandlerThread = new RoleThread("chandler");
        RoleThread JoeyThread = new RoleThread("joey");
        RoleThread MonicaThread = new RoleThread("monica");
        RoleThread PhoebeThread = new RoleThread("phoebe");
        RoleThread RachelThread = new RoleThread("rachel");
        RoleThread RossThread = new RoleThread("ross");
        Reader reader = new Reader();

        // Запуск потоков
        reader.start();
        ChandlerThread.start();
        JoeyThread.start();
        MonicaThread.start();
        PhoebeThread.start();
        RachelThread.start();
        RossThread.start();

        while (reader.isAlive()) {

        }

        // Прерывание потоков после того, как отыграна вся сцена
        ChandlerThread.interrupt();
        JoeyThread.interrupt();
        MonicaThread.interrupt();
        PhoebeThread.interrupt();
        RachelThread.interrupt();
        RossThread.interrupt();
    }

    // Поток отвечающий за чтение новых строк из файла с репликами
    static class Reader extends Thread {

        // Файл со сценкой
        String path = "Friends/script.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        Reader() throws FileNotFoundException {

        }

        @Override
        public void run() {
            while (scanner.hasNextLine()) {
                synchronized (strings) {
                    strings.add(scanner.nextLine());

                    // Как только получили новою реплику, оповещаем все потоки об этом
                    strings.notifyAll();
                }

                try {
                    // Засыпаем для того, чтобы потоки успели обработать текущую реплику, до того момента когда
                    // Reader читает новую строку
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class RoleThread extends Thread {

        // Имя участника диалога
        private final String name;

        RoleThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (strings) {
                    try {

                        // Ждем пока не получим новую реплику
                        strings.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Поток проверяет, должен ли он печать реплику.
                    if (!strings.isEmpty()) {
                        String string = strings.get(0);

                        if (string.toLowerCase().split(" ")[0].equals(name + ":")) {

                            // Печатаем строчку и удаляем ее из очереди
                            System.out.println(strings.remove(0));
                        }
                    }
                }
            }

        }
    }
}
