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
        String alg = arg.getOrDefault("-alg", "shift");
        String data = getData(arg);
        char[] input = data.toCharArray();

        String output = crypter(action, alg, key, input);


        if (!arg.containsKey("-out") ){
            System.out.print(output);
        } else {
            File file = new File(arg.get("-out"));
            try(FileWriter writer = new FileWriter(file)) {
                writer.write(output);
            } catch (IOException e) {
                System.out.printf("An exception occurred %s", e.getMessage());
            }

        }

    }

    static abstract class Common {
        protected int key;
        protected char[] data;

        protected Common (int key, char[] data)  {
            this.key = key;
            this.data = data;
        }

        public abstract char[] encode();

        public abstract char[] decode();
    }

    static class Shift extends Common {

        private final List<Character> lowerDictionary = new ArrayList<>(List.of('a', 'b', 'c', 'd'
                , 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v'
                , 'w', 'x', 'y', 'z'));
        private final List<Character> upperDictionary = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F'
                , 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'
                , 'W', 'X', 'Y', 'Z'));
        char[] encodedValue = new char[data.length];

        public Shift(int key, char[] data) {
            super(key, data);
        }

        @Override
        public char[] encode() {

            for (int i = 0; i < data.length; i++) {
                int value = data[i] + key;

                if (lowerDictionary.contains(data[i])) {
                    if (value > 'z') {
                        value = (value - 'z') % lowerDictionary.size()-1;
                        encodedValue[i] = lowerDictionary.get(value);
                    } else {
                        encodedValue[i] = (char) (data[i] + key);
                    }
                } else if (upperDictionary.contains(data[i])) {
                    if (value > 'Z') {
                        value = (value - 'Z') % lowerDictionary.size()-1;
                        encodedValue[i] = upperDictionary.get(value);
                    } else {
                        encodedValue[i] = (char) (data[i] + key);
                    }
                } else {
                    encodedValue[i] = data[i];
                }
            }
            return encodedValue;
        }

        @Override
        public char[] decode() {

            for (int i = 0; i < data.length; i++) {
                int value = data[i] - key;

                if (lowerDictionary.contains(data[i])) {
                    if (value < 'a') {
                        value = (value - 'z') % lowerDictionary.size() - 1;
                        encodedValue[i] = lowerDictionary.get(lowerDictionary.size()  - (value * -1));
                    } else {
                        encodedValue[i] = (char) (data[i] - key);
                    }
                } else if (upperDictionary.contains(data[i])) {
                    if (value < 'A') {
                        value = (value - 'Z') % upperDictionary.size() - 1;
                        encodedValue[i] = upperDictionary.get(lowerDictionary.size() - (value * -1));
                    } else {
                        encodedValue[i] = (char) (data[i] - key);
                    }
                } else {
                    encodedValue[i] = data[i];
                }
            }
            return encodedValue;
        }
    }

    static class Unicode extends Common {
        private final int dictionary = '~' - ' ';
        char[] encodedValue = new char[data.length];
        public Unicode(int key, char[] data) {
            super(key, data);
        }

        @Override
        public char[] encode() {

            for (int i = 0; i < data.length; i++) {
                int value = data[i] + key;
                if (value > '~') {
                    value = (value - '~') % dictionary - 1;
                    encodedValue[i] = (char) (' ' + value);
                } else {
                    encodedValue[i] = (char) (data[i] + key);
                }
            }
            return encodedValue;
        }

        @Override
        public char[] decode () {

            for (int i = 0; i < data.length; i++) {
                int value = data[i] - key;
                if (value < ' ') {
                    value = (value - ' ') % dictionary - 1;
                    encodedValue[i] = (char)('~' - value);
                } else {
                    encodedValue[i] = (char)(data[i] - key);
                }
            }
            return encodedValue;
        }
    }

    public static String crypter (String action, String alg, int key, char[] data) {

        if ("unicode".equals(alg)) {
            Common unicode = new Unicode(key, data);
            char[] output = action.equals("enc") ? unicode.encode() : unicode.decode();
            return String.valueOf(output);
        } else {
            Common shift = new Shift(key, data);
            char[] output = action.equals("enc") ? shift.encode() : shift.decode();
            return String.valueOf(output);
        }
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