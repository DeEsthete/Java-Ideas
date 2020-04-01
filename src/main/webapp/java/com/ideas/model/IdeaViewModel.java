package com.ideas.model;

import com.ideas.entity.User;

import java.util.Date;
import java.util.List;

public class IdeaViewModel {
    private int id;
    private int userId;
    private String userNickname;
    private String content;
    private Date dateCreate;
    private int likesCount;
    private int dislikesCount;
    private List<User> usersLike;
    private List<User> usersDislike;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public List<User> getUsersLike() {
        return usersLike;
    }

    public void setUsersLike(List<User> usersLike) {
        this.usersLike = usersLike;
    }

    public List<User> getUsersDislike() {
        return usersDislike;
    }

    public void setUsersDislike(List<User> usersDislike) {
        this.usersDislike = usersDislike;
    }
}
