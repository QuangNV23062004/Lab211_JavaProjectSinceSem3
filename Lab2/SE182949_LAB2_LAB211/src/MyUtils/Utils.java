package MyUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Utils {

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
        Scanner sc = new Scanner(System.in);
        boolean ValidInput = false;
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

    public void SaveToFileTXT(String fname, Object data) {
        File f = new File(fname);
        if (f.exists()) {
            f.delete();
        } else {
            try {
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fw);
                if (data instanceof String) {
                    bw.write((String) data);
                } else if (data instanceof Integer) {
                    bw.write(Integer.toString((Integer) data));
                } else if (data instanceof ArrayList) {
                    for (Object item : (ArrayList) data) {
                        if (item instanceof String) {
                            bw.write((String) item);
                            bw.newLine();
                        }
                    }
                }
                bw.close();
                fw.close();
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    public Object LoadFromFileTXT(String fname, Class<?> dataType) {
        File f = new File(fname);
        if (f.length() == 0) {
            System.out.println("Empty files");
            return null;
        } else {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                if (dataType == String.class) {
                    return br.readLine();
                } else if (dataType == Integer.class) {
                    return Integer.parseInt(br.readLine());
                } else if (dataType == ArrayList.class) {
                    ArrayList<String> data = new ArrayList<>();
                    String line;
                    while ((line = br.readLine()) != null) {
                        data.add(line);
                    }
                    return data;
                }
            } catch (IOException i) {
                System.out.println(i);
            }
        }
        return null;
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

}
