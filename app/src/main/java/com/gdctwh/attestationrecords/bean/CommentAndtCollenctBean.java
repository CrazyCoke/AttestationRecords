package com.gdctwh.attestationrecords.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public class CommentAndtCollenctBean {


    /**
     * ret : true
     * items : [{"uid":"2","username":"adobe","content":"testforme","time":"2018-01-15 11:03"},{"uid":"2","username":"adobe","content":"testforme","time":"2018-01-15 11:02"},{"uid":"2","username":"","content":"testforme","time":"2017-12-23 15:56"}]
     */

    private boolean ret;
    private int num;
    private List<ItemsBean> items;

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static class ItemsBean {
        /**
         * uid : 2
         * username : adobe
         * content : testforme
         * time : 2018-01-15 11:03
         */

        private String uid;
        private String username;
        private String content;
        private String time;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
