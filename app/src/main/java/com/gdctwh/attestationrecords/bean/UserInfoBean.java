package com.gdctwh.attestationrecords.bean;

/**
 * Created by Administrator on 2018/3/30.
 */

public class UserInfoBean {

    /**
     * ret : 1
     * uid : 10
     * username : 先锋
     * image :
     */

    private int ret;
    private String uid;
    private String username;
    private String image;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
