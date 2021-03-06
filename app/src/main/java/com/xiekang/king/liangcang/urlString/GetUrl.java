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
    public static final String SHOP_CATAGORY_URL = "http://mobile.iliangcang.com/goods/goodsCategory?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";


    /**
     * 分类详情
     * @param id 商品id
     * @param page 分页加载
     * @param order 排序条件 默认为null
     * @return
     */
    public static String getShopDetailsUrl(String id,int page,String order){
        return "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=0" +
                id +
                "&count=10&coverId=1&" +
                order +
                "page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //品牌
    public static String getShopBrandUrl(int page) {
        return "http://mobile.iliangcang.com/brand/brandList?app_key=Android&count=20&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }


    public static String getShopBraDetailsUrl(String id,int page){
        return "http://mobile.iliangcang.com/brand/brandShopList?app_key=Android&brand_id=" +
                id +
                "&count=20&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //首页
    public static final String SHOP_HOME_URL = "http://mobile.iliangcang.com/goods/shopHome?app_key=Android&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";

    //专题
    public static String getShopTopicUrl(int page) {
        return "http://mobile.iliangcang.com/goods/shopSpecial?app_key=Android&count=10&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    public static String getShopGiftUrl(String id,int page,String params){
        return "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=" +
                id +
                "&" +
                params +
                "page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //搜索 输入关键字
    public static String getShopSearchUrl(String keyword,int page) {
        return "http://mobile.iliangcang.com/goods/search?app_key=Android&count=10&is_outter=0&keyword=" +
                keyword +
                "&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
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
    //杂志选择
    public static String getMgzSelectUrl(String category,String id){
        return "http://mobile.iliangcang.com/topic/magazineList?app_key=Android&" +
                category +
                "=" +
                id +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
        //http://mobile.iliangcang.com/topic/magazineList?app_key=Android&cat_id=21&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0
    }

   /**
    * 达人
    */
    //默认排序
    public static String getOrderDefaultUrl(int page) {
        return "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //最多排序
    public static String getOrderSumUrl(int page) {
        return "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=goods_sum&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //最受欢迎
    public static String getOrderFollwerUrl(int page) {
        return "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=followers&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //最新排序
    public static String getOrderActionUrl(int page) {
        return "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=action_time&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //最新加入
    public static String getOrderTimeUrl(int page) {
        return "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=reg_time&page=" +
                page  +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //搜索
    public static String getTalentSearchUrl(String keyWord,int page) {
        return "http://mobile.iliangcang.com/user/search?app_key=Android&count=18&keyword=" +
                keyWord +
                "&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    /**
     * 分享
     */
    //全部分享
    public static String getShareAllUrl(int page) {
        return "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&count=10&page=" +
                page +
                "&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&type=0&v=1.0";    }

    //商店分享
    public static String getShareShopUrl(int page) {
        return "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&count=10&page=" +
                page +
                "&self_host=1&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }

    //分类分享
    public static String getShareCategoryUrl(int type,int page) {
        return "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&count=10&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&type=" +
                type +"&page="+page+
                "&v=1.0";
    }
    //商品详情
    public static String getGoodsDetail(String id){
        return "http://mobile.iliangcang.com" +
                "/goods/goodsDetail?app_key=Android&sig=5693B044D5F961D25489767100FA6553%7C731720001493468&v=1.0&goods_id="+id;
    }
    //商品评论
    public static String getGoodsComments(String id,int page){
        return "http://mobile.iliangcang.com" +
                "/comments/goods?app_key=Android&count=3&sig=5693B044D5F961D25489767100FA6553%7C731720001493468&v=1.0&goods_id="+id+"&page="+page;
    }

    /**
     * 用户信息
     */
    //喜欢
    public static String getUserLike(String id,int page){
        return " http://mobile.iliangcang.com" +
                "/user/masterLike?app_key=Android&count=10&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0&owner_id="+id+"&page="+page;
    }
    //推荐
    public static String getUserTuijian(String id,int page){
        return "  http://mobile.iliangcang.com" +
                "/user/masterListInfo?app_key=Android&count=10&owner_id="+id+"&page="+page+"&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }
    //关注
    public static String getUserFollow(String id,int page){
        return "http://mobile.iliangcang.com" +
                "/user/masterFollow?app_key=Android&count=12&owner_id="+id+"&page="+page+"&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }
    //粉丝
    public static String getUserFans(String id,int page){
        return "http://mobile.iliangcang.com" +
                "/user/masterFollowed?app_key=Android&count=12&owner_id="+id+"&page="+page+"&sig=6D569443F5A6EB51036D09737946AC2A%7C002841520425331&v=1.0";
    }
}
