package edu.ufp.inf.Util;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class StdIn implements Serializable {
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern EMPTY_PATTERN;
    private static final Pattern EVERYTHING_PATTERN;
    private static Scanner scanner;

    private StdIn() {
    }

    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException var2) {
            line = null;
        }

        return line;
    }

    public static char readChar() {
        try {
            scanner.useDelimiter(EMPTY_PATTERN);
            String ch = scanner.next();

            assert ch.length() == 1 : "Internal (Std)In.readChar() error! Please contact the authors.";

            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException var1) {
            throw new NoSuchElementException("attempts to read a 'char' value from standard input, but no more tokens are available");
        }
    }

    public static String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        } else {
            String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return result;
        }
    }

    public static String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException var1) {
            throw new NoSuchElementException("attempts to read a 'String' value from standard input, but no more tokens are available");
        }
    }

    public static int readInt() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException var2) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attemps to read an 'int' value from standard input, but no more tokens are available");
        }
    }

    public static double readDouble() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException var2) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'double' value from standard input, but no more tokens are available");
        }
    }

    public static float readFloat() {
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException var2) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'float' value from standard input, but there no more tokens are available");
        }
    }

    public static long readLong() {
        try {
            return scanner.nextLong();
        } catch (InputMismatchException var2) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'long' value from standard input, but no more tokens are available");
        }
    }

    public static short readShort() {
        try {
            return scanner.nextShort();
        } catch (InputMismatchException var2) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'short' value from standard input, but no more tokens are available");
        }
    }

    public static byte readByte() {
        try {
            return scanner.nextByte();
        } catch (InputMismatchException var2) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'byte' value from standard input, but no more tokens are available");
        }
    }

    public static boolean readBoolean() {
        try {
            String token = readString();
            if ("true".equalsIgnoreCase(token)) {
                return true;
            } else if ("false".equalsIgnoreCase(token)) {
                return false;
            } else if ("1".equals(token)) {
                return true;
            } else if ("0".equals(token)) {
                return false;
            } else {
                throw new InputMismatchException("attempts to read a 'boolean' value from standard input, but the next token is \"" + token + "\"");
            }
        } catch (NoSuchElementException var1) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from standard input, but no more tokens are available");
        }
    }

    public static String[] readAllStrings() {
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length != 0 && tokens[0].length() <= 0) {
            String[] decapitokens = new String[tokens.length - 1];

            for(int i = 0; i < tokens.length - 1; ++i) {
                decapitokens[i] = tokens[i + 1];
            }

            return decapitokens;
        } else {
            return tokens;
        }
    }

    public static String[] readAllLines() {
        ArrayList<String> lines = new ArrayList();

        while(hasNextLine()) {
            lines.add(readLine());
        }

        return (String[])lines.toArray(new String[0]);
    }

    public static int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            vals[i] = Integer.parseInt(fields[i]);
        }

        return vals;
    }

    public static long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            vals[i] = Long.parseLong(fields[i]);
        }

        return vals;
    }

    public static double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            vals[i] = Double.parseDouble(fields[i]);
        }

        return vals;
    }

    private static void resync() {
        setScanner(new Scanner(new BufferedInputStream(System.in), "UTF-8"));
    }

    private static void setScanner(Scanner scanner) {
        StdIn.scanner = scanner;
        StdIn.scanner.useLocale(LOCALE);
    }

    /** @deprecated */
    @Deprecated
    public static int[] readInts() {
        return readAllInts();
    }

    /** @deprecated */
    @Deprecated
    public static double[] readDoubles() {
        return readAllDoubles();
    }

    /** @deprecated */
    @Deprecated
    public static String[] readStrings() {
        return readAllStrings();
    }

    public static void main(String[] args) {
        StdOut.print("Type a string: ");
        String s = readString();
        StdOut.println("Your string was: " + s);
        StdOut.println();
        StdOut.print("Type an int: ");
        int a = readInt();
        StdOut.println("Your int was: " + a);
        StdOut.println();
        StdOut.print("Type a boolean: ");
        boolean b = readBoolean();
        StdOut.println("Your boolean was: " + b);
        StdOut.println();
        StdOut.print("Type a double: ");
        double c = readDouble();
        StdOut.println("Your double was: " + c);
        StdOut.println();
    }

    static {
        LOCALE = Locale.US;
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        EMPTY_PATTERN = Pattern.compile("");
        EVERYTHING_PATTERN = Pattern.compile("\\A");
        resync();
    }
}
