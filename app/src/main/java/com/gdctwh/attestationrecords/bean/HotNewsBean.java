package com.gdctwh.attestationrecords.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/31.
 */

public class HotNewsBean {

    private List<SliderNewsBean> sliderNews;
    private List<NewsItemsBean> newsItems;

    public List<SliderNewsBean> getSliderNews() {
        return sliderNews;
    }

    public void setSliderNews(List<SliderNewsBean> sliderNews) {
        this.sliderNews = sliderNews;
    }

    public List<NewsItemsBean> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItemsBean> newsItems) {
        this.newsItems = newsItems;
    }

    public static class SliderNewsBean {
        /**
         * id : 4886
         * title : 习近平签署主席令 任命国务院副总理、国务委员
         * date : 2018-03-19 02:35:19
         * desc : 两会新华社快讯：国家主席习近平签署主席令，任命了国务院副总理、国务委员、各部部长、各委员会主任、中国人民银行行长、审计长、秘书长。
         * image : image/201803/20180319103557797666403.jpg
         * adv : 0
         * advlink :
         */

        private String id;
        private String title;
        private String date;
        private String desc;
        private String image;
        private String adv;
        private String advlink;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAdv() {
            return adv;
        }

        public void setAdv(String adv) {
            this.adv = adv;
        }

        public String getAdvlink() {
            return advlink;
        }

        public void setAdvlink(String advlink) {
            this.advlink = advlink;
        }
    }

    public static class NewsItemsBean {
        /**
         * id : 4885
         * title : 李嘉誠的商業智慧
         * date : 2018-03-19 02:26:10
         * desc : 李嘉誠正式宣布退休，可說是一個時代的終結。這位年近90歲的長和系創辦人，1968年將一間塑膠制品廠，發展成今日一個業務橫跨全球的巨型跨國綜合企業，單計本港上市的公司就約有1萬億元的市值。其成就非凡，不容置疑。
         * image : image/201803/201803191026101857064915.jpg
         * adv : 0
         * advlink :
         * topic : 上市公司
         * type : 1
         */

        private String id;
        private String title;
        private String date;
        private String desc;
        private String image;
        private String adv;
        private String advlink;
        private String topic;
        private int type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAdv() {
            return adv;
        }

        public void setAdv(String adv) {
            this.adv = adv;
        }

        public String getAdvlink() {
            return advlink;
        }

        public void setAdvlink(String advlink) {
            this.advlink = advlink;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
