package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<String> arg = new ArrayList<>(Arrays.asList(args));

        //If there is no -mode, the program should work in enc mode.
        String action = arg.contains("-mode") ? arg.get(arg.indexOf("-mode") + 1) : "enc";
        //If there is no -key, the program should consider that key = 0.
        int key = arg.contains("-key") ? Integer.parseInt(arg.get(arg.indexOf("-key") + 1)) : 0;
        String data = getData(arg);

        char[] input = dataToArr(data);
        char[] output = action.equals("enc") ? encode(input, key) : decode(input,key);

        for (char value : output) {
            System.out.print(value);
        }

    }

    public static char[] dataToArr(String input) {
        char[] out = new char[input.length()];
        for (int i = 0; i <input.length(); i++) {
            out[i] =input.charAt(i);
        }
        return out;
    }

    public static char[] encode(char[] arr, int key) {
        char[] encodedValue = new char[arr.length];
        int library = '~' - ' ';
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i] + key;
            if (value > '~') {
                value = (value - '~') % library - 1;
                encodedValue[i] = (char) (' ' + value);
            } else {
                encodedValue[i] = (char) (arr[i] + key);
            }
        }
        return encodedValue;
    }

    public static char[] decode (char[] arr, int key) {
        char[] encodedValue = new char[arr.length];
        int library = '~' - ' ';
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i] - key;
            if (value < ' ') {
                value = (value - ' ') % library - 1;
                encodedValue[i] = (char)('~' - value);
            } else {
                encodedValue[i] = (char)(arr[i] - key);
            }
        }
        return encodedValue;
    }

    public static String getData(List<String> args) {
        String data = null;
        //If there is no -data, and there is no -in the program should assume that the data is an empty string.
        if (!args.contains("-in") || !args.contains("-data")) {
            data = "";
        }

        //If there are both -data and -in arguments, your program should prefer -data over -in.
        if (args.contains("-in") && args.contains("-data")) {
            data = args.get(args.indexOf("-data") + 1);
        }

        //If arguments contain -in, read data from file. Throw exception if file does not exist.
        if (args.contains("-in")) {
            File file = new File(args.get(args.indexOf("-in") + 1));
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    data = scanner.next();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error, file does not exist!");
            }
        }
        //if
        if (args.contains("-data")) {

           data = args.get(args.indexOf("-data") + 1);
        }

        return data;
    }

}