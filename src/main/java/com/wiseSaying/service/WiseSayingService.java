package com.wiseSaying.service;

import com.google.gson.Gson;
import com.wiseSaying.WiseSaying;
import com.wiseSaying.repository.WiseSayingRepository;

import java.io.FileWriter;
import java.io.IOException;
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

    public void listWiseSayings() {
        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll();
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            System.out.println(wiseSayings.get(i));
        }
    }

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
}
