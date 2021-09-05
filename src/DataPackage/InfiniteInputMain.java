package DataPackage;

import java.util.*;


public class InfiniteInputMain {
    public static String[][] dataArray = new String[0][0];
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String answer;
        System.out.println("type /help");
        do {
            answer = scan.nextLine();
            if (answer.equalsIgnoreCase("/new")||answer.equalsIgnoreCase("/n")) {
                inputOnePair(dataArray);
                printArray(dataArray);

            } else if (answer.equalsIgnoreCase("/delete_index")||answer.equalsIgnoreCase("/din")) {
                if (dataArray.length > 0) {
                    dataArray = deleteKeyByIndex(scanNumber(StorageClass.indexForDeleteText), dataArray);
                    printArray(dataArray);
                } else {
                    System.out.println(StorageClass.arrayEmptyText);
                }

            } else if (answer.equalsIgnoreCase("/delete_item")||answer.equalsIgnoreCase("/dit")) {
                dataArray = deleteKeyByName(dataArray);
                printArray(dataArray);

            } else if (answer.equalsIgnoreCase("/help")||answer.equalsIgnoreCase("/h")) {
                System.out.println(StorageClass.helpText);
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

    /**
     * Input, ignore all inputs except integer.
     * @param text - any text for display.
     * @return int number
     */
    public static int scanNumber(String text) {
        int i;
        System.out.println(text);
        while (!scan.hasNextInt()){
            System.out.println(StorageClass.notANumberText);
            scan.nextLine();
        }
        i = scan.nextInt();
        return i;
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
    public static Boolean isThatKeyFoundInData(String keyItem, String[][] array) {
        for (String[] strings : array) {
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
     * High function that launch fillSlotByValues with true condition if Key exist and
     * with false otherwise.
     */
    public static void inputOnePair(String[][] array) {
        String tmpKey = scanInput(StorageClass.keyIs);
        if (isThatKeyFoundInData(tmpKey, array)) {
            if (answerYesOrNo()) {
                dataArray = fillSlotByValues(scanInput("Values is: ").split(","), tmpKey, dataArray, true);

            } else {
                System.out.println(StorageClass.skippedText);
            }
        } else {
            dataArray = fillSlotByValues(scanInput(StorageClass.valueInputText).split(","), tmpKey, dataArray, false);
        }
    }

    public static String[][] decrementArrayHeight(String[][] array) {
        String[][] temp = array.clone();
        array = new String[array.length - 1][];
        System.arraycopy(temp, 0, array, 0, temp.length - 1);
        return array;
    }

    public static String[][] incrementArrayHeight(String[][] getArray) {
        String[][] arrayClone = getArray.clone();
        getArray = new String[getArray.length + 1][];
        System.arraycopy(arrayClone, 0, getArray, 0, getArray.length - 1);
        return getArray;
    }

    /**
     * fn for fill slot by values.
     * @param valuesArray array with new values String.
     * @param keyName Slot for write new values here.
     * @param getArray array for working with.
     * @param isExist condition to determinate where write new values in exist key or new.
     * @return return main data array.
     */
    public static String[][] fillSlotByValues(String[] valuesArray, String keyName, String[][] getArray, boolean isExist) {
        String[] valuesArrayFiltered = Arrays.stream(valuesArray).filter(x -> !x.trim().isEmpty()).toArray(String[]::new);
        if (valuesArrayFiltered.length == 0){
            System.out.println(StorageClass.skippedText);
            return getArray;
        }
        String[] arrayKeyAndValues = new String[1 + valuesArrayFiltered.length];
        arrayKeyAndValues[StorageClass.keyPosition] = keyName;
        for (int i = 1; i < arrayKeyAndValues.length; i++) {
            arrayKeyAndValues[i] = valuesArrayFiltered[i - 1].trim();
        }
        if (!isExist) {
            getArray = incrementArrayHeight(getArray);
            getArray[getArray.length - 1] = arrayKeyAndValues;
        } else {
            getArray[keyFoundIndex(keyName)] = arrayKeyAndValues;
        }
        return getArray;
    }

    public static String[][] deleteKeyByIndex(int index, String[][] array) {
        String[][] res;
        if (index >= 0 && index <= array.length - 1) {
            for (int i = index; i == array.length - 2; i++) {
                array[i] = array[i + 1];
            }
            res = decrementArrayHeight(array);
        } else {
            System.out.println(StorageClass.indexOutOfRangeText);
            res = array;
        }
        return res;
    }

    public static String[][] deleteKeyByName(String[][] array) {
        String[][] res = array;
        String tmpKey = scanInput(StorageClass.keyIs);
        if (isThatKeyFoundInData(tmpKey, array)) {
            res = deleteKeyByIndex(keyFoundIndex(tmpKey), array);
        } else {
            System.out.println(StorageClass.keyDoesNotExist);
        }
        return res;
    }

    public static void printArray(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print("index " + i + " || ");
            for (int j = 0; j < arr[i].length; j++) {
                if (j == StorageClass.keyPosition) {
                    System.out.print("Key: " + emptySpacesForKeysDecorate(arr, arr[i][j]) + " | Values: ");
                } else if (j == StorageClass.keyPosition + 1) {
                    System.out.print(arr[i][j]);
                } else {
                    System.out.print(", " + arr[i][j]);
                }
            }
            System.out.println(".");
        }
    }

    public static String emptySpacesForKeysDecorate(String[][] array, String key) {
        int longestKey = 0;

        for (String[] strings : array) {
            if (strings[StorageClass.keyPosition].length() > longestKey) {
                longestKey = strings[StorageClass.keyPosition].length();
            }
        }
        return key + " ".repeat(Math.max(0, longestKey - key.length()));
    }

}

