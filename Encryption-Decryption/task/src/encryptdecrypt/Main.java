package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        if (args.length % 2 != 0){
            System.out.println("Error, invalid arguments.");
            return;
        }

        HashMap<String, String> arg = new HashMap<>();

        for (int i = 0; i < args.length; i+=2){
            arg.put(args[i], args[i+1]);
        }

        String action = arg.getOrDefault("-mode", "enc");
        int key = Integer.parseInt(arg.getOrDefault("-key", "0"));
        String data = getData(arg);
        char[] input = data.toCharArray();
        char[] output = action.equals("enc") ? encode(input, key) : decode(input,key);


        if (!arg.containsKey("-out") ){
            System.out.print(String.valueOf(output));
        } else {
            File file = new File(arg.get("-out"));
            try(FileWriter writer = new FileWriter(file)) {
                writer.write(String.valueOf(output));
            } catch (IOException e) {
                System.out.printf("An exception occurred %s", e.getMessage());
            }

        }

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

    public static String getData(HashMap<String, String> arg) {

        if (!arg.containsKey("-in") && !arg.containsKey("-data")) {
            return "";
        }

        if (arg.containsKey("-in") && arg.containsKey("-data")) {
            return arg.get("-data");
        }

        if (arg.containsKey("-data")) {
            return arg.get("-data");
        }

        //If arguments contain -in, read data from file. Throw exception if file does not exist.
        String data = null;
        if (arg.containsKey("-in")) {
            File file = new File(arg.get("-in"));
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    data = scanner.nextLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error, file does not exist!");
            }
        }
        return data;
    }

}