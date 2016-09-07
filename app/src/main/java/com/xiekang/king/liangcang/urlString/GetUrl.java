package com.xiekang.king.liangcang.urlString;

/**
 * Created by King on 2016/9/7.
 *链接
 */
public class GetUrl {

    /**
     * 商城的链接
     */

    //分类
    public static final String CATAGORY_URL = "http://mobile.iliangcang.com/goods/goodsCategory?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //品牌
    public static final String CATAGORY_BRAND_URL = "mobile.iliangcang.com/brand/brandList?app_key=Android&count=20&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //首页
    public static final String CATAGORY_HOME_URL = "http://mobile.iliangcang.com/goods/shopHome?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //专题
    public static final String CATAGORY_TOPIC_URL = "mobile.iliangcang.com/goods/shopSpecial?app_key=Android&count=10&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //搜索 输入关键字
    public static String getCatagorySearchUrl(String keyword) {
        return "http://mobile.iliangcang.com/goods/search?app_key=Android&count=10&is_outter=0&keyword=" +
                keyword +
                "&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    /**
     * 杂志的链接
     */
    //杂志主页
    public static final String MAGEZINE_URL = "http://mobile.iliangcang.com/topic/magazineList?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //杂志作者
    public static final String MGZ_AUTHOR_URL = "http://mobile.iliangcang.com/topic/magazineAuthorList?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //杂志分类
    public static final String MGZ_CATEGORY_URL = "http://mobile.iliangcang.com/topic/magazineCatList?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";


   /**
    * 达人
    */
    //默认排序
    public static final String ORDER_DEFAULT_URL = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //最多排序
    public static final String ORDER_SUM_URL = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=goods_sum&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //最受欢迎
    public static final String ORDER_FOLLWER_URL = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=followers&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //最新排序
    public static final String ORDER_ACTION_URL = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=action_time&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //最新加入
    public static final String ORDER_TIME_URL = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=reg_time&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //搜索
    public static final String ORDER_SEARCH_URL = "http://mobile.iliangcang.com/user/search?app_key=Android&count=18&keyword=11&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";


    /**
     * 分享
     */
    //全部分享
    public static final String SHARE_ALL_URL = "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&count=10&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&type=0&v=1.0";
    //商店分享
    public static final String SHARE_SHOP_URL = "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&count=10&page=1&self_host=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    //分类分享
    public static String getShareCategoryUrl(int type) {
        return "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&count=10&page=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&type=" +
                type +
                "&v=1.0";
    }
}
