import worktask.Warper_Array;

import java.io.*;
import java.util.Arrays;

/**
 * Пример сериализации путем сохранения в файл и обратную загрузку из него для проверки корректной работы.
 */
public class RunProgram {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        short[] input = {1, 22, 10, 65, 66, 200, 300,1,126,127,128,129,31,32,33};
        Warper_Array warp = new Warper_Array(input);
        System.out.println("Массив до сериализации");
        System.out.println(Arrays.toString(warp.getArray()));

        FileOutputStream fileOut = new FileOutputStream("demo.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOut);
        oos.writeObject(warp);
        oos.close();
        fileOut.close();

        FileInputStream fileIn = new FileInputStream("demo.txt");
        ObjectInputStream ois = new ObjectInputStream(fileIn);
        Warper_Array deserializedArray = (Warper_Array) ois.readObject();
        ois.close();
        fileIn.close();

        System.out.println("Массив после десериализации");
        System.out.println(Arrays.toString(deserializedArray.getArray()));





    }
}
