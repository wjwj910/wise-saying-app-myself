package com.wiseSaying.repository;
// 역할 : 데이터의 조회, 수정, 삭제, 생성을 담당
// 스캐너 사용금지, 출력 금지
import com.wiseSaying.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private static int lastId = 0;
    private List<WiseSaying> wiseSayingList = new ArrayList<>();

    public static int getNextId() {
        return ++lastId;
    }

    public static int getLastId() {
        return lastId;
    }

    public void save(WiseSaying wiseSaying) {
        wiseSayingList.add(wiseSaying);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingList;
    }

    public WiseSaying findById(int id) {
        for (WiseSaying wiseSaying : wiseSayingList) {
            if (wiseSaying.getId() == id) {
                return wiseSaying;
            }
        }
        return null;
    }

    public boolean delete(int id) {
        WiseSaying wiseSayingToDel = findById(id);
        if (wiseSayingToDel != null) {
            wiseSayingList.remove(wiseSayingToDel);
            return true;
        }
        return false;
    }

    public void update(int id, String author, String content) {
        WiseSaying wiseSayingToUpdate = findById(id);
        if (wiseSayingToUpdate != null) {
            wiseSayingToUpdate.setAuthor(author);
            wiseSayingToUpdate.setContent(content);
        }
    }
}