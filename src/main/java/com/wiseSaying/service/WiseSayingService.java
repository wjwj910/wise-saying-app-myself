package com.wiseSaying.service;

import com.google.gson.Gson;
import com.wiseSaying.WiseSaying;
import com.wiseSaying.repository.WiseSayingRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WiseSayingService {
    private WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public void addWiseSaying(String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(WiseSayingRepository.getNextId(), author, content);
        wiseSayingRepository.save(wiseSaying);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void listWiseSayings(int page) {
        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll();

        Collections.sort(wiseSayings, Comparator.comparingInt(WiseSaying::getId).reversed());

        int totalCount = wiseSayings.size();
        int itemsPerPage = 5;
        int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);

        if (page < 1){
            page = 1;
        } else if (page > totalPages){
            page = totalPages;
        }

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalCount);
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (int i = startIndex; i < endIndex; i++) {
            System.out.println(wiseSayings.get(i));
        }

        // 페이지 정보 출력
        System.out.print("----------------------\n페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                System.out.print("[" + i + "] ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
        /*
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            System.out.println(wiseSayings.get(i));
        }
        */
    

    public void deleteWiseSaying(int id) {
        if (wiseSayingRepository.delete(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public String getWiseSayingContent(int id) {
        WiseSaying wiseSaying = wiseSayingRepository.findById(id);
        return wiseSaying != null ? wiseSaying.getContent() : null;
    }

    public String getWiseSayingAuthor(int id) {
        WiseSaying wiseSaying = wiseSayingRepository.findById(id);
        return wiseSaying != null ? wiseSaying.getAuthor() : null;
    }

    public void modifyWiseSaying(int id, String author, String content) {
        wiseSayingRepository.update(id, author, content);
        System.out.println(id + "번 명언이 수정되었습니다.");
    }

    public void buildDataJson() {
        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll();
        String json = new Gson().toJson(wiseSayings);

        try (FileWriter writer = new FileWriter("src/main/resources/db/wiseSaying/data.json")) {
            writer.write(json);
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<WiseSaying> searchWiseSayings(String keywordType, String keyword) {
        List<WiseSaying> results = wiseSayingRepository.findByKeyword(keywordType, keyword);

        // 내림차순 정렬
        Collections.sort(results, new Comparator<WiseSaying>() {
            @Override
            public int compare(WiseSaying ws1, WiseSaying ws2) {
                return Integer.compare(ws2.getId(), ws1.getId());
            }
        });

        return results;
    }
}
