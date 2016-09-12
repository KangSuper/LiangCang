package com.xiekang.king.liangcang.bean.detail;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class GoodsComments {
    /**
     * status : 0
     * server_time : 2016-09-09 16:41:42
     * account_id : 0
     * cost : 0.007828
     * errdata : null
     * errmsg :
     */

    private MetaBean meta;
    /**
     * meta : {"status":0,"server_time":"2016-09-09 16:41:42","account_id":0,"cost":0.007828,"errdata":null,"errmsg":""}
     * version : 1
     * data : {"has_more":false,"num_items":2,"items":[{"comment_id":8649,"parent_uid":0,"parent_user_name":"","parent_user_image":"","parent_id":0,"goods_id":254496,"user_id":1000020766,"user_name":"威廉","is_daren":"0","user_image":"http://imgs-qn.iliangcang.com/images/default/default180.png","msg":"不错","create_time":"2016-09-06 23:22:53"},{"comment_id":8648,"parent_uid":0,"parent_user_name":"","parent_user_image":"","parent_id":0,"goods_id":254496,"user_id":709275315,"user_name":"709275315","is_daren":"0","user_image":"http://imgs-qn.iliangcang.com/images/default/default180.png","msg":"很漂亮，看起来很有食欲","create_time":"2016-09-06 12:15:56"}]}
     */

    private int version;
    /**
     * has_more : false
     * num_items : 2
     * items : [{"comment_id":8649,"parent_uid":0,"parent_user_name":"","parent_user_image":"","parent_id":0,"goods_id":254496,"user_id":1000020766,"user_name":"威廉","is_daren":"0","user_image":"http://imgs-qn.iliangcang.com/images/default/default180.png","msg":"不错","create_time":"2016-09-06 23:22:53"},{"comment_id":8648,"parent_uid":0,"parent_user_name":"","parent_user_image":"","parent_id":0,"goods_id":254496,"user_id":709275315,"user_name":"709275315","is_daren":"0","user_image":"http://imgs-qn.iliangcang.com/images/default/default180.png","msg":"很漂亮，看起来很有食欲","create_time":"2016-09-06 12:15:56"}]
     */

    private DataBean data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MetaBean {
        private int status;
        private String server_time;
        private int account_id;
        private double cost;
        private Object errdata;
        private String errmsg;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getServer_time() {
            return server_time;
        }

        public void setServer_time(String server_time) {
            this.server_time = server_time;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public Object getErrdata() {
            return errdata;
        }

        public void setErrdata(Object errdata) {
            this.errdata = errdata;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }
    }

    public static class DataBean {
        private boolean has_more;
        private int num_items;
        /**
         * comment_id : 8649
         * parent_uid : 0
         * parent_user_name :
         * parent_user_image :
         * parent_id : 0
         * goods_id : 254496
         * user_id : 1000020766
         * user_name : 威廉
         * is_daren : 0
         * user_image : http://imgs-qn.iliangcang.com/images/default/default180.png
         * msg : 不错
         * create_time : 2016-09-06 23:22:53
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
            private int comment_id;
            private int parent_uid;
            private String parent_user_name;
            private String parent_user_image;
            private int parent_id;
            private int goods_id;
            private int user_id;
            private String user_name;
            private String is_daren;
            private String user_image;
            private String msg;
            private String create_time;

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getParent_uid() {
                return parent_uid;
            }

            public void setParent_uid(int parent_uid) {
                this.parent_uid = parent_uid;
            }

            public String getParent_user_name() {
                return parent_user_name;
            }

            public void setParent_user_name(String parent_user_name) {
                this.parent_user_name = parent_user_name;
            }

            public String getParent_user_image() {
                return parent_user_image;
            }

            public void setParent_user_image(String parent_user_image) {
                this.parent_user_image = parent_user_image;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getIs_daren() {
                return is_daren;
            }

            public void setIs_daren(String is_daren) {
                this.is_daren = is_daren;
            }

            public String getUser_image() {
                return user_image;
            }

            public void setUser_image(String user_image) {
                this.user_image = user_image;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
