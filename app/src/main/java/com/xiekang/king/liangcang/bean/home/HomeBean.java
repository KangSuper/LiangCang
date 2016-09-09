package com.xiekang.king.liangcang.bean.home;

import java.util.List;

/**
 * Created by King on 2016/9/8.
 */
public class HomeBean {

    /**
     * has_more : false
     * num_items : 1
     * items : {"slide":[{"slide_id":"218","content_type":"1","content_id":"1472","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24136.jpg","order_num":"10","topic_name":"今年中秋，最拿得出手的月饼都在这了","topic_url":"http://www.iliangcang.com/i/topicapp/201608264439"},{"slide_id":"217","content_type":"2","content_id":"389","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24135.jpg","order_num":"9","topic_name":"","topic_url":""},{"slide_id":"222","content_type":"1","content_id":"1493","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24262.jpg","order_num":"8","topic_name":"美到不行的中秋家宴指南","topic_url":"http://www.iliangcang.com/i/topicapp/201609063455"},{"slide_id":"221","content_type":"1","content_id":"1487","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24231.jpg","order_num":"7","topic_name":"教师节好礼 | 送给那些年我们爱过的酷导师","topic_url":"http://www.iliangcang.com/i/topicapp/201609021854"},{"slide_id":"219","content_type":"1","content_id":"1483","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24137.jpg","order_num":"6","topic_name":"日本最流行的\u201c肌断食\u201d疗法，秋天皮肤不再过敏","topic_url":"http://www.iliangcang.com/i/topicapp/201609012855"}],"list":[{"home_id":"46","home_type":"4","order_num":"9","one":{"home_id":"46","content_type":"1","content_id":"1484","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24229.jpg","pos":"1","topic_name":"全网独家8折闪购 | 德国红点奖轻便型功夫茶具","topic_url":"http://www.iliangcang.com/i/topicapp/201609014938"},"two":{"home_id":"46","content_type":"1","content_id":"1493","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24260.jpg","pos":"2","topic_name":"美到不行的中秋家宴指南","topic_url":"http://www.iliangcang.com/i/topicapp/201609063455"},"three":{"home_id":"46","content_type":"1","content_id":"1485","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24224.jpg","pos":"3","topic_name":"童年中秋味 | 迪士尼黑皮月饼×哲品中秋礼盒","topic_url":"http://www.iliangcang.com/i/topicapp/201609015539"},"four":{"home_id":"46","content_type":"1","content_id":"1481","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24225.jpg","pos":"4","topic_name":"三种月亮，三款最美中秋礼盒","topic_url":"http://www.iliangcang.com/i/topicapp/201608313328"}},{"home_id":"49","home_type":"1","order_num":"8","one":{"home_id":"49","content_type":"1","content_id":"1486","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24227.jpg","pos":"1","topic_name":"￥119起｜东方最美微醺甜酒，糯言","topic_url":"http://www.iliangcang.com/i/topicapp/201609025404"}},{"home_id":"51","home_type":"2","order_num":"7","one":{"home_id":"51","content_type":"1","content_id":"1469","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24261.jpg","pos":"1","topic_name":"一套东方餐具才是团聚时刻的最好见证","topic_url":"http://www.iliangcang.com/i/topicapp/201608230858"},"two":{"home_id":"51","content_type":"1","content_id":"1471","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24226.jpg","pos":"2","topic_name":"吃腻了中秋月饼，不如试试台湾风味土凤梨酥/牛轧糖","topic_url":"http://www.iliangcang.com/i/topicapp/201608254155"}}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private boolean has_more;
        private int num_items;
        private ItemsBean items;

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public int getNum_items() {
            return num_items;
        }

        public void setNum_items(int num_items) {
            this.num_items = num_items;
        }

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * slide_id : 218
             * content_type : 1
             * content_id : 1472
             * pic_url : http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24136.jpg
             * order_num : 10
             * topic_name : 今年中秋，最拿得出手的月饼都在这了
             * topic_url : http://www.iliangcang.com/i/topicapp/201608264439
             */

