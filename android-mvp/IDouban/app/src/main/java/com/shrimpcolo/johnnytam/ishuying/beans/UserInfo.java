package com.shrimpcolo.johnnytam.ishuying.beans;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1317025437668609819L;

    private String userIcon;
    private String userName;
    private Gender userGender;
    private String userNote;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Gender getUserGender() {
        return userGender;
    }

    public void setUserGender(Gender userGender) {
        this.userGender = userGender;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public static enum Gender {MALE, FEMALE}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserInfo: [");
        builder.append("\n");
        builder.append(userName);
        builder.append(",  ");
        builder.append(userIcon);
        builder.append(", ");
        builder.append(userGender);
        builder.append(", ");
        builder.append(userNote);
        builder.append(" ]");

        return builder.toString();
    }
}
