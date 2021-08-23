package DataPackage;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class InfiniteInputMain {

    public static String[][] dualDataArray = new String[10][2];
    public static int keyFoundedIndex;


    public static void main(String[] args) {
        while (true) {
            inputOnePair();
            System.out.println(Arrays.deepToString(dualDataArray));
        }
    }

            public static String scanKeyOrValue (String outputText){
                /* Функція приймає аргумент для виводу в консоль (що ми запитуємо в користувача)
                 * створює сканер, потім перевіряє обрізаний на пробіли ввід на наявність символів
                 * якщо символи присутні, то *виводить в консоль* та повертає результат строки.
                 * на вході в функцію змінюємо isErrorInScanFn змінюється на помилку до моменту її
                 * "вирішення" тобто повернення строки, при поверненні вертає false.
                 */
                Scanner scan = new Scanner(System.in);
                String ResultTemp = null;
                boolean isErrorInScanFn = true;

                while (isErrorInScanFn) {
                    System.out.println(outputText);
                    ResultTemp = scan.nextLine();
                    if (ResultTemp.trim().isEmpty()) {
                        System.out.println("Zero chars in your input!");
                    } else {
                        isErrorInScanFn = false;
                    }
                }
                return ResultTemp.trim();
            }

            public static Boolean answerShortYesOrNo() {
                /* Зчитує введення, та ігнорує все окрім Y та N.
                 * ігнорує Case. вихід з циклу відбувається
                 * тільки давши відповідь.
                 */
                Scanner scan = new Scanner(System.in);
                String answer = scan.nextLine().toUpperCase(Locale.ROOT);
                while (!answer.equals("Y") && !answer.equals("N")) {
                    System.out.println("y/n?");
                    answer = scan.nextLine().toUpperCase(Locale.ROOT);
                }
                return answer.equals("Y");

            }

            public static Boolean isThatKeyFoundInData (String keyItem){
                /* Функція для пошуку введеного аргументу в масиві по першому індексу.
                 * Використовує глобальну змінну класу для запису позиції.
                 * Ще не вмію повертати два return з 1 ф-ції, тому реалізовано
                 * через глобальну змінну.
                 */
                for (int i = 0; i < dualDataArray.length; i++) {
                    if (keyItem.equalsIgnoreCase(dualDataArray[i][0])) {
                        keyFoundedIndex = i;
                        return true;
                    }
                }
                return false;
            }

            public static int searchFirstEmptyPlaceInArray() {
                /* Пошук першого вільного місця для запису нового ключа.
                 * Якщо масив заповнений, то повертає індекс першого елементу.
                 * можливо буде перероблена в процесі створення другого масиву, або
                 * буде вбудований тригер для копіювання масиву з більшим контейнером.
                 */
                for (int i = 0; i < dualDataArray.length; i++) {
                    if (dualDataArray[i][0] == null) {
                        return i;
                    }
                }
                return 0;
            }

            public static void inputOnePair() {
        /*
        Спочатку зчитується введення потенційного ключа з клавіатури.
        Виконується перевірка на наявність такого в масиві, якщо існує,
        то пропонується переписати ключ для такого масиву.
        в іншому випадку шукає вільне місце та записує туди пару ключ-значення.
         */
                String tmpKeyInsideFn = scanKeyOrValue("Key is: ");
                if (isThatKeyFoundInData(tmpKeyInsideFn)) {
                    System.out.println("This Key already exist, Do you want to change Value? (y/n)");
                    if (answerShortYesOrNo()) {
                        dualDataArray[keyFoundedIndex][1] = scanKeyOrValue("New Value for " + dualDataArray[keyFoundedIndex][0] + " is: ");
                    } else {
                        System.out.println("skipped");
                    }
                } else {
                    int firstIndexPosition = searchFirstEmptyPlaceInArray();
                    dualDataArray[firstIndexPosition][0] = tmpKeyInsideFn;
                    dualDataArray[firstIndexPosition][1] = scanKeyOrValue("Value for " + dualDataArray[firstIndexPosition][0] + " is: ");
                }
            }

}

