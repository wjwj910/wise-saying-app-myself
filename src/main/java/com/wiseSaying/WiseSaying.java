package com.wiseSaying;

public class WiseSaying {
    private int id;
    private String author;
    private String content;

    public WiseSaying(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }
    public int getId() { return id; }

    public String getAuthor() { return author; }

    public void setAuthor(String modifyAuthor) { this.author = modifyAuthor; }

    public String getContent() { return content; }

    public void setContent(String modifyContent) { this.content = modifyContent; }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }
}
