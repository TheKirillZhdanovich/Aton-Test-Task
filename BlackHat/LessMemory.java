package BlackHat;

import org.apache.commons.lang3.RandomStringUtils;

/*

DATA_SIZE = 18 758 328, Минимальное кол-во памяти.
Avg_name_size = 20

Для того чтобы хранить данные с минимальным количеством памяти, при условии, что кол-во данных не будет меняться, нужно
использовать стандартный массив данных типа long[] и String[]. Получать запись мы можем по индексу т.к.
нужный телефон и имя будут храниться под одним индексом. Если же кол-во записей может меняться, то тогда для минимизации
памяти лучше всего использовать ArrayList.

Теперь посчитаем память:
1) Номер будет переменной типа long, поэтому под каждый номер выделяем 8 байт.
Итого 8 * DATA_SIZE = 150 066 624 байт <=> 0.14 Гб

2) Имя будет объектом String, пустой объект String (+ заголовок char[]) + Avg_name_size * 2 байта (char) = 56 байт одно имя.
Итого 56 * DATA_SIZE = 1 050 466 368 байт <=> 0.98 Гб

3) long[DATA_SIZE] = 0.14 Гб и String[DATA_SIZE] = 0.98 Гб (Заголовки массивом можно не учитывать)

Итого получили примерно 1.12 Гб

*/

// Практическая оценка памяти на 10 000 000 записях
// Результат программы = 730 684 824 <=> 0.68 Гб
// Результат ручного подсчета = 640 000 000 <=> 0.6 Гб

public class LessMemory {
    public static void main(String[] args) {
        final int DATA_SIZE = 18_758_328;

        long[] numbers = new long[DATA_SIZE];
        String[] names = new String[DATA_SIZE];
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long targetNumber = 89000000000L;
        String targetString = "Target String Something";

        long startNumber = 89000000001L;

        for (int i = 0; i < DATA_SIZE - 1; i++) {
            numbers[i] = startNumber + i;
            names[i] = generateString();
        }

        numbers[DATA_SIZE - 1] = targetNumber;
        names[DATA_SIZE - 1] = targetString;

        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long actualMemUsed = afterUsedMem - beforeUsedMem;

        System.out.println(actualMemUsed);

    }

    public static String generateString() {
        final int AVERAGE_LENGTH = 20;

        return RandomStringUtils.randomAlphabetic(AVERAGE_LENGTH);
    }
}
