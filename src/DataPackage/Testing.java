package DataPackage;

import java.util.Scanner;

public class Testing {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        scanNumber();

    }
    public static int scanNumber(){
        do {
            System.out.println("start");
            if (scan.hasNextInt()) {
                return scan.nextInt();
            }else{
                scan.nextLine();
            }
        } while (!scan.hasNextInt());
        return -1;
    }
}
