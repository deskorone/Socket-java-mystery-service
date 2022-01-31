package clientpack;

import java.util.Scanner;

public class InputNum {
    public synchronized static int inputInteger(){
        Scanner sc = new Scanner(System.in);
        Integer num = null;
        System.out.println("Input num: ");
        while(num == null) {
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                if(num < 0 || num > 2){
                    num = null;
                    System.out.println("Num > 0 or < 2!" );
                }
            } else {
                sc.nextLine();
                System.out.println("Input int");
            }
        }
        return num;
    }
    public synchronized static int inputIntegerChoise(){
        Scanner sc = new Scanner(System.in);
        Integer num = null;
        System.out.println("Input num: ");
        while(num == null) {
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                if(num < 0 || num > 5){
                    num = null;
                    System.out.println("Num > 0 or < 5!" );
                }
            } else {
                sc.nextLine();
                System.out.println("Input int");
            }
        }
        return num;
    }

}
