import java.util.Arrays;
import java.util.Random;

public class RunProgram {
    public static void main(String[] args) {

        short[] input = {1, 22, 10, 65, 66, 200, 300,1,126,127,128,129,31,32,33};
        Warp_Array warp = new Warp_Array(input);
        System.out.println(warp.getArray()==input );


        System.out.println("Размер массива: "+warp.getArray().length*16 + " Бит");
        System.out.println("Размер строки: "+warp.getStr().length()*8 + " Бит");
        System.out.println(Arrays.toString(warp.getDeserializeArray()));

    }
}
