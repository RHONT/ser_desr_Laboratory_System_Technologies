package worktask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Класс оболочка массива
 */
public class Warper_Array implements Serializable {
    public static final long serialversionUID = 123L;
    private transient short[] array;
    private transient static final byte step = 93;
    private int lengthArray;
    public String compactString;

    public Warper_Array(short[] input) {
        array = Arrays.copyOf(input, input.length);  // Применяем глубокое копирование для входного массива
        lengthArray = input.length;                  // запоминаем длину входного массива
        compactString = null;                        // Если заходит новый массив, нужно обнулить строкове представление
    }

    /**
     * Изменяю вручную способ сериализации при записи в файл
     * @param oos
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        this.compactString = generateCompactString();
        oos.writeObject(this.compactString);
    }

    /**
     * Изменяю вручную процесс десириализации из файла
     * @param ois
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        compactString = (String) ois.readObject();
        array = CodeDecodeFunc.deserializeArray(compactString, lengthArray);
    }

    /**
     * Генерация строкового представления на основе текущего массива
     * @return
     */
    public String generateCompactString() {
        Map<Byte, List<Short>> bucketDigit = CodeDecodeFunc.fillBucketDigit(array);
        compactString = CodeDecodeFunc.fillStringSerialise(bucketDigit);
        return compactString;
    }

    public short[] getArray() {
        return array;
    }

    /**
     * Если представление строковое уже есть отдаем результат.
     * @return
     */
    public String getCompactString() {
        if (compactString == null) {
            throw new RuntimeException(
                    "Представление массива строкой не сгенерировано. Воспользуйтесь функцией generateCompactString()");
        }
        return compactString;
    }

    public void getStatistic() {
        int sizeArrayInByte = getArray().length * 16;
        int sizeCompactStringInByte = getCompactString().length() * 8;
        double compressionRatio =100d - (100d / sizeArrayInByte) * sizeCompactStringInByte;

        System.out.println("Массив до сериализации");
        System.out.println(Arrays.toString(getArray()));
        System.out.println("Представление массива в строке: ");
        System.out.println(getCompactString());
        System.out.println("Массив после десериализации");
        System.out.println(Arrays.toString(CodeDecodeFunc.deserializeArray(compactString,lengthArray)));

        System.out.println("Размер массива: " + sizeArrayInByte + " Бит");
        System.out.println("Размер строки: " + sizeCompactStringInByte + " Бит");
        System.out.println("Коэффициент сжатия: " + compressionRatio + "%");

    }

    /**
     * Отдельный статичный класс отвечающий за логику функции сер/десер
     */
    private static class CodeDecodeFunc {

        /**
         * Помещаем наполненные корзины в метод, которые их потом переведет в строковое представление
         * @param bucket
         * @return
         */
        private static String fillStringSerialise(Map<Byte, List<Short>> bucket) {
            StringBuilder strBuild = new StringBuilder();
            for (Map.Entry<Byte, List<Short>> element : bucket.entrySet()) {
                if (!element.getValue().isEmpty()) {
                    strBuild.append(" ").append(element.getKey());
                    for (Short valShort : element.getValue()) {
                        char ch = (char) (short) valShort;
                        strBuild.append(ch);
                    }
                }
            }
            return strBuild.toString();
        }

        /**
         * Сортируем числа по корзинам. Приводим из к диапазону 33-126, а ключ Byte будет указывать, как нам
         * вернуть прежнее значени числа
         * @param array
         * @return
         */
        private static Map<Byte, List<Short>> fillBucketDigit(short[] array) {
            Map<Byte, List<Short>> bucket = new HashMap<>();
            bucket.put((byte) 0, new ArrayList<>());
            bucket.put((byte) 1, new ArrayList<>());
            bucket.put((byte) 2, new ArrayList<>());
            bucket.put((byte) 3, new ArrayList<>());
            for (int i = 0; i < array.length; i++) {

                if (array[i] > 0 && array[i] < 33) {
                    bucket.get((byte) 0).add((short) (array[i] + step));
                } else if (array[i] > 32 && array[i] < 127) {
                    bucket.get((byte) 1).add(array[i]);
                } else {
                    int integer = array[i] / step;
                    int remainder = array[i] % step;
                    if (remainder < 33) {
                        bucket.get((byte) (integer)).add((short) (remainder + step));
                    } else
                        bucket.get((byte) (integer + 1)).add((short) remainder);
                }
            }
            return bucket;
        }

        /**
         * Возвращаем исходный массив на основе строки и длинный массива, который запомнили на этапе конструктора
         * @param compactString
         * @param lengthArray
         * @return
         */
        private static short[] deserializeArray(String compactString, int lengthArray) {
            short[] array = new short[lengthArray];
            int stepDeserializeArray = 0;
            byte mnoshitel = 0;
            char[] chars = compactString.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == ' ') {
                    mnoshitel = Byte.parseByte(String.valueOf(chars[i + 1]));
                    i++;
                } else {
                    if (mnoshitel == 0) {
                        array[stepDeserializeArray] = (short) (chars[i] - step);
                    } else if (mnoshitel == 1) {
                        array[stepDeserializeArray] = (short) (chars[i]);
                    } else {
                        array[stepDeserializeArray] = (short) (chars[i] + ((mnoshitel - 1) * step));
                    }
                    stepDeserializeArray++;
                }
            }
            return array;
        }
    }
}
