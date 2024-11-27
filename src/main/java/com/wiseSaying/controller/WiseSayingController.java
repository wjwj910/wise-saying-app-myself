package com.wiseSaying.controller;

import com.wiseSaying.entity.WiseSaying;
import com.wiseSaying.service.WiseSayingService;

import java.util.List;
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
            wiseSayingService.listWiseSayings(1);
        } else if (cmd.startsWith("목록?page=")){
            int page;
            if (cmd.equals("목록?page=")){
                page = 1;
            } else {
                page = Integer.parseInt(cmd.split("=")[1]);
            }
            wiseSayingService.listWiseSayings(page);
        }else if (cmd.startsWith("삭제?id=")) {
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
        } else if (cmd.equals("빌드")) {
            wiseSayingService.buildDataJson();
        } else if (cmd.startsWith("목록?keywordType=")) {
            String[] parts = cmd.split("&");
            String keywordType = parts[0].split("=")[1];
            String keyword = parts[1].split("=")[1];

            // 페이지 번호 처리
            int page = 1; // 기본 페이지는 1
            if (parts.length > 2) {
                // 페이지 번호가 포함되어 있는 경우
                page = Integer.parseInt(parts[2].split("=")[1]);
            }

            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");

            List<WiseSaying> results = wiseSayingService.searchWiseSayings(keywordType, keyword, page);
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            for (WiseSaying wiseSaying : results) {
                System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            }
        }
    }
}