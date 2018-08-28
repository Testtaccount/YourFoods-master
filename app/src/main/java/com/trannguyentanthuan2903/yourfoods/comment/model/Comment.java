package com.trannguyentanthuan2903.yourfoods.comment.model;

import java.util.List;

/**
 * Created by Administrator on 11/3/2017.
 */

public class Comment {

    private String key_cmt;
    private String comment;
    private String user_id;
    private List<Like> likes;
    private String date_created;

    public Comment() {

    }

    public Comment(String key_cmt,String comment, String user_id, List<Like> likes, String date_created) {
        this.key_cmt = key_cmt;
        this.comment = comment;
        this.user_id = user_id;
        this.likes = likes;
        this.date_created = date_created;
    }

    public String getKey_cmt() {
        return key_cmt;
    }

    public void setKey_cmt(String key_cmt) {
        this.key_cmt = key_cmt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "key_cmt='" + key_cmt + '\'' +
                ", comment='" + comment + '\'' +
                ", user_id='" + user_id + '\'' +
                ", likes=" + likes +
                ", date_created='" + date_created + '\'' +
                '}';
    }
}