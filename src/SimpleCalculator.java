import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator {
    private final JTextField display; // текстовое поле для вывода данных
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    public SimpleCalculator() {
        // Создание окна
        // Создание элементов интерфейса
        JFrame frame = new JFrame("Калькулятор");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout(10, 10)); // Добавлены отступы между компонентами

        // Создание текстового поля для вывода
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        // Создание панели для кнопок
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10)); // Добавлены отступы между кнопками

        // Кнопки чисел (0-9)
        // массив кнопок для чисел.
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            numberButtons[i].addActionListener(new ButtonClickListener());
            numberButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Отступы внутри кнопок
            panel.add(numberButtons[i]);
        }

        // Кнопки операций (+, -, *, /)
        String[] operators = {"+", "-", "*", "/"};
        // массив кнопок для операций
        JButton[] operatorButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            operatorButtons[i] = new JButton(operators[i]);
            operatorButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            operatorButtons[i].addActionListener(new ButtonClickListener());
            operatorButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Отступы внутри кнопок
            panel.add(operatorButtons[i]);
        }

        // Кнопка "=" для вычисления
        JButton equalButton = new JButton("=");
        equalButton.setFont(new Font("Arial", Font.PLAIN, 20));
        equalButton.addActionListener(new EqualButtonListener());
        equalButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Отступы внутри кнопки
        panel.add(equalButton);

        // Кнопка "C" для очистки
        // Кнопки "=" и "C" для вычисления и очистки
        JButton clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 20));
        clearButton.addActionListener(new ClearButtonListener());
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Отступы внутри кнопки
        panel.add(clearButton);

        // Добавление панели с кнопками в окно, с отступами от бордюра
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Отступы между панелью и бордюром окна

        // Добавление панели в основное окно
        frame.add(panel, BorderLayout.CENTER);

        // Отображение окна
        frame.setVisible(true);
    }

    // Обработчик для кнопок чисел и операторов
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // Если нажата цифра, добавляем её в текущее введенное значение
            if ("0123456789".contains(command)) {
                currentInput += command;
                display.setText(currentInput);
            }

            // Если нажата операция (+, -, *, /), сохраняем первое число и операцию
            if ("+-*/".contains(command)) {
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    currentInput = "";
                    operator = command;
                }
            }
        }
    }

    // Обработчик для кнопки "="
    private class EqualButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!currentInput.isEmpty()) {
                double secondOperand = Double.parseDouble(currentInput);
                double result = 0;

                // Выполнение операции в зависимости от выбранного оператора
                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            display.setText("Ошибка!");
                            return;
                        }
                        break;
                }

                // Отображение результата
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result); // для дальнейших вычислений
                operator = "";
            }
        }
    }

    // Обработчик для кнопки "C"
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentInput = "";
            operator = "";
            firstOperand = 0;
            display.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }
}