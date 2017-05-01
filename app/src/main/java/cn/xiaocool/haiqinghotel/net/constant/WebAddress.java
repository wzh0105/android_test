package cn.xiaocool.haiqinghotel.net.constant;

/**
 * Created by wzh on 2016/5/7.
 */
public interface WebAddress extends NetBaseConstant {
    /**
     * 发送验证码后缀
     */
    String SEND_CODE = NET_BASE_PREFIX + "a=SendMobileCode";
    /**
     * 登录后缀
     */
    String LOGIN = NET_BASE_PREFIX + "a=applogin";
    /**
     * 注册后缀
     */
    String REGISTER = NET_BASE_PREFIX + "a=AppRegister";
    /**
     * 修改密码
     */
    String CHANGE_PASS = NET_BASE_PREFIX + "a=forgetpwd";
    /**
     * 首页促销列表
     */
    String ONSALE_LIST = NET_BASE_PREFIX + "a=getpromotionlist";
    /**
     * 房间详情
     */
    String ROOM_DETAILS = NET_BASE_PREFIX + "a=showbedroominfo";
    /**
     * 立即预定房间
     */
    String RESERVE_ROOM_NOW = NET_BASE_PREFIX + "a=bookingroom";
    /**
     * 获取餐饮信息
     */
    String CATERING_DETAILS = NET_BASE_PREFIX + "a=showcateringinfo";
    /**
     * 预订餐饮
     */
    String RESERVE_CATERING = NET_BASE_PREFIX + "a=bookingcatering";

    /**
     * 获取首页预订房间列表
     */
    String HOME_RESERVE_ROOM = NET_BASE_PREFIX + "a=getbedroomlist";

    /**
     * 获取我的房间订单
     */
    String MINE_ROOM_ORDER = NET_BASE_PREFIX + "a=getbookingorderlist";
    /**
     * 获取我的餐饮订单
     */
    String MINE_CATERING_ORDER = NET_BASE_PREFIX + "a=getcateringorderlist";
    /**
     * 获取我的餐饮订单
     */
    String MINE_SHOP_ORDER = NET_BASE_PREFIX + "a=getshoppingorderlist";
    /**
     * 获取设施列表
     */
    String FACILITY_LIST = NET_BASE_PREFIX + "a=getFacilitytypelist";
    /**
     * 获取设施列表
     */
    String FACILITY_CLICK_LIST = NET_BASE_PREFIX + "a=getFacilitylist";
    /**
     * 获取商城列表
     */
    String SHOP_LIST = NET_BASE_PREFIX + "a=getshoppinglist";

}
