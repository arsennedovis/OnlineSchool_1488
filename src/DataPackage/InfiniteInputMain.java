package DataPackage;

import java.util.*;


public class InfiniteInputMain {
    //створення масиву, з яким будемо працювати.
    public static String[][] dataArray = new String[0][2];
    public static Scanner scan = new Scanner(System.in);

    //цикл наповнення контейнеру.
    public static void main(String[] args) {
        String answer;
        System.out.println("type /help");
        do {
            answer = scan.nextLine();
            if (answer.equalsIgnoreCase("/new")) {
                inputOnePair();
                printArray(dataArray);

            } else if (answer.equalsIgnoreCase("/delete_index")) {
                if (dataArray.length > 0) {
                    deleteKeyByIndex(scanNumber("Input index for delete: "));
                    printArray(dataArray);
                } else {
                    System.out.println("Array is already empty!");
                }

            } else if (answer.equalsIgnoreCase("/delete_item")) {
                deleteKeyByName();
                printArray(dataArray);

            } else if (answer.equalsIgnoreCase("/help")) {
                System.out.println("""
                        /new - type new key.
                        /delete_index - type index for delete.
                        /delete_item - type name for delete.
                        /end - program exit.""");
            }

        } while (!answer.equalsIgnoreCase("/end"));
    }

    /**
     * @param outputText text only for user output.
     * @return String what we put in array. It can be anything like Key of Value.
     */
    public static String scanInput(String outputText) {

        String resultTemp;
        do {
            System.out.println(outputText);
            resultTemp = scan.nextLine();
            if (resultTemp.trim().isEmpty())
                System.out.println(StorageClass.errorEmptyField);
        }
        while (resultTemp.trim().isEmpty());
        return resultTemp.trim();

    }

    public static int scanNumber(String text) {
        do {
            System.out.println(text);
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else {
                scan.nextLine();
            }
        } while (!scan.hasNextInt());
        return -1;
    }

    /**
     * Read input and ignore anything except Y or N. ignore case.
     *
     * @return Bool true for YES and false for NO
     */
    public static Boolean answerYesOrNo() {
        System.out.println(StorageClass.warningKeyExist);

        String answer = scan.nextLine().toUpperCase(Locale.ROOT);
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println(StorageClass.yesOrNo);
            answer = scan.nextLine().toUpperCase(Locale.ROOT);
        }
        return answer.equals("Y");

    }

    /**
     * Search key in array. If catch null in a row return false, like not founded.
     *
     * @param keyItem key what we are looking for.
     * @return true if we found kay in data and false otherwise.
     */
    public static Boolean isThatKeyFoundInData(String keyItem) {
        for (String[] strings : dataArray) {
            if (keyItem.equalsIgnoreCase(strings[StorageClass.keyPosition])) {
                return true;
            } else if (strings[StorageClass.keyPosition] == null) {
                return false;
            }
        }
        return false;
    }

    /**
     * Search for a key index of argument.
     *
     * @param keyItem key what are we looking for.
     * @return index of founded key.
     */
    public static int keyFoundIndex(String keyItem) {
        for (int i = 0; i < dataArray.length; i++) {
            if (keyItem.equalsIgnoreCase(dataArray[i][StorageClass.keyPosition])) {
                return i;
            }
        }
        //Потрібна допомога. як не вертати нуль, але обривати цикл при находженні i?
        return 0;
    }


    /**
     * High function that launch inputKeyIfExist if Key exist and
     * inputKeyIfNotExist otherwise.
     */
    public static void inputOnePair() {
        String tmpKey = scanInput(StorageClass.keyIs);
        if (isThatKeyFoundInData(tmpKey)) {
            inputKeyIfExist(tmpKey);
        } else {
            inputKeyIfNotExist(tmpKey);
        }
    }

    /**
     * Logic for case when Key exist in array.
     */
    public static void inputKeyIfExist(String tmpKey) {
        if (answerYesOrNo()) {
            dataArray[keyFoundIndex(tmpKey)][StorageClass.valuePosition] = scanInput("New Value for "
                    + dataArray[keyFoundIndex(tmpKey)][StorageClass.keyPosition] + " is: ");
        } else {
            System.out.println(StorageClass.skipped);
        }

    }

    /**
     * Logic for case when Key is unique.
     */
    public static void inputKeyIfNotExist(String tmpKeyInsideFn) {
        dataArray = incrementArrayHeight(dataArray);
        dataArray[dataArray.length - 1][StorageClass.keyPosition] = tmpKeyInsideFn;
        dataArray[dataArray.length - 1][StorageClass.valuePosition] = scanInput("Value for "
                + dataArray[dataArray.length - 1][StorageClass.keyPosition] + " is: ");
    }

    public static String[][] incrementArrayHeight(String[][] array) {
        String[][] temp = array.clone();
        array = new String[array.length + 1][StorageClass.arrayWight];
        System.arraycopy(temp, 0, array, 0, temp.length);
        return array;
    }

    public static String[][] decrementArrayHeight(String[][] array) {
        String[][] temp = array.clone();
        array = new String[array.length - 1][StorageClass.arrayWight];
        System.arraycopy(temp, 0, array, 0, temp.length - 1);
        return array;
    }

    public static void deleteKeyByIndex(int index) {
        if (index >= 0 && index <= dataArray.length - 1) {
            for (int i = index; i == dataArray.length - 2; i++) {
                dataArray[i][StorageClass.keyPosition] = dataArray[i + 1][StorageClass.keyPosition];
                dataArray[i][StorageClass.valuePosition] = dataArray[i + 1][StorageClass.valuePosition];
            }
            dataArray = decrementArrayHeight(dataArray);
        } else {
            System.out.println("Index out of range!");
        }
    }

    public static void deleteKeyByName() {
        String tmpKey = scanInput(StorageClass.keyIs);
        if (isThatKeyFoundInData(tmpKey)) {
            deleteKeyByIndex(keyFoundIndex(tmpKey));
        } else {
            System.out.println("Key does not exist.");
        }
    }

    public static void printArray(String[][] arr){
        for (int i=0; i<arr.length;i++) {
            System.out.print("index "+ i +" || ");
            for (int j = 0; j<arr[i].length; j++)
                if(j==StorageClass.keyPosition){
                    System.out.print("Key: " + arr[i][j] + " | Values: ");
                } else {
                    System.out.print(arr[i][j] + ", ");
                }
            System.out.println();
        }
    }

    public static String[] splitValues(int key) {
        String values = scanInput(StorageClass.valueInputText);
        String[] splitValues = values.split(" ");
        if ((splitValues.length  + 1) > StorageClass.arrayWight) {
            StorageClass.arrayWight = splitValues.length + 1;
        }
        return splitValues;
    }

}

