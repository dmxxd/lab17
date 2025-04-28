import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double x = 0;
        double y = 0;
        String filename = "calculator_state.txt";

        while (true) {
            System.out.println("Введите число, 'save' для сохранения, 'upload' для загрузки или 'exit' для выхода:");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы...");
                break;
            } else if (input.equalsIgnoreCase("save")) {
                try (PrintWriter wr = new PrintWriter(new FileWriter(filename))) {
                    wr.println(x);
                    wr.println(y);
                    System.out.println("Состояние сохранено в файл " + filename);
                } catch (IOException e) {
                    System.out.println("Ошибка при сохранении: " + e.getMessage());
                }
            } else if (input.equalsIgnoreCase("upload")) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                    String xLine = reader.readLine();
                    String yLine = reader.readLine();
                    if (xLine != null && yLine != null) {
                        x = Double.parseDouble(xLine);
                        y = Double.parseDouble(yLine);
                        System.out.println("Состояние загружено из файла " + filename);
                        System.out.println("Исходное значение x = " + x);
                        System.out.println("Результат y = " + y);
                    } else {
                        System.out.println("Файл пустой или поврежден.");
                    }
                } catch (IOException | NumberFormatException ex) {
                    System.out.println("Ошибка при загрузке: " + ex.getMessage());
                }
            } else {
                try {
                    x = Double.parseDouble(input);
                    y = x - Math.sin(x);
                    System.out.println("Исходное значение x = " + x);
                    System.out.println("Результат y = " + y);
                } catch (NumberFormatException ex) {
                    System.out.println("Некорректный ввод. Пожалуйста, введите число или команду.");
                }
            }
        }
        sc.close();
    }
}