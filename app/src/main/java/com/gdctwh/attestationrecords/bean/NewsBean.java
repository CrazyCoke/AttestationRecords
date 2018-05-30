package com.gdctwh.attestationrecords.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 * 文化动态列表
 */

public class NewsBean {


    private List<ArticlesBean> articles;//列表集合

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * article_id : 154
         * original : http://120.77.214.194:8086/image/catalog/test/wenhau1.jpg
         * thumb : http://120.77.214.194:8086/image/cache/catalog/test/wenhau1-x.jpg
         * name : 让艺术品金融化推动文化产业发展
         * preview : <founder-content><p>国家行政学院原副院长周文彰认为艺术品交易的创新一定会受消费者欢迎</p></founder-content><p></p>
         * attributes : []
         * href : http://120.77.214.194:8086/index.php?route=newsblog/article&amp;newsblog_path=&amp;newsblog_article_id=154
         * date : false
         * date_modified : false
         * viewed : 36
         */

        private String article_id;//详情id
        private String original;//图片1
        private String thumb;//图片2
        private String name;//标题
        private String preview;//简介
        private String href;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        private String date;

        public String getDate_modified() {
            return date_modified;
        }

        public void setDate_modified(String date_modified) {
            this.date_modified = date_modified;
        }

        private String date_modified;
        private String viewed;
        private List<?> attributes;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }


        public String getViewed() {
            return viewed;
        }

        public void setViewed(String viewed) {
            this.viewed = viewed;
        }

        public List<?> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<?> attributes) {
            this.attributes = attributes;
        }
    }
}
