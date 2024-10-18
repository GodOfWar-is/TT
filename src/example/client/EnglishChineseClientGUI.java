package example.client;

import cn.com.webxml.EnglishChinese;
import cn.com.webxml.EnglishChineseSoap;
import cn.com.webxml.ArrayOfString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnglishChineseClientGUI extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;
    private JButton translateButton;
    private JButton exampleButton; // 增加获取例句的按钮

    public EnglishChineseClientGUI() {
        setTitle("English-Chinese Translator");
        setSize(500, 400); // 增大窗口尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 输出区域
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(Color.WHITE); // 设置背景为白色
        outputArea.setBorder(BorderFactory.createTitledBorder("Translation Output")); // 添加边框标题

        // 输入框
        inputField = new JTextField();
        inputField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createTitledBorder("Enter Text")); // 添加边框标题

        // 按钮
        translateButton = new JButton("Translate");
        translateButton.setFont(new Font("Arial", Font.BOLD, 16)); // 设置按钮字体
        translateButton.setPreferredSize(new Dimension(120, 40)); // 设置按钮大小

        exampleButton = new JButton("Get Example");
        exampleButton.setFont(new Font("Arial", Font.BOLD, 16)); // 设置按钮字体
        exampleButton.setPreferredSize(new Dimension(120, 40)); // 设置按钮大小

        // 创建面板以存放输入框和按钮
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER); // 输入框占据中心
        inputPanel.add(translateButton, BorderLayout.EAST); // 翻译按钮在输入框右侧

        JPanel examplePanel = new JPanel();
        examplePanel.setLayout(new FlowLayout());
        examplePanel.add(exampleButton); // 例句按钮

        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH); // 输入框和翻译按钮在顶部
        add(examplePanel, BorderLayout.SOUTH); // 例句按钮在底部

        // 按钮事件处理
        translateButton.addActionListener(new TranslateAction());
        exampleButton.addActionListener(new ExampleAction()); // 添加例句按钮的事件处理

        setVisible(true);
        setLocationRelativeTo(null); // 窗口居中显示
    }

    private class TranslateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToTranslate = inputField.getText().trim();
            if (!wordToTranslate.isEmpty()) {
                try {
                    EnglishChinese service = new EnglishChinese();
                    EnglishChineseSoap englishChineseSoap = service.getEnglishChineseSoap();

                    ArrayOfString translatedResult = englishChineseSoap.translatorString(wordToTranslate);
                    StringBuilder result = new StringBuilder("Translation:\n");
                    for (String translation : translatedResult.getString()) {
                        result.append(translation).append("\n"); // 直接添加翻译结果
                    }

                    outputArea.setText(result.toString());

                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        }
    }

    private class ExampleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToTranslate = inputField.getText().trim();
            if (!wordToTranslate.isEmpty()) {
                try {
                    EnglishChinese service = new EnglishChinese();
                    EnglishChineseSoap englishChineseSoap = service.getEnglishChineseSoap();

                    ArrayOfString exampleResult = englishChineseSoap.translatorSentenceString(wordToTranslate);
                    StringBuilder result = new StringBuilder("Example Sentences:\n");
                    for (String example : exampleResult.getString()) {
                        result.append(example).append("\n"); // 添加例句结果
                    }

                    outputArea.setText(result.toString());

                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnglishChineseClientGUI::new);
    }
}
