import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Warp_Array implements Serializable {
    public static final long serialversionUID = 123L;
    private short[] array;
    private transient static final byte step = 93;
    private int lengthArray;
    private transient short[] deserializeArray;
    public String compactString;
    private Map<Byte, List<Short>> bucket = new HashMap<>();

    public Warp_Array(short[] input) {
        array = Arrays.copyOf(input, input.length);
        lengthArray = input.length;
        bucket.put((byte) 0, new ArrayList<>());
        bucket.put((byte) 1, new ArrayList<>());
        bucket.put((byte) 2, new ArrayList<>());
        bucket.put((byte) 3, new ArrayList<>());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        fillBucketDigit();
        oos.defaultWriteObject();//For default serialization of Student class

        this.compactString = fillStringSerialise();

        //serialization of regNo
//        oos.writeObject(this.regNo);
    }

    private String fillStringSerialise() {
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

    private short[] deserializeArray() {
        deserializeArray = new short[lengthArray];
        int stepDeserializeArray = 0;
        byte mnoshitel = 0;
        char[] chars = compactString.toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                mnoshitel = Byte.parseByte(String.valueOf(chars[i + 1]));
                i++;
            } else {
                if (mnoshitel == 0) {
                    deserializeArray[stepDeserializeArray] = (short) (chars[i] - step);
                } else if (mnoshitel == 1) {
                    deserializeArray[stepDeserializeArray] = (short) (chars[i]);
                } else {
                    deserializeArray[stepDeserializeArray] = (short) (chars[i] + ((mnoshitel - 1) * step));
                }
                stepDeserializeArray++;
            }
        }
        return deserializeArray;
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
//        ois.defaultReadObject();////For default deserialization of Student class
//        String s = (String)ois.readObject();
//        this.regNo = s;
    }

    private void fillBucketDigit() {
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
    }

    public Map<Byte, List<Short>> getBucket() {
        return bucket;
    }

    public short[] getArray() {
        return array;
    }

    public String getStr() {
        return compactString.toString();
    }

    public short[] getDeserializeArray() {
        return deserializeArray;
    }
}
