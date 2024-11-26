package com;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static int lastId = 0;
    ArrayList<WiseSaying> wiseSayingList = new ArrayList<>();

    public void run() {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.print("작가 : ");
                String author = scanner.nextLine();

                int id = ++lastId;
                WiseSaying wiseSaying = new WiseSaying(id, author, content);
                wiseSayingList.add(wiseSaying);
                System.out.println(id + "번 명언이 등록되었습니다.");
            } else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                for (WiseSaying wiseSaying : wiseSayingList) {
                    System.out.println(wiseSaying);
                }
            }
        }
    }
}