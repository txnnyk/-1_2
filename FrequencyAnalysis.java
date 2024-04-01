import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;

public class FrequencyAnalysis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть опцію: 1 - ввести текст з консолі, 2 - зчитати текст з файлу");
        int option = scanner.nextInt();
        scanner.nextLine(); 
        String text = "";

        switch (option) {
            case 1:
                System.out.println("Введіть текст для аналізу:");
                text = scanner.nextLine();
                break;
            case 2:
                System.out.println("Введіть шлях до файлу для аналізу:");
                String filePath = scanner.nextLine();
                try {
                    File file = new File(filePath);
                    scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        text += scanner.nextLine();
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Файл не знайдено");
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Невірний вибір. Будь ласка, виберіть 1 або 2.");
                System.exit(0);
        }

        text = text.replaceAll("[^а-яА-Я]", "").toLowerCase();

        Map<Character, Integer> charFrequencies = calculateCharFrequencies(text);

        Map<String, Integer> bigramFrequencies = calculateBigramFrequencies(text);

        System.out.println("Частоти символів:");
        for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + (double) entry.getValue() / text.length());
        }

        System.out.println("Частоти біграм:");
        for (Map.Entry<String, Integer> entry : bigramFrequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + (double) entry.getValue() / (text.length() - 1));
        }
    }

    private static Map<Character, Integer> calculateCharFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    private static Map<String, Integer> calculateBigramFrequencies(String text) {
        Map<String, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < text.length() - 1; i++) {
            String bigram = text.substring(i, i + 2);
            frequencies.put(bigram, frequencies.getOrDefault(bigram, 0) + 1);
        }
        return frequencies;
    }
}

