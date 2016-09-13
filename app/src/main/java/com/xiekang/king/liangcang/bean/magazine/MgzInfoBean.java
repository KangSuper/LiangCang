package com.xiekang.king.liangcang.bean.magazine;

/**
 * Created by King on 2016/9/12.
 *
 */
public class MgzInfoBean {
      private String taid;
      private String topic_name;
      private String cat_id;
      private String author_id;
      private String topic_url;
      private String access_url;
      private String cover_img;
      private String cover_img_new;
      private String hit_number;
      private String addtime;
      private String content;
      private String nav_title;
      private String author_name;
      private String cat_name;

    public MgzInfoBean(String taid, String topic_name, String cat_id, String author_id, String topic_url, String access_url, String cover_img, String cover_img_new, String hit_number, String addtime, String content, String nav_title, String author_name, String cat_name) {
        this.taid = taid;
        this.topic_name = topic_name;
        this.cat_id = cat_id;
        this.author_id = author_id;
        this.topic_url = topic_url;
        this.access_url = access_url;
        this.cover_img = cover_img;
        this.cover_img_new = cover_img_new;
        this.hit_number = hit_number;
        this.addtime = addtime;
        this.content = content;
        this.nav_title = nav_title;
        this.author_name = author_name;
        this.cat_name = cat_name;
    }


    public String getTaid() {
        return taid;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getTopic_url() {
        return topic_url;
    }

    public String getAccess_url() {
        return access_url;
    }

    public String getCover_img() {
        return cover_img;
    }

    public String getCover_img_new() {
        return cover_img_new;
    }

    public String getHit_number() {
        return hit_number;
    }

    public String getAddtime() {
        return addtime;
    }

    public String getContent() {
        return content;
    }

    public String getNav_title() {
        return nav_title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getCat_name() {
        return cat_name;
    }
}
