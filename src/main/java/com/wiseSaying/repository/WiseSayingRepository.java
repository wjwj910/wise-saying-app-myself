package com.wiseSaying.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wiseSaying.WiseSaying;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private static int lastId = 0;
    private static final String DIRECTORY = "src/main/resources/db/wiseSaying/";
    private static final String LAST_ID_FILE = DIRECTORY + "lastId.txt";
    private final Gson gson;

    public WiseSayingRepository() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadLastId();
    }

    private void loadLastId() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(LAST_ID_FILE)));
            lastId = Integer.parseInt(content.trim());
        } catch (IOException e) {
            lastId = 0; // 파일이 없으면 0으로 초기화
        }
    }

    public static int getNextId() {
        return ++lastId;
    }

    public void save(WiseSaying wiseSaying) {
        try {
            String json = gson.toJson(wiseSaying);
            FileWriter writer = new FileWriter(DIRECTORY + wiseSaying.getId() + ".json");
            writer.write(json);
            writer.close();

            saveLastId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLastId() {
        try {
            FileWriter writer = new FileWriter(LAST_ID_FILE);
            writer.write(String.valueOf(lastId));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<WiseSaying> findAll() {
        List<WiseSaying> wiseSayingList = new ArrayList<>();
        File folder = new File(DIRECTORY);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 명언 파일만 읽고, data.json은 무시합니다.
                    if (file.getName().endsWith(".json") && !file.getName().equals("lastId.txt") && !file.getName().equals("data.json")) {
                        try {
                            String json = new String(Files.readAllBytes(file.toPath()));
                            WiseSaying wiseSaying = gson.fromJson(json, WiseSaying.class);
                            wiseSayingList.add(wiseSaying);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return wiseSayingList;
    }


    public WiseSaying findById(int id) {
        File file = new File(DIRECTORY + id + ".json");
        if (file.exists()) {
            try {
                String json = new String(Files.readAllBytes(file.toPath()));
                return gson.fromJson(json, WiseSaying.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean delete(int id) {
        WiseSaying wiseSayingToDel = findById(id);
        if (wiseSayingToDel != null) {
            File file = new File(DIRECTORY + id + ".json");
            if (file.delete()) {
                return true;
            }
        }
        return false;
    }

    public void update(int id, String author, String content) {
        WiseSaying wiseSayingToUpdate = findById(id);
        if (wiseSayingToUpdate != null) {
            wiseSayingToUpdate.setAuthor(author);
            wiseSayingToUpdate.setContent(content);
            save(wiseSayingToUpdate); // 파일 업데이트
        }
    }


    public List<WiseSaying> findByKeyword(String keywordType, String keyword) {
        List<WiseSaying> result = new ArrayList<>();
        List<WiseSaying> wiseSayings = findAll();

        for (WiseSaying wiseSaying : wiseSayings) {
            if (keywordType.equals("content") && wiseSaying.getContent().contains(keyword)) {
                result.add(wiseSaying);
            } else if (keywordType.equals("author") && wiseSaying.getAuthor().contains(keyword)) {
                result.add(wiseSaying);
            }
        }
        return result;
    }

}
