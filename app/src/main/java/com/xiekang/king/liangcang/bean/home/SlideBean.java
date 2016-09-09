package com.xiekang.king.liangcang.bean.home;

/**
 * Created by King on 2016/9/8.
 */
public class SlideBean {


    /**
     * home_id : 46
     * home_type : 4
     * order_num : 9
     * one : {"home_id":"46","content_type":"1","content_id":"1484","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24229.jpg","pos":"1","topic_name":"全网独家8折闪购 | 德国红点奖轻便型功夫茶具","topic_url":"http://www.iliangcang.com/i/topicapp/201609014938"}
     * two : {"home_id":"46","content_type":"1","content_id":"1493","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24260.jpg","pos":"2","topic_name":"美到不行的中秋家宴指南","topic_url":"http://www.iliangcang.com/i/topicapp/201609063455"}
     * three : {"home_id":"46","content_type":"1","content_id":"1485","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24224.jpg","pos":"3","topic_name":"童年中秋味 | 迪士尼黑皮月饼×哲品中秋礼盒","topic_url":"http://www.iliangcang.com/i/topicapp/201609015539"}
     * four : {"home_id":"46","content_type":"1","content_id":"1481","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24225.jpg","pos":"4","topic_name":"三种月亮，三款最美中秋礼盒","topic_url":"http://www.iliangcang.com/i/topicapp/201608313328"}
     */

    private String slide_id;
    private String content_type;
    private String content_id;
    private String pic_url;
    private String order_num;
    private String topic_name;
    private String topic_url;

    public String getSlide_id() {
        return slide_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public String getContent_id() {
        return content_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getOrder_num() {
        return order_num;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public String getTopic_url() {
        return topic_url;
    }

    public SlideBean(String slide_id, String content_type, String content_id, String pic_url, String order_num, String topic_name, String topic_url) {
        this.slide_id = slide_id;
        this.content_type = content_type;
        this.content_id = content_id;
        this.pic_url = pic_url;
        this.order_num = order_num;
        this.topic_name = topic_name;
        this.topic_url = topic_url;
    }
}