            private List<SlideBean> slide;
            /**
             * home_id : 46
             * home_type : 4
             * order_num : 9
             * one : {"home_id":"46","content_type":"1","content_id":"1484","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24229.jpg","pos":"1","topic_name":"全网独家8折闪购 | 德国红点奖轻便型功夫茶具","topic_url":"http://www.iliangcang.com/i/topicapp/201609014938"}
             * two : {"home_id":"46","content_type":"1","content_id":"1493","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24260.jpg","pos":"2","topic_name":"美到不行的中秋家宴指南","topic_url":"http://www.iliangcang.com/i/topicapp/201609063455"}
             * three : {"home_id":"46","content_type":"1","content_id":"1485","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24224.jpg","pos":"3","topic_name":"童年中秋味 | 迪士尼黑皮月饼×哲品中秋礼盒","topic_url":"http://www.iliangcang.com/i/topicapp/201609015539"}
             * four : {"home_id":"46","content_type":"1","content_id":"1481","pic_url":"http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24225.jpg","pos":"4","topic_name":"三种月亮，三款最美中秋礼盒","topic_url":"http://www.iliangcang.com/i/topicapp/201608313328"}
             */

            private List<ListBean> list;

            public List<SlideBean> getSlide() {
                return slide;
            }

            public void setSlide(List<SlideBean> slide) {
                this.slide = slide;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class SlideBean {
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

                public void setSlide_id(String slide_id) {
                    this.slide_id = slide_id;
                }

                public String getContent_type() {
                    return content_type;
                }

                public void setContent_type(String content_type) {
                    this.content_type = content_type;
                }

                public String getContent_id() {
                    return content_id;
                }

                public void setContent_id(String content_id) {
                    this.content_id = content_id;
                }

                public String getPic_url() {
                    return pic_url;
                }

                public void setPic_url(String pic_url) {
                    this.pic_url = pic_url;
                }

                public String getOrder_num() {
                    return order_num;
                }

                public void setOrder_num(String order_num) {
                    this.order_num = order_num;
                }

                public String getTopic_name() {
                    return topic_name;
                }

                public void setTopic_name(String topic_name) {
                    this.topic_name = topic_name;
                }

                public String getTopic_url() {
                    return topic_url;
                }

                public void setTopic_url(String topic_url) {
                    this.topic_url = topic_url;
                }
            }

            public static class ListBean {
                private String home_id;
                private String home_type;
                private String order_num;
                /**
                 * home_id : 46
                 * content_type : 1
                 * content_id : 1484
                 * pic_url : http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24229.jpg
                 * pos : 1
                 * topic_name : 全网独家8折闪购 | 德国红点奖轻便型功夫茶具
                 * topic_url : http://www.iliangcang.com/i/topicapp/201609014938
                 */

                private OneBean one;
                /**
                 * home_id : 46
                 * content_type : 1
                 * content_id : 1493
                 * pic_url : http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24260.jpg
                 * pos : 2
                 * topic_name : 美到不行的中秋家宴指南
                 * topic_url : http://www.iliangcang.com/i/topicapp/201609063455
                 */

                private TwoBean two;
                /**
                 * home_id : 46
                 * content_type : 1
                 * content_id : 1485
                 * pic_url : http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24224.jpg
                 * pos : 3
                 * topic_name : 童年中秋味 | 迪士尼黑皮月饼×哲品中秋礼盒
                 * topic_url : http://www.iliangcang.com/i/topicapp/201609015539
                 */

                private ThreeBean three;
                /**
                 * home_id : 46
                 * content_type : 1
                 * content_id : 1481
                 * pic_url : http://7qn7hi.com1.z0.glb.clouddn.com/ware/orig/2/24/24225.jpg
                 * pos : 4
                 * topic_name : 三种月亮，三款最美中秋礼盒
                 * topic_url : http://www.iliangcang.com/i/topicapp/201608313328
                 */

                private FourBean four;

                public String getHome_id() {
                    return home_id;
                }

                public void setHome_id(String home_id) {
                    this.home_id = home_id;
                }

                public String getHome_type() {
                    return home_type;
                }

                public void setHome_type(String home_type) {
                    this.home_type = home_type;
                }

                public String getOrder_num() {
                    return order_num;
                }

                public void setOrder_num(String order_num) {
                    this.order_num = order_num;
                }

                public OneBean getOne() {
                    return one;
                }

                public void setOne(OneBean one) {
                    this.one = one;
                }

                public TwoBean getTwo() {
                    return two;
                }

                public void setTwo(TwoBean two) {
                    this.two = two;
                }

                public ThreeBean getThree() {
                    return three;
                }

                public void setThree(ThreeBean three) {
                    this.three = three;
                }

                public FourBean getFour() {
                    return four;
                }

                public void setFour(FourBean four) {
                    this.four = four;
                }

                public static class OneBean {
                    private String home_id;
                    private String content_type;
                    private String content_id;
                    private String pic_url;
                    private String pos;
                    private String topic_name;
                    private String topic_url;

                    public String getHome_id() {
                        return home_id;
                    }

                    public void setHome_id(String home_id) {
                        this.home_id = home_id;
                    }

                    public String getContent_type() {
                        return content_type;
                    }

                    public void setContent_type(String content_type) {
                        this.content_type = content_type;
                    }

                    public String getContent_id() {
                        return content_id;
                    }

                    public void setContent_id(String content_id) {
                        this.content_id = content_id;
                    }

                    public String getPic_url() {
                        return pic_url;
                    }

                    public void setPic_url(String pic_url) {
                        this.pic_url = pic_url;
                    }

                    public String getPos() {
                        return pos;
                    }

                    public void setPos(String pos) {
                        this.pos = pos;
                    }

                    public String getTopic_name() {
                        return topic_name;
                    }

                    public void setTopic_name(String topic_name) {
                        this.topic_name = topic_name;
                    }

                    public String getTopic_url() {
                        return topic_url;
                    }

                    public void setTopic_url(String topic_url) {
                        this.topic_url = topic_url;
                    }
                }

                public static class TwoBean {
                    private String home_id;
                    private String content_type;
                    private String content_id;
                    private String pic_url;
                    private String pos;
                    private String topic_name;
                    private String topic_url;

                    public String getHome_id() {
                        return home_id;
                    }

                    public void setHome_id(String home_id) {
                        this.home_id = home_id;
                    }

                    public String getContent_type() {
                        return content_type;
                    }

                    public void setContent_type(String content_type) {
                        this.content_type = content_type;
                    }

                    public String getContent_id() {
                        return content_id;
                    }

                    public void setContent_id(String content_id) {
                        this.content_id = content_id;
                    }

                    public String getPic_url() {
                        return pic_url;
                    }

                    public void setPic_url(String pic_url) {
                        this.pic_url = pic_url;
                    }

                    public String getPos() {
                        return pos;
                    }

                    public void setPos(String pos) {
                        this.pos = pos;
                    }

                    public String getTopic_name() {
                        return topic_name;
                    }

                    public void setTopic_name(String topic_name) {
                        this.topic_name = topic_name;
                    }

                    public String getTopic_url() {
                        return topic_url;
                    }

                    public void setTopic_url(String topic_url) {
                        this.topic_url = topic_url;
                    }
                }

                public static class ThreeBean {
                    private String home_id;
                    private String content_type;
                    private String content_id;
                    private String pic_url;
                    private String pos;
                    private String topic_name;
                    private String topic_url;

                    public String getHome_id() {
                        return home_id;
                    }

                    public void setHome_id(String home_id) {
                        this.home_id = home_id;
                    }

                    public String getContent_type() {
                        return content_type;
                    }

                    public void setContent_type(String content_type) {
                        this.content_type = content_type;
                    }

                    public String getContent_id() {
                        return content_id;
                    }

                    public void setContent_id(String content_id) {
                        this.content_id = content_id;
                    }

                    public String getPic_url() {
                        return pic_url;
                    }

                    public void setPic_url(String pic_url) {
                        this.pic_url = pic_url;
                    }

                    public String getPos() {
                        return pos;
                    }

                    public void setPos(String pos) {
                        this.pos = pos;
                    }

                    public String getTopic_name() {
                        return topic_name;
                    }

                    public void setTopic_name(String topic_name) {
                        this.topic_name = topic_name;
                    }

                    public String getTopic_url() {
                        return topic_url;
                    }

                    public void setTopic_url(String topic_url) {
                        this.topic_url = topic_url;
                    }
                }

                public static class FourBean {
                    private String home_id;
                    private String content_type;
                    private String content_id;
                    private String pic_url;
                    private String pos;
                    private String topic_name;
                    private String topic_url;

                    public String getHome_id() {
                        return home_id;
                    }

                    public void setHome_id(String home_id) {
                        this.home_id = home_id;
                    }

                    public String getContent_type() {
                        return content_type;
                    }

                    public void setContent_type(String content_type) {
                        this.content_type = content_type;
                    }

                    public String getContent_id() {
                        return content_id;
                    }

                    public void setContent_id(String content_id) {
                        this.content_id = content_id;
                    }

                    public String getPic_url() {
                        return pic_url;
                    }

                    public void setPic_url(String pic_url) {
                        this.pic_url = pic_url;
                    }

                    public String getPos() {
                        return pos;
                    }

                    public void setPos(String pos) {
                        this.pos = pos;
                    }

                    public String getTopic_name() {
                        return topic_name;
                    }

                    public void setTopic_name(String topic_name) {
                        this.topic_name = topic_name;
                    }

                    public String getTopic_url() {
                        return topic_url;
                    }

                    public void setTopic_url(String topic_url) {
                        this.topic_url = topic_url;
                    }
                }
            }
        }
    }
}
