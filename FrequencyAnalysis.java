/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/


import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalysis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть текст для аналізу:");
        String text = scanner.nextLine();
        scanner.close();

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

