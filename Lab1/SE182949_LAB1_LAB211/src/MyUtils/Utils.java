package MyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils  {

    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStringCanEmpty(String welcome, String oldData) {
        String result = "";
        Scanner sc = new Scanner(System.in);
        System.out.print(welcome);
        result = sc.nextLine();
        if (result.isEmpty()) {
            result = oldData;
        }
        return result;
    }

    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStringregCanEmpty(String welcome, String pattern, String oldData, String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                result = oldData;
                check = false;
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static int getInt(String welcome, int min) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static float getloat(String welcome, int min) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public double ValueInRange(String welcome, int min, int max, String range) {
        double value = 0;
        boolean ValidInput = false;
        Scanner sc = new Scanner(System.in);
        while (!ValidInput) {
            try {
                System.out.println(welcome);
                value = Double.parseDouble(sc.nextLine());
                if (value < min || value > max) {
                    System.out.println(range);
                } else {
                    ValidInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
            }
        }
        return value;
    }   
        public void saveToFileDAT(String fname, ArrayList<?> data) {
        File f = new File(fname);
        try {
            FileOutputStream fos = new FileOutputStream(fname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            System.out.println(ioe + fname);
        }
    }

    public ArrayList<?> loadFromFileDAT(String fname) {
        ArrayList<?> data = null;
        try {
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (ArrayList<?>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            System.out.println(ioe + fname);
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            System.out.println(c + fname);
        }
        return data;
    }
}
