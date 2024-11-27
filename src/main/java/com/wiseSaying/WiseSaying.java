package com.wiseSaying;

// 역할 : 명언 객체(번호/명언내용/작가)
// 컨트롤러, 서비스, 리포지토리에서 모두 사용가능

public class WiseSaying {
    private final int id;
    private String author;
    private String content;

    public WiseSaying(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public int getId() { return id; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }
}
