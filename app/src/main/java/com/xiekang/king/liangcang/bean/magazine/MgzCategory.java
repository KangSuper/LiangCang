package com.xiekang.king.liangcang.bean.magazine;

import java.util.List;

/**
 * Created by King on 2016/9/13.
 */
public class MgzCategory {

    /**
     * has_more : false
     * num_items : 1
     * items : [{"cat_id":"21","cat_name":"趣物","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194442_18118.jpg"},{"cat_id":"20","cat_name":"数码","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194452_90248.jpg"},{"cat_id":"18","cat_name":"汽车","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194501_78040.jpg"},{"cat_id":"11","cat_name":"文化","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194514_19249.jpg"},{"cat_id":"15","cat_name":"时尚","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194523_80496.jpg"},{"cat_id":"9","cat_name":"美食","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194533_20710.jpg"},{"cat_id":"19","cat_name":"建筑","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194555_43195.jpg"},{"cat_id":"16","cat_name":"空间","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194543_94581.jpg"},{"cat_id":"7","cat_name":"圈子","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194603_36960.jpg"},{"cat_id":"12","cat_name":"清单","thumb":"http://www.iliangcang.com/ware/upload/app/20150901/20150901194611_20810.jpg"},{"cat_id":"23","cat_name":"视频","thumb":"http://www.iliangcang.com/ware/upload/app/20151227/20151227201723_90708.jpg"},{"cat_id":"24","cat_name":"活动","thumb":"http://www.iliangcang.com/ware/upload/app/20160215/20160215114158_49650.jpg"}]
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
        /**
         * cat_id : 21
         * cat_name : 趣物
         * thumb : http://www.iliangcang.com/ware/upload/app/20150901/20150901194442_18118.jpg
         */

        private List<ItemsBean> items;

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

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private String cat_id;
            private String cat_name;
            private String thumb;

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }
}
