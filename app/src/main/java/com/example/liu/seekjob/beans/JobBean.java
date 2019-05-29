package com.example.liu.seekjob.beans;

public class JobBean {
    private int id;
    private String title;
    private String content;
    private String publisher;
    private long publishTime;
    private int state;
    private String publisherHeadImg;
    private int hits;
    private String skill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPublisherHeadImg() {
        return publisherHeadImg;
    }

    public void setPublisherHeadImg(String publisherHeadImg) {
        this.publisherHeadImg = publisherHeadImg;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
