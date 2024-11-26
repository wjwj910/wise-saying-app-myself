package com;

import java.util.Scanner;

public class App {
    public void run(){
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            if(cmd.equals("종료")){
                break;
            }
        }
    }
}