import java.util.Objects;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner vvod = new Scanner(System.in);
        String input = vvod.nextLine();
        String output = calc(input);
        System.out.println(output);
    }

    public static int checkLang(String number){
        // массив используемых римских символов
        String[] rimNumbers = new String[5];
        rimNumbers[0] = "I";
        rimNumbers[1] = "V";
        rimNumbers[2] = "X";
        rimNumbers[3] = "L";
        rimNumbers[4] = "C";
        //
        // масссив арабских цифр
        String[] arabNumbers = new String[10];
        for (int i = 0; i < arabNumbers.length; i++) {
            arabNumbers[i] = Integer.toString(i);
        }
        //
        int type = 0; // тип числа
        int counterArab = 0; // счетчик для арбских
        int counterRim = 0; // счетчик для римских
        for (int i = 0; i <number.length(); i++) {
            for (int j = 0; j < 10; j++) {
                if (arabNumbers[j].charAt(0) == number.charAt(i)) {
                    counterArab = counterArab + 1;
                }
            }
        }
        // Проверка на римские 1 числа
        for (int i = 0; i < number.length(); i++) {
            for (int j = 0; j < 5; j++) {
                if (rimNumbers[j].charAt(0) == number.charAt(i)) {
                    counterRim = counterRim + 1;
                }
            }
        }
        // Тип числа 1-араб 2-рим
        if (counterArab == number.length() || counterRim == number.length()) {
            if (counterArab == number.length()) {
                type = 1;
            }
            if (counterRim == number.length()) {
                type = 2;
            }
        } else {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Число не соответствует формату");
                System.exit(0);
            }
        }
        return type;
    }
    public static int fromArabToNum(String number){
        int num = 0;
        for (int i = 0; i < number.length(); i++) {
            char sym = number.charAt(i);
            num = (sym- '0') + (num * 10);
        }
        return num;
    }
    public static int fromRimToNum(String number){
        Counter counter1 = new Counter(); // объект, нужный лишь для того, чтобы применять методы
        // римское 1
        int num = 0;
        int lastNum = number.length()-1;
        char[] symvoli = number.toCharArray(); //X I V
        int current = 0;
        num = counter1.convertRimSymbol(symvoli[lastNum]);
        for(int i = lastNum-1; i>=0; i--){
            current = counter1.convertRimSymbol(symvoli[i]);
            if(current < counter1.convertRimSymbol(symvoli[i+1])){
                num = num - current;
            }else{
                num = num + current;
            }
        }
        return num;
    }
    public static String calc(String input){
        Counter counter1 = new Counter(); // объект, нужный лишь для того, чтобы применять методы
        String number1 = ""; // число 1 до обработки
        String number2 = ""; // число 2 до обработки
        char symbol = '0'; // символ выражения
        int num1 = 0; // число 1 после обработки
        int num2 = 0; // число 2 после обработки
        int result = 0; // итог вычислений до перевода
        String output = ""; // выводимый результат
        // ввод + проверка на пустой и на кол-во элементов разделенных пробелами
        if (input.isEmpty() == true) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Вы ничего не ввели");
            }
        } else {
            // разделение на элементы пробелами
            String[] elements = input.split(" ");
            if (elements.length != 3) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Вы ввели неверное количество чисел или знаков");
                    System.exit(0);
                }
            } else{
                // выделение чисел и знака
                number1 = elements[0];
                symbol = elements[1].charAt(0);
                number2 = elements[2];
            }

        }
        // выход - предположительно число1, знак, число2
        // Проверка языка чисел
        int firstType = checkLang(number1); // тип 1 числа
        int secondType = checkLang(number2); // тип 2 числа
        // проверка одинаковости языка чисел
        if (firstType != secondType) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Числа разных алфавитов");
                System.exit(0);
            }
        } else{
            // перевод в int + в арабские, если надо
            if (firstType == 1){
                num1 = fromArabToNum(number1);
            } else{
                num1 = fromRimToNum(number1);
            }
            if (num1 < 1 || num1 > 10) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Введенное число 1 не соответствует требованиям");
                    System.exit(0);
                }
            }
            if(secondType == 1){
                num2 = fromArabToNum(number2);
            } else{
                num2 = fromRimToNum(number2);
            }
            if (num2 < 1 || num2 > 10) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Введенное число 2 не соответствует требованиям");
                    System.exit(0);
                }
            }
        }
        // переработка чисел и знака в результат
        result = counter1.performOperation(num1, num2, symbol);
        // перевод результат
        if(firstType == 1){
            output = Integer.toString(result);
        } else{
            if(result < 1){
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Результат отрицательный или 0 и не может быть отображен в римских числах");
                    System.exit(0);
                }
            }else {
                output = counter1.convertArabSymbol(result);
            }
        }
        //возврат результата
        return output;
    }

}
    class Counter {

        public int performOperation(int num1, int num2, char symbol) {
            int result = 0;
            switch (symbol) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
            }
            return result;
        }
        public String convertArabSymbol(int num){
            String result = "";
            int counterLast = num%10;
            int counterTens = num/10;
                if(counterTens == 0){
                    if(counterLast == 9){
                        result = result + "IX";
                    } else if(counterLast < 9 && counterLast >4){
                        result = result + "V";
                        for(int j = 6; j<=counterLast; j++){
                            result = result + "I";
                        }
                    }else if(counterLast ==4){
                        result = result + "IV";
                    } else if(counterLast < 4 && counterLast > 0){
                        for(int j = 1; j <= counterLast; j++){
                            result = result + "I";
                        }
                    }
                } else if(counterTens >0 && counterTens<4){
                    for(int i = 1; i <= counterTens; i++){
                        result = result + "X";
                    }
                    if(counterLast == 9){
                        result = result + "IX";
                    } else if(counterLast < 9 && counterLast >4){
                        result = result + "V";
                        for(int j = 6; j<=counterLast; j++){
                            result = result + "I";
                        }
                    }else if(counterLast ==4){
                        result = result + "IV";
                    } else if(counterLast < 4 && counterLast > 0){
                        for(int j = 1; j <= counterLast; j++){
                            result = result + "I";
                        }
                    }
                } else if(counterTens ==4){
                    result = result + "XL";
                    if(counterLast == 9){
                        result = result + "IX";
                    } else if(counterLast < 9 && counterLast >4){
                        result = result + "V";
                        for(int j = 6; j<=counterLast; j++){
                            result = result + "I";
                        }
                    }else if(counterLast ==4){
                        result = result + "IV";
                    } else if(counterLast < 4 && counterLast > 0){
                        for(int j = 1; j <= counterLast; j++){
                            result = result + "I";
                        }
                    }
                } else if(counterTens > 4 && counterTens < 9){
                    result = result + "L";
                    for(int i = 6; i <= counterTens; i++){
                        result = result + "X";
                    }
                    if(counterLast == 9){
                        result = result + "IX";
                    } else if(counterLast < 9 && counterLast >4){
                        result = result + "V";
                        for(int j = 6; j<=counterLast; j++){
                            result = result + "I";
                        }
                    }else if(counterLast ==4){
                        result = result + "IV";
                    } else if(counterLast < 4 && counterLast > 0){
                        for(int j = 1; j <= counterLast; j++){
                            result = result + "I";
                        }
                    }
                } else if(counterTens == 9){
                    result = result + "XC";
                    if(counterLast == 9){
                        result = result + "IX";
                    } else if(counterLast < 9 && counterLast >4){
                        result = result + "V";
                        for(int j = 6; j<=counterLast; j++){
                            result = result + "I";
                        }
                    }else if(counterLast ==4){
                        result = result + "IV";
                    } else if(counterLast < 4 && counterLast > 0){
                        for(int j = 1; j <= counterLast; j++){
                            result = result + "I";
                        }
                    }
                } else if(counterTens == 10){
                    result = result + "C";
                    if(counterLast == 9){
                        result = result + "IX";
                    } else if(counterLast < 9 && counterLast >4){
                        result = result + "V";
                        for(int j = 6; j<=counterLast; j++){
                            result = result + "I";
                        }
                    }else if(counterLast ==4){
                        result = result + "IV";
                    } else if(counterLast < 4 && counterLast > 0){
                        for(int j = 1; j <= counterLast; j++){
                            result = result + "I";
                        }
                    }
                }
            return result;
        }
        public int convertRimSymbol(char symbol) {
            int result = 0;
            switch (symbol) {
                case 'I':
                    result = 1;
                    break;
                case 'V':
                    result = 5;
                    break;
                case 'X':
                    result = 10;
                    break;
                case 'L':
                    result = 50;
                    break;
                case 'C':
                    result = 100;
                    break;
            }
            return result;
        }
    }
