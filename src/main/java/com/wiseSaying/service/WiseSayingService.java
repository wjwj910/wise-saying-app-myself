package com.wiseSaying.service;
// 역할 : 순수 비지니스 로직
// 스캐너 사용금지, 출력금지
import com.wiseSaying.WiseSaying;
import com.wiseSaying.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public void addWiseSaying(String author, String content) {
        wiseSayingRepository.save(new WiseSaying(WiseSayingRepository.getNextId(), author, content));
        System.out.println(WiseSayingRepository.getLastId() + "번 명언이 등록되었습니다.");
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
}