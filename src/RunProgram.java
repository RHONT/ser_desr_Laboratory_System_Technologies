import worktask.Warperr_Array;

import java.io.*;
import java.util.Arrays;


public class RunProgram {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        short[] input = {1, 22, 10, 65, 66, 200, 300,1,126,127,128,129,31,32,33};
        Warperr_Array warp = new Warperr_Array(input);
        System.out.println("Массив до сериализации");
        System.out.println(Arrays.toString(warp.getArray()));

        FileOutputStream fileOut = new FileOutputStream("demo.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOut);
        oos.writeObject(warp);
        oos.close();
        fileOut.close();


        FileInputStream fileIn = new FileInputStream("demo.txt");
        ObjectInputStream ois = new ObjectInputStream(fileIn);
        Warperr_Array deserializedArray = (Warperr_Array) ois.readObject();
        ois.close();
        fileIn.close();

        System.out.println("Массив после десериализации");
        System.out.println(Arrays.toString(deserializedArray.getArray()));





    }
}
