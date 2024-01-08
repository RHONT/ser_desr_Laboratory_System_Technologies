package worktask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Warperr_Array implements Serializable {
    public static final long serialversionUID = 123L;
    private short[] array;
    private transient static final byte step = 93;
    private int lengthArray;
    public String compactString;

    public Warperr_Array(short[] input) {
        array = Arrays.copyOf(input, input.length);
        lengthArray = input.length;
        compactString = null;
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {

        oos.defaultWriteObject();
        this.compactString = generateCompactString();
        oos.writeObject(this.compactString);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        compactString = (String) ois.readObject();
        array = CodeDecodeFunc.deserializeArray(compactString, lengthArray);
    }

    public String generateCompactString() {
        Map<Byte, List<Short>> bucketDigit = CodeDecodeFunc.fillBucketDigit(array);
        compactString = CodeDecodeFunc.fillStringSerialise(bucketDigit);
        return compactString;
    }

    public short[] getArray() {
        return array;
    }

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

    private static class CodeDecodeFunc {

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
