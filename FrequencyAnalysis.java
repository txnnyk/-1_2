import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FrequencyAnalysisGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Frequency Analysis");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JTextArea inputArea = new JTextArea(10, 40);
            JTextArea outputArea = new JTextArea(10, 40);
            outputArea.setEditable(false);

            JButton button = new JButton("Choose File");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        performFrequencyAnalysis(selectedFile, outputArea);
                    }
                }
            });

            JButton button2 = new JButton("Analyze Text");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = inputArea.getText();
                    performFrequencyAnalysis(text, outputArea);
                }
            });

            JPanel panel = new JPanel();
            panel.add(button);
            panel.add(button2);
            panel.add(new JScrollPane(inputArea));
            panel.add(new JScrollPane(outputArea));
            frame.getContentPane().add(panel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    private static void performFrequencyAnalysis(File file, JTextArea outputArea) {
        String text = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                text += scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено");
            e.printStackTrace();
        }

        performFrequencyAnalysis(text, outputArea);
    }

    private static void performFrequencyAnalysis(String text, JTextArea outputArea) {
        text = text.replaceAll("[^а-яА-Я]", "").toLowerCase();

        Map<Character, Integer> charFrequencies = calculateCharFrequencies(text);

        Map<String, Integer> bigramFrequencies = calculateBigramFrequencies(text);

        StringBuilder output = new StringBuilder();
        output.append("Частоти символів:\n");
        for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
            output.append(entry.getKey() + ": " + (double) entry.getValue() / text.length() + "\n");
        }

        output.append("Частоти біграм:\n");
        for (Map.Entry<String, Integer> entry : bigramFrequencies.entrySet()) {
            output.append(entry.getKey() + ": " + (double) entry.getValue() / (text.length() - 1) + "\n");
        }

        outputArea.setText(output.toString());
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
