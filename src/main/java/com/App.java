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

                for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
                    System.out.println(wiseSayingList.get(i));
                }
            } else if (cmd.startsWith("삭제")){
                int delID = Integer.parseInt(cmd.substring(6));
                WiseSaying wiseSayingToDel = null;

                //존재하지 않는 명언 삭제 예외 처리
                for (WiseSaying wiseSaying : wiseSayingList) {
                    if (wiseSaying.getId() == delID) {
                        wiseSayingToDel = wiseSaying;
                        break;
                    }
                }
                if(wiseSayingToDel != null) {
                    wiseSayingList.remove(wiseSayingToDel);
                    System.out.println(delID + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(delID + "번 명언은 존재하지 않습니다.");
                }

            }
        }
    }
}