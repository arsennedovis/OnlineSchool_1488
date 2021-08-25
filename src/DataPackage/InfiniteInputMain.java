package DataPackage;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class InfiniteInputMain {
    public static String[][] dualDataArray = new String[10][2];

    public static void main(String[] args) {
        while (true) {
            inputOnePair();
            System.out.println(Arrays.deepToString(dualDataArray));
        }
    }

    /**
     * Функція приймає аргумент для виводу в консоль (що ми запитуємо в користувача)
     * створює сканер, потім перевіряє обрізаний на пробіли ввід на наявність символів
     * якщо символи присутні, то *виводить в консоль* та повертає результат строки.
     * на вході в функцію змінюємо isErrorInScanFn змінюється на помилку до моменту
     * повернення строки, при поверненні вертає false для виходу з циклу.
     */
    public static String scanInput(String outputText) {

        Scanner scan = new Scanner(System.in);
        String ResultTemp = null;
        boolean isErrorInScanFn = true;

        while (isErrorInScanFn) {
            System.out.println(outputText);
            ResultTemp = scan.nextLine();
            if (ResultTemp.trim().isEmpty()) {
                System.out.println(StorageClass.errorEmptyField);
            } else {
                isErrorInScanFn = false;
            }
        }
        return ResultTemp.trim();
    }

    /**
     * Зчитує введення, та ігнорує все окрім Y та N.
     * ігнорує Case. вихід з циклу відбувається
     * тільки давши відповідь.
     */
    public static Boolean answerYesOrNo() {
        System.out.println(StorageClass.warningKeyExist);

        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine().toUpperCase(Locale.ROOT);
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println(StorageClass.yesOrNo);
            answer = scan.nextLine().toUpperCase(Locale.ROOT);
        }
        return answer.equals("Y");

    }

    /**
     * Функція для пошуку введеного аргументу в масиві по першому індексу.
     */
    public static Boolean isThatKeyFoundInData(String keyItem) {
        for (String[] strings : dualDataArray) {
            if (keyItem.equalsIgnoreCase(strings[StorageClass.keyPositionInArray])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Пошук індексу ключового слова в списку dualDataArray
     */
    public static int keyFoundIndex(String keyItem) {
        for (int i = 0; i < dualDataArray.length; i++) {
            if (keyItem.equalsIgnoreCase(dualDataArray[i][StorageClass.keyPositionInArray])) {
                return i;
            }
        }
        //Потрібна допомога. як не вертати нуль, але обривати цикл при находженні i?
        return 0;
    }

    /**
     * Пошук першого вільного місця для запису нового ключа.
     * Якщо масив заповнений, то повертає індекс першого елементу.
     * можливо буде перероблена в процесі створення другого масиву, або
     * буде вбудований тригер для копіювання масиву з більшим контейнером.
     */
    public static int searchFirstEmptyKeyPlaceInArray() {
        for (int i = 0; i < dualDataArray.length; i++) {
            if (dualDataArray[i][StorageClass.keyPositionInArray] == null) {
                return i;
            }
        }
        //Виклик функції на створення нового списку.
        return 0;
    }

    /**
     * Спочатку зчитується введення потенційного ключа з клавіатури.
     * Виконується перевірка на наявність такого в масиві, якщо існує,
     * то пропонується переписати ключ для такого масиву.
     * в іншому випадку шукає вільне місце та записує туди пару ключ-значення.
     */
    public static void inputOnePair() {
        String tmpKeyInsideFn = scanInput(StorageClass.keyIs);
        if (isThatKeyFoundInData(tmpKeyInsideFn)) {
            inputKeyIfExist(tmpKeyInsideFn);
        } else {
            inputKeyIfNotExist(tmpKeyInsideFn);
        }
    }

    /**
     * Логіка для випадку, коли введений ключ знаходиться в масиві.
     */
    public static void inputKeyIfExist(String tmpKeyInsideFn) {
        if (answerYesOrNo()) {
            dualDataArray[keyFoundIndex(tmpKeyInsideFn)][StorageClass.valuePositionInArray] = scanInput("New Value for "
                    + dualDataArray[keyFoundIndex(tmpKeyInsideFn)][StorageClass.keyPositionInArray] + " is: ");
        } else {
            System.out.println(StorageClass.skipped);
        }

    }

    /**
     * Логіка для випадку, коли введений ключ являється унікальним.
     */
    public static void inputKeyIfNotExist(String tmpKeyInsideFn) {
        int emptyKey = searchFirstEmptyKeyPlaceInArray();
        dualDataArray[emptyKey][StorageClass.keyPositionInArray] = tmpKeyInsideFn;
        dualDataArray[emptyKey][StorageClass.valuePositionInArray] = scanInput("Value for "
                + dualDataArray[emptyKey][StorageClass.keyPositionInArray] + " is: ");
    }

}

