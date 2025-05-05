import java.io.*;
import java.util.Scanner;

class SinusCalculator implements Serializable {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;

    public SinusCalculator(double x) {
        this.x = x;
        this.y = calculateY(x);
    }
    public double calculateY(double x) {
        return x - Math.sin(x);
    }
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("Состояние объекта сохранено в файл: " + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static SinusCalculator uploadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            SinusCalculator calc = (SinusCalculator) ois.readObject();
            System.out.println("Состояние объекта восстановлено из файла: " + filename);
            return calc;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    @Override
    public String toString() {
        return "Состояние: " + "x=" + x + ", y=" + y;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите значение x: ");
        double inputX = sc.nextDouble();

        SinusCalculator calc = new SinusCalculator(inputX);
        System.out.println(calc);

        while (true) {
            System.out.print("Введите команду save, upload, exit: ");
            String cmd = sc.next();

            if ("save".equalsIgnoreCase(cmd)) {
                calc.saveToFile("state.txt");
            } else if ("upload".equalsIgnoreCase(cmd)) {
                SinusCalculator loadedCalc = uploadFromFile("state.txt");
                if (loadedCalc != null) {
                    System.out.println(loadedCalc);
                    calc = loadedCalc;
                }
            } else if ("exit".equalsIgnoreCase(cmd)) {
                break;
            } else {
                System.out.println("Неверная команда.");
            }
        }
        sc.close();
    }
}