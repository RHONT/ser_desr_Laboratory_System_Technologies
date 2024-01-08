package tests;

import org.junit.jupiter.api.Test;
import worktask.Warperr_Array;

import java.util.Random;

class Warp_ArrayTest {

    @Test
    void shortArray(){
        int[] ints = new Random().ints(10, 1, 30).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }

    @Test
    void random50Digit(){
        int[] ints = new Random().ints(50, 1, 300).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }

    @Test
    void random100Digit(){
        int[] ints = new Random().ints(100, 1, 300).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }

    @Test
    void random500Digit(){
        int[] ints = new Random().ints(500, 1, 300).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }

    @Test
    void random1000Digit(){
        int[] ints = new Random().ints(1000, 1, 300).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }

    @Test
    void randomOnceDigit(){
        int[] ints = new Random().ints(900, 1, 10).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }


    @Test
    void randomTwiceDigit(){
        int[] ints = new Random().ints(900, 10, 100).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }

    @Test
    void randomtriplesDigit(){
        int[] ints = new Random().ints(900, 100, 300).toArray();
        short[] inShort=convertIntsToShorts(ints);
        Warperr_Array warperr_array=new Warperr_Array(inShort);
        warperr_array.generateCompactString();
        warperr_array.getStatistic();
        System.out.println("-".repeat(50));
    }



    private short[] convertIntsToShorts(int[] inputArray){
        short inShort[] = new short[inputArray.length];

        for(int i = 0; i < inputArray.length; i++)
        {
            inShort[i] = (short)inputArray[i];
        }
        return inShort;
    }




}