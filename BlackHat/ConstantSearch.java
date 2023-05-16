package BlackHat;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/*

DATA_SIZE = 18 758 328, Получение записи за О(1).
Avg_name_size = 20

Для того чтобы получать запись за О(1), нужно использовать HashMap, где в качестве ключа будет выступать номер телефона.

Теперь посчитаем память:
Теперь посчитаем память:
1) Номер будет переменной типа long, поэтому под каждый номер выделяем 8 байт.
Итого 8 * DATA_SIZE = 150 066 624 байт <=> 0.14 Гб

2) Имя будет объектом String, пустой объект String (+ заголовок char[]) + Avg_name_size * 2 байта (char) = 56 байт одно имя.
Итого 56 * DATA_SIZE = 1 050 466 368 байт <=> 0.98 Гб

3) Теперь сама HashMap.
Одна нода будет занимать 32 + 24 + 8 (Key и Value) = 64 байта * DATA_SIZE = 1 200 532 992 <=> 1.12 Гб

Итого примерно 0.14 + 0.98 + 1.12 = 2.24 Гб (Отбрасывая всякие доп. расходы на HashMap)

 */

// Практическая оценка памяти на 10 000 000 записях.
// Результат программы = 1 350 567 936 <=> 1,26 Гб
// Результат ручного подсчета = 1 280 000 000 <=> 1,19 Гб

public class ConstantSearch {
    public static void main(String[] args) {
        final int DATA_SIZE = 10_000_000;

        Map<Long, String> map = new HashMap<>();
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long targetNumber = 89000000000L;
        String targetString = "Target String Something";

        long startNumber = 89000000001L;

        for (int i = 0; i < DATA_SIZE - 1; i++) {
            map.put(startNumber + i, generateString());
        }

        map.put(targetNumber, targetString);

        System.out.println(map.get(targetNumber));

        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long actualMemUsed = afterUsedMem - beforeUsedMem;

        System.out.println(actualMemUsed);
    }

    public static String generateString() {
        final int AVERAGE_LENGTH = 20;

        return RandomStringUtils.randomAlphabetic(AVERAGE_LENGTH);
    }
}
