import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import static CodeDecodeFunc.*;

public class Warp_Array implements Serializable {
    public static final long serialversionUID = 123L;
    private short[] array;
    private transient static final byte step = 93;
    private int lengthArray;
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
        oos.defaultWriteObject();
        this.compactString = fillStringSerialise();
        oos.writeObject(this.compactString);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        compactString = (String) ois.readObject();
        array=deserializeArray();
    }



    private short[] deserializeArray() {
        array = new short[lengthArray];
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


    public short[] getArray() {
        return array;
    }

    public String getStr() {
        return compactString;
    }
}
