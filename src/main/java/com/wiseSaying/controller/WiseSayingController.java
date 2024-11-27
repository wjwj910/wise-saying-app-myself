package com.wiseSaying.controller;
// 역할 : 고객의 명령을 입력받고 적절한 응답을 표현
// 스캐너 사용가능, 출력 사용가능

import com.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void handleCommand(String cmd, Scanner scanner) {
        if (cmd.equals("등록")) {
            System.out.print("명언 : ");
            String content = scanner.nextLine();
            System.out.print("작가 : ");
            String author = scanner.nextLine();
            wiseSayingService.addWiseSaying(author, content);
        } else if (cmd.equals("목록")) {
            wiseSayingService.listWiseSayings();
        } else if (cmd.startsWith("삭제?id=")) {
            int delID = Integer.parseInt(cmd.substring(6));
            wiseSayingService.deleteWiseSaying(delID);
        } else if (cmd.startsWith("수정?id=")) {
            int modifyID = Integer.parseInt(cmd.substring(6));
            System.out.println("명언(기존) : " + wiseSayingService.getWiseSayingContent(modifyID));
            System.out.print("명언 : ");
            String modifyContent = scanner.nextLine();

            System.out.println("작가(기존) : " + wiseSayingService.getWiseSayingAuthor(modifyID));
            System.out.print("작가 : ");
            String modifyAuthor = scanner.nextLine();

            wiseSayingService.modifyWiseSaying(modifyID, modifyAuthor, modifyContent);
        } else if(cmd.equals("빌드")){
            wiseSayingService.buildDataJson();
        }
    }
}