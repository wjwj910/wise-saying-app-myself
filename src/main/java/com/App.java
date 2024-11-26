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
            } else if (cmd.startsWith("삭제?id=")) {
                int delID = Integer.parseInt(cmd.substring(6));
                WiseSaying wiseSayingToDel = null;

                // 존재하지 않는 명언 삭제 예외 처리
                for (WiseSaying wiseSaying : wiseSayingList) {
                    if (wiseSaying.getId() == delID) {
                        wiseSayingToDel = wiseSaying;
                        break;
                    }
                }
                if (wiseSayingToDel != null) {
                    wiseSayingList.remove(wiseSayingToDel);
                    System.out.println(delID + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(delID + "번 명언은 존재하지 않습니다.");
                }
            } else if (cmd.startsWith("수정?id=")) {
                int modifyID = Integer.parseInt(cmd.substring(6));
                WiseSaying wiseSayingToModify = null;

                // 수정할 명언 찾기
                for (WiseSaying wiseSaying : wiseSayingList) {
                    if (wiseSaying.getId() == modifyID) {
                        wiseSayingToModify = wiseSaying;
                        break;
                    }
                }
                if (wiseSayingToModify != null) {
                    System.out.println("명언(기존) : " + wiseSayingToModify.getContent());
                    System.out.print("명언 : ");
                    String modifyContent = scanner.nextLine(); // 새로운 명언 입력 받기
                    System.out.println("작가(기존) : " + wiseSayingToModify.getAuthor());
                    System.out.print("작가 : ");
                    String modifyAuthor = scanner.nextLine(); // 새로운 작가 입력 받기

                    // 기존 명언과 작가를 새로운 값으로 교체
                    wiseSayingToModify.setContent(modifyContent);
                    wiseSayingToModify.setAuthor(modifyAuthor);

                    System.out.println(modifyID + "번 명언이 수정되었습니다.");
                } else {
                    System.out.println(modifyID + "번 명언은 존재하지 않습니다.");
                }
            }
        }
    }
}