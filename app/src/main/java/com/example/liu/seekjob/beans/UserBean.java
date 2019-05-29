package com.example.liu.seekjob.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {
    private String user_name;
    private String password;
    private String nick_name;
    private String mail_address;
    private String phone_number;
    private int user_level;
//    private byte[] mUserImage;

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nick_name;
    }

    public void setNickName(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMailAddress() {
        return mail_address;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getUserLevel() {
        return user_level;
    }

    public void setUserLevel(int user_level) {
        this.user_level = user_level;
    }

//    public byte[] getUserImage() {
//        return mUserImage;
//    }
//
//    public void setUserImage(byte[] userImage) {
//        mUserImage = userImage;
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_name);
        dest.writeString(password);
        dest.writeString(nick_name);
        dest.writeString(mail_address);
        dest.writeString(phone_number);
        dest.writeInt(user_level);
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {

        @Override
        public UserBean createFromParcel(Parcel source) {
            UserBean bean = new UserBean();
            bean.user_name = source.readString();
            bean.password = source.readString();
            bean.nick_name = source.readString();
            bean.mail_address = source.readString();
            bean.phone_number = source.readString();
            bean.user_level = source.readInt();
            return bean;
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
