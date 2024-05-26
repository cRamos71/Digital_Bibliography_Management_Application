package edu.ufp.inf.Util;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class In implements Serializable {
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern EMPTY_PATTERN;
    private static final Pattern EVERYTHING_PATTERN;
    private Scanner scanner;

    public In() {
        this.scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        this.scanner.useLocale(LOCALE);
    }

    public In(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket argument is null");
        } else {
            try {
                InputStream is = socket.getInputStream();
                this.scanner = new Scanner(new BufferedInputStream(is), "UTF-8");
                this.scanner.useLocale(LOCALE);
            } catch (IOException var3) {
                throw new IllegalArgumentException("Could not open " + socket, var3);
            }
        }
    }

    public In(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url argument is null");
        } else {
            try {
                URLConnection site = url.openConnection();
                InputStream is = site.getInputStream();
                this.scanner = new Scanner(new BufferedInputStream(is), "UTF-8");
                this.scanner.useLocale(LOCALE);
            } catch (IOException var4) {
                throw new IllegalArgumentException("Could not open " + url, var4);
            }
        }
    }

    public In(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file argument is null");
        } else {
            try {
                FileInputStream fis = new FileInputStream(file);
                this.scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                this.scanner.useLocale(LOCALE);
            } catch (IOException var3) {
                throw new IllegalArgumentException("Could not open " + file, var3);
            }
        }
    }

    public In(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        } else if (name.length() == 0) {
            throw new IllegalArgumentException("argument is the empty string");
        } else {
            try {
                File file = new File(name);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    this.scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                    this.scanner.useLocale(LOCALE);
                } else {
                    URL url = this.getClass().getResource(name);
                    if (url == null) {
                        url = this.getClass().getClassLoader().getResource(name);
                    }

                    if (url == null) {
                        url = new URL(name);
                    }

                    URLConnection site = url.openConnection();
                    InputStream is = site.getInputStream();
                    this.scanner = new Scanner(new BufferedInputStream(is), "UTF-8");
                    this.scanner.useLocale(LOCALE);
                }
            } catch (IOException var6) {
                throw new IllegalArgumentException("Could not open " + name, var6);
            }
        }
    }

    public In(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner argument is null");
        } else {
            this.scanner = scanner;
        }
    }

    public boolean exists() {
        return this.scanner != null;
    }

    public boolean isEmpty() {
        return !this.scanner.hasNext();
    }

    public boolean hasNextLine() {
        return this.scanner.hasNextLine();
    }

    public boolean hasNextChar() {
        this.scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = this.scanner.hasNext();
        this.scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public String readLine() {
        String line;
        try {
            line = this.scanner.nextLine();
        } catch (NoSuchElementException var3) {
            line = null;
        }

        return line;
    }

    public char readChar() {
        this.scanner.useDelimiter(EMPTY_PATTERN);

        try {
            String ch = this.scanner.next();

            assert ch.length() == 1 : "Internal (Std)In.readChar() error! Please contact the authors.";

            this.scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException var2) {
            throw new NoSuchElementException("attempts to read a 'char' value from the input stream, but no more tokens are available");
        }
    }

    public String readAll() {
        if (!this.scanner.hasNextLine()) {
            return "";
        } else {
            String result = this.scanner.useDelimiter(EVERYTHING_PATTERN).next();
            this.scanner.useDelimiter(WHITESPACE_PATTERN);
            return result;
        }
    }

    public String readString() {
        try {
            return this.scanner.next();
        } catch (NoSuchElementException var2) {
            throw new NoSuchElementException("attempts to read a 'String' value from the input stream, but no more tokens are available");
        }
    }

    public int readInt() {
        try {
            return this.scanner.nextInt();
        } catch (InputMismatchException var3) {
            String token = this.scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from the input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var4) {
            throw new NoSuchElementException("attemps to read an 'int' value from the input stream, but no more tokens are available");
        }
    }

    public double readDouble() {
        try {
            return this.scanner.nextDouble();
        } catch (InputMismatchException var3) {
            String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from the input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var4) {
            throw new NoSuchElementException("attemps to read a 'double' value from the input stream, but no more tokens are available");
        }
    }

    public float readFloat() {
        try {
            return this.scanner.nextFloat();
        } catch (InputMismatchException var3) {
            String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from the input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var4) {
            throw new NoSuchElementException("attemps to read a 'float' value from the input stream, but no more tokens are available");
        }
    }

    public long readLong() {
        try {
            return this.scanner.nextLong();
        } catch (InputMismatchException var3) {
            String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from the input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var4) {
            throw new NoSuchElementException("attemps to read a 'long' value from the input stream, but no more tokens are available");
        }
    }

    public short readShort() {
        try {
            return this.scanner.nextShort();
        } catch (InputMismatchException var3) {
            String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from the input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var4) {
            throw new NoSuchElementException("attemps to read a 'short' value from the input stream, but no more tokens are available");
        }
    }

    public byte readByte() {
        try {
            return this.scanner.nextByte();
        } catch (InputMismatchException var3) {
            String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from the input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException var4) {
            throw new NoSuchElementException("attemps to read a 'byte' value from the input stream, but no more tokens are available");
        }
    }

    public boolean readBoolean() {
        try {
            String token = this.readString();
            if ("true".equalsIgnoreCase(token)) {
                return true;
            } else if ("false".equalsIgnoreCase(token)) {
                return false;
            } else if ("1".equals(token)) {
                return true;
            } else if ("0".equals(token)) {
                return false;
            } else {
                throw new InputMismatchException("attempts to read a 'boolean' value from the input stream, but the next token is \"" + token + "\"");
            }
        } catch (NoSuchElementException var2) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from the input stream, but no more tokens are available");
        }
    }

    public String[] readAllStrings() {
        String[] tokens = WHITESPACE_PATTERN.split(this.readAll());
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

    public String[] readAllLines() {
        ArrayList<String> lines = new ArrayList();

        while(this.hasNextLine()) {
            lines.add(this.readLine());
        }

        return (String[])lines.toArray(new String[0]);
    }

    public int[] readAllInts() {
        String[] fields = this.readAllStrings();
        int[] vals = new int[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            vals[i] = Integer.parseInt(fields[i]);
        }

        return vals;
    }

    public long[] readAllLongs() {
        String[] fields = this.readAllStrings();
        long[] vals = new long[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            vals[i] = Long.parseLong(fields[i]);
        }

        return vals;
    }

    public double[] readAllDoubles() {
        String[] fields = this.readAllStrings();
        double[] vals = new double[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            vals[i] = Double.parseDouble(fields[i]);
        }

        return vals;
    }

    public void close() {
        this.scanner.close();
    }

    /** @deprecated */
    @Deprecated
    public static int[] readInts(String filename) {
        return (new In(filename)).readAllInts();
    }

    /** @deprecated */
    @Deprecated
    public static double[] readDoubles(String filename) {
        return (new In(filename)).readAllDoubles();
    }

    /** @deprecated */
    @Deprecated
    public static String[] readStrings(String filename) {
        return (new In(filename)).readAllStrings();
    }

    /** @deprecated */
    @Deprecated
    public static int[] readInts() {
        return (new In()).readAllInts();
    }

    /** @deprecated */
    @Deprecated
    public static double[] readDoubles() {
        return (new In()).readAllDoubles();
    }

    /** @deprecated */
    @Deprecated
    public static String[] readStrings() {
        return (new In()).readAllStrings();
    }

    public static void main(String[] args) {
        String urlName = "https://introcs.cs.princeton.edu/java/stdlib/InTest.txt";
        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");

        In in;
        try {
            in = new In(urlName);
            System.out.println(in.readAll());
        } catch (IllegalArgumentException var4) {
            System.out.println(var4);
        }

        System.out.println();
        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");

        String s;
        try {
            in = new In(urlName);

            while(!in.isEmpty()) {
                s = in.readLine();
                System.out.println(s);
            }
        } catch (IllegalArgumentException var11) {
            System.out.println(var11);
        }

        System.out.println();
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");

        try {
            in = new In(urlName);

            while(!in.isEmpty()) {
                s = in.readString();
                System.out.println(s);
            }
        } catch (IllegalArgumentException var10) {
            System.out.println(var10);
        }

        System.out.println();
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");

        try {
            in = new In("./InTest.txt");

            while(!in.isEmpty()) {
                s = in.readLine();
                System.out.println(s);
            }
        } catch (IllegalArgumentException var9) {
            System.out.println(var9);
        }

        System.out.println();
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");

        try {
            in = new In("../stdlib/InTest.txt");

            while(!in.isEmpty()) {
                s = in.readLine();
                System.out.println(s);
            }
        } catch (IllegalArgumentException var8) {
            System.out.println(var8);
        }

        System.out.println();
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");

        try {
            in = new In("InTest.txt");

            while(!in.isEmpty()) {
                char c = in.readChar();
                System.out.print(c);
            }
        } catch (IllegalArgumentException var7) {
            System.out.println(var7);
        }

        System.out.println();
        System.out.println();
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");

        try {
            in = new In("/n/fs/introcs/www/java/stdlib/InTest.txt");

            while(!in.isEmpty()) {
                s = in.readLine();
                System.out.println(s);
            }
        } catch (IllegalArgumentException var6) {
            System.out.println(var6);
        }

        System.out.println();
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");

        try {
            in = new In("G:\\www\\introcs\\stdlib\\InTest.txt");

            while(!in.isEmpty()) {
                s = in.readLine();
                System.out.println(s);
            }

            System.out.println();
        } catch (IllegalArgumentException var5) {
            System.out.println(var5);
        }

        System.out.println();
    }

    static {
        LOCALE = Locale.US;
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        EMPTY_PATTERN = Pattern.compile("");
        EVERYTHING_PATTERN = Pattern.compile("\\A");
    }
}
