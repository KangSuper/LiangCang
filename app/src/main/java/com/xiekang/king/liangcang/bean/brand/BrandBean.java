package com.xiekang.king.liangcang.bean.brand;

import java.util.List;

/**
 * Created by King on 2016/9/8.
 */
public class BrandBean {
    /**
     * has_more : true
     * num_items : 483
     * items : [{"brand_id":14,"brand_name":"Maison Martin Margiela Line 13","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/14.jpg"},{"brand_id":11,"brand_name":"sowhat","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/11.jpg"},{"brand_id":561,"brand_name":"慢作worktop","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/561.jpg"},{"brand_id":560,"brand_name":"香投","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/560.jpg"},{"brand_id":559,"brand_name":"Inuk","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/559.jpg"},{"brand_id":558,"brand_name":"IF如果","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/558.jpg"},{"brand_id":557,"brand_name":"糯言","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/557.jpg"},{"brand_id":556,"brand_name":"雅昌艺术图书","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/556.jpg"},{"brand_id":555,"brand_name":"Beats","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/555.jpg"},{"brand_id":554,"brand_name":"好东西","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/554.jpg?t=1472182665"},{"brand_id":553,"brand_name":"若可","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/553.jpg?t=1472116405"},{"brand_id":551,"brand_name":"花治","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/551.jpg"},{"brand_id":550,"brand_name":"几行","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/550.jpg"},{"brand_id":549,"brand_name":"弥若间MiroMiro","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/549.jpg"},{"brand_id":548,"brand_name":"修羅場TATTOO","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/548.jpg"},{"brand_id":547,"brand_name":"本土创造","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/547.jpg"},{"brand_id":546,"brand_name":"True Grace","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/546.jpg"},{"brand_id":545,"brand_name":"ROSY RINGS","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/545.jpg"},{"brand_id":544,"brand_name":"OMNES","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/544.jpg"},{"brand_id":543,"brand_name":"HOMI","brand_logo":"http://imgs-qn.iliangcang.com/ware/brand/543.jpg"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brand_id : 14
         * brand_name : Maison Martin Margiela Line 13
         * brand_logo : http://imgs-qn.iliangcang.com/ware/brand/14.jpg
         */

        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private int brand_id;
            private String brand_name;
            private String brand_logo;

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getBrand_logo() {
                return brand_logo;
            }

            public void setBrand_logo(String brand_logo) {
                this.brand_logo = brand_logo;
            }
        }
    }
}
