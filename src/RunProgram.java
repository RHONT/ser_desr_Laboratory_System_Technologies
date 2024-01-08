import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class RunProgram {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        short[] input = {1, 22, 10, 65, 66, 200, 300,1,126,127,128,129,31,32,33};
        Warp_Array warp = new Warp_Array(input);
        System.out.println("Массив до сериализации");
        System.out.println(Arrays.toString(warp.getArray()));

        FileOutputStream fileOut = new FileOutputStream("demo.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOut);
        oos.writeObject(warp);
        oos.close();
        fileOut.close();


        FileInputStream fileIn = new FileInputStream("demo.txt");
        ObjectInputStream ois = new ObjectInputStream(fileIn);
        Warp_Array deserializedArray = (Warp_Array) ois.readObject();
        ois.close();
        fileIn.close();

        System.out.println("Массив после десериализации");
        System.out.println(Arrays.toString(deserializedArray.getArray()));



        System.out.println("Размер массива: "+warp.getArray().length*16 + " Бит");
        System.out.println("Размер строки: "+warp.getStr().length()*8 + " Бит");

    }
}
