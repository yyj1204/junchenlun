package com.wktx.www.emperor.utils;

/**
 * Created by yyj on 2018/01/18 .
 * 常量工具类
 */

public class ConstantUtil {

    public static final String TOAST_NONET= "请检查网络!";
    public static final String TOAST_ERROR= "请求出错了~";
    //退出登录弹窗
    public static final String LOGOUT = "退出";
    //修改公司logo弹窗
    public static enum Type {
        PHONE, CAMERA
    }
    //SharedPreferenceUtil
    public static final String HEADBASE64_NAME = "headBase64_Name";
    public static final String HEADBASE64STR_KEY = "headBase64Str";
    //请求码
    public static final int REQUESTCODE_SCREENING = 0;//招聘检索条件筛选
    public static final int REQUESTCODE_UPDATENAME = 0;//更换公司名称

    //结果码
    public static final int RESULTCODE_SCREENING = 0;//招聘检索条件筛选
    public static final int RESULTCODE_UPDATENAME = 0;//更换公司名称

    //intent
    public static final String  KEY_POSITION= "position";//序号
    public static final String  KEY_DATA= "data";//数据




    /**
     * ---------------------------------没用----------------------------------------
     */
    public static final class RESULT {
        public static final int QQ = 3;
        public static final int WEIXIN = 4;
        public static final int SINA = 5;
    }
    public static final int REQUESTCODE_LOGIN = 1;
    public static final int REQUESTCODE_REPLYLIST = 2;
    public static final int REQUESTCODE_RELEASE = 3;
    public static final int REQUESTCODE_ADDRESSEDIT = 4;
    public static final int REQUESTCODE_ADDRESSEADD = 5;
    public static final int RESULTCODE_ADDOREDIT = 5;
    public static final int RESULTCODE_RELEASE = 1;
    public static final int RESULTCODE_CANCELED = 0;
    public static final int RESULT_LOGINCODE = 1;
    public static final int RESULT_OUTLOGINCODE = 2;

    public static final int REQUESTCODE_UPDATEADDRESS = 1;
    public static final int REQUESTCODE_NOACTION = 0;
    public static final int REQUESTCODE_GOODSDETAIL = 0;
    public static final int RESULT_GOODSDETAIL = 4;

    public static final int WAITPAYCODE = 1;
    public static final int WAITSENDCODE = 2;
    public static final int WAITRECEIVECODE = 3;
    public static final int FINISHCODE = 4;
    public static final int CANCELCODE = 5;
    public static final int WAITCCOMMENTCODE = 6;
    public static final int CANCELLEDCODE = 7;

    public static final String PAGE_GOODSDETAIL = "goodsDetail";
    public static final String PAGE = "PAGE";
    public static final String REQUESTCODE = "requestCode";
    public static final String LOGIN_RESULTCODE = "loginresultcode";
    public static final String LOGININFO = "logininfo";
    public static final String LOGININFO_OBJECT = "logininfoobject";
    public static final String SENDCODE = "sendCode";
    public static final String CHECKCODE = "checkCode";
    public static final String UPDATEINFOSEX = "sex";
    public static final String UPDATEHEAD = "updateHead";
    public static final String GETUSERINFO = "getUserInfo";
    public static final String GETGUESSFAVORINFO = "getGuessFavorInfo";
    public static final String SUBMITREGIEST = "submitRegiest";
    public static final String INTENT_IDTOKEN = "intent_id_token";
    public static final String ISLOGIN = "islogin";
    public static final String SP_ISLOGIN = "sp_islogin";

    public static final String THREADSTATE = "threadState";
    public static final String THREADSTATE_NAME = "threadState_Name";

    public static final String HOMEINFO_NAME = "homeInfo_Name";
    public static final String USERINFO_NAME = "userInfo_Name";
    public static final String HOMEINFO = "homeInfo";
    public static final String NICKTEXT = "nickText";
    public static final String CANCEL = "CANCEL";
    public static final String WAITPAY = "WAITPAY";
    public static final String WAITSEND = "WAITSEND";
    public static final String WAITRECEIVE = "WAITRECEIVE";
    public static final String FINISH = "FINISH";
    public static final String CANCELLED = "CANCELLED";
    public static final String WAITCCOMMENT = "WAITCCOMMENT";
    public static final String RECEIVESURE = "receiveSure";
    public static final String CACCLESURE = "cancleSure";
    public static final String WAITCOMMENT = "waitComment";
    public static final String SHAKESAVETICKET = "saveTicket";
    public static final String UPDATEDEFAULTADRESS = "updateDefaultAdress";
    public static final String DELETERECEIVEADRESS = "deleteReceiveAdress";
    public static final String GETRECEIVEADRESSLIST = "getReceiveAdressList";
    public static final String RECEIVEADDRESSITEM = "receiveAddressItem";
    public static final String UPDATETHUMBUP = "updateThumbUp";
    public static final String UPDATEATTENTION = "updateAttention";
    public static final String GETMINESTATEHV = "getMineStateHv";
    public static final String GETMINESTATELIST = "getMineStateList";
    public static final String ADDOREDIT = "addOrEdit";
    public static final String STATEGETSClIST = "getScList";
    public static final String STATEEDITSCGOODCOUNT = "stateEditScGoodsCount";
    public static final String STATESHOPPINGGOODS = "stateShoppingGoods";
    public static final String STATESHIPPING = "stateShipping";
    public static final String STATECHECKORDER = "stateCheckOrder";
    public static final String STATEGETCOLLECTLIST = "stateGetCollectList";
    public static final String STATEDELFAVOR = "stateDelFavor";
    public static final String STATEGETDESIGNLIST = "stateGetDesignList";
    public static final String STATEDELDESIGN = "stateDelDesign";
    public static final String STATEOUTLOGIN = "stateOutLogin";
    public static final String STATELOGINWEIXIN = "stateLoginWeixin";
    public static final String STATELOGINIPHONE = "stateLoginIphone";
    public static final String STATEORDERDETAIL = "stateOrderDetail";
    public static final String SUREORDERADRESS = "sureOrderAdress";
    public static final String PAGE_FAN = "pageFan";
    public static final String PAGE_FOLLOW = "pageFollow";
    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String OUT = "退出";
    public static final String ORDER_ID = "orderId";
    public static final String GOODS_ID = "goods_id";
    public static final String REC_ID = "rec_id";
    public static final String STATEDETAIL = "stateDetail";
    public static final String STATEINFO = "stateinfo";
    public static final String UID = "uid";
    public static final String ID = "id";
    public static final String NICKNAME = "nickName";
    public static final String MINESTATE = "mineState";
    public static final String LISTNAME = "listName";
    public static final String ARTICLE_ID = "article_id";
    public static final String SUREORDER = "sureOrder";
    public static final String ADRESSINFO = "adressInfo";
    public static final String PAYORDERINFO = "payOrderInfo";
    public static final String ADRESSINFOWAITPAY = "adressInfoWaitPay";
    public static final String SETTLEMENTINFODATA = "settlementInfoData";
    public static final String ODDETAILDATAINFO = "odDetailDataInfo";
    public static final String WAITSTATEINFODATAINFO = "WaitStateInfoDataInfo";
    public static final String ISCUSTOM = "isCustom";
    public static final String ALIPAY = "alipay";
    public static final String WXPAY = "wxpay";
    public static final String PRICE = "price";
    public static final String ISREDESIGN = "isReDesign";
    public static final String APP_ID = "wx46c0c2a1b9188bf6";

//    public static final int USER_ID = 2594;
//    public static final String TOKEN = "B6BEDE4F7096EE637EBD1041E5296789334486814B05943FD98408559ABBF6DB";

    public static final String URL_SHOP = "http://shop.xf.readite.cn";
    public static final String URL_NAME = "http://api.xf.readite.cn/api/index.php?service=";
    //    public static final String URL_SHOP = "http://shop.xf.iiillu.com";
//    public static final String URL_NAME = "http://api.xf2.iiillu.com/api/index.php?service=";
    //登录注册个人信息
    public static final String URL_CHECKCODE = URL_NAME + "SendSMS.CheckCode";
    public static final String URL_SENDCODE = URL_NAME + "SendSMS.SendCodeSMS";
    public static final String URL_SUBMIT_REGIEST = URL_NAME + "User_User_Login.Phone";
    public static final String URL_SUBMIT_LOGIN = URL_NAME + "User_User_Login.Phone";
    //    public static final String URL_GETUSERINFO = URL_NAME + "User_User_Info.GetUserInfo";
    public static final String URL_GETUSERINFO = URL_NAME + "Center.Index";
    public static final String URL_EDITUSERINFO = URL_NAME + "User_User_Info.EditUserInfo";
    public static final String URL_UPDATEHEAD = URL_NAME + "Upload.UploadImgForBase64";
    public static final String URL_USER_USER_INFO_FORGETPASSWORD = URL_NAME + "User_User_Info.ForgetPassword";
    //个人中心
    public static final String URL_GUESSFAVOR = URL_NAME + "Goods.GetGuessYouLike";
    public static final String URL_FINISH = URL_NAME + "Center.OrderList";
    public static final String URL_WAITRECEIVE = URL_NAME + "Center.WaitReceive";
    public static final String URL_WAITPAY = URL_NAME + "Center.OrderList";
    public static final String URL_WAITSEND = URL_NAME + "Center.OrderList";
    public static final String URL_ALLORDER = URL_NAME + "Center.OrderList";
    public static final String URL_WAITRETURN = URL_NAME + "Center.ReturnGoodsList";
    public static final String URL_CANCLEORDER = URL_NAME + "Center.CancelOrder";
    public static final String URL_ADRESSLIST = URL_NAME + "Center.AddressList";
    public static final String URL_ADDADRESS = URL_NAME + "Center.AddAddress";
    public static final String URL_DELADRESS = URL_NAME + "Center.DelAddress";
    public static final String URL_EDITADRESS = URL_NAME + "Center.EditAddress";
    public static final String URL_EXPRESS = URL_NAME + "Center.Express";
    public static final String URL_POINTS = URL_NAME + "Center.Points";
    public static final String URL_SETDEFAULTADRESS = URL_NAME + "Center.SetDefultAddress";
    public static final String URL_GOODSCOLLECT = URL_NAME + "Center.GoodsCollect";
    public static final String URL_WAICOMMENT = URL_NAME + "Center.Comment";
    public static final String URL_RECEIVESURE = URL_NAME + "Center.ConfirmOrder";
    public static final String URL_ORDERDETAIL = URL_NAME + "Center.OrderDetail";
    public static final String URL_TICKET = URL_NAME + "Center.MyCardCoupon";
    public static final String URL_CENTER_ADDCOMENT = URL_NAME + "Center.AddComment";
    public static final String URL_CENTER_MESSAGENOTIC = URL_NAME + "Center.MessageNotic";
    public static final String URL_GOODS_DELCOLLECT = URL_NAME + "Goods.DelCollect";
    public static final String URL_ARTICLE_GETARTICLEINFO = URL_NAME + "Article.GetArticleInfo";
    public static final String URL_USER_USER_INFO_LOGOUT = URL_NAME + "User_User_Info.Logout";
    public static final String URL_USER_USER_LOGIN_WEIXIN = URL_NAME + "User_User_Login.Weixin";
    public static final String URL_CUSTOMIZED_MYCUSTOMIZED = URL_NAME + "Customized.MyCustomized";
    public static final String URL_CUSTOMIZED_DELMYCUSTOMIZED = URL_NAME + "Customized.DelMyCustomized";
    public static final String URL_CENTER_GETCUSTOMERSERVICEINFO = URL_NAME + "Center.GetCustomerServiceInfo";
    //摇一摇
    public static final String URL_SHAKE = URL_NAME + "Shake.ShakeShake";
    public static final String URL_SHAKE_CHECKNUM = URL_NAME + "Shake.CheckShakeNum";
    public static final String URL_SHAKE_SAVETICKET = URL_NAME + "Shake.SaveCardCoupon";
    //发现
    public static final String URL_DYNAMIC_INDEXN = URL_NAME + "Dynamic.Index";
    public static final String URL_DYNAMIC_THUMBUP = URL_NAME + "Dynamic.ThumbUp";
    public static final String URL_DYNAMIC_FOLLOW = URL_NAME + "Dynamic.Follow";
    public static final String URL_DYNAMIC_FOLLOWDYNAMICLIST = URL_NAME + "Dynamic.FollowDynamicList";
    public static final String URL_DYNAMIC_DYNAMICCENTER = URL_NAME + "Dynamic.DynamicCenter";
    public static final String URL_DYNAMIC_OTHERSDYNAMIC = URL_NAME + "Dynamic.OthersDynamic";
    public static final String URL_DYNAMIC_GETFOLLOWMELIST = URL_NAME + "Dynamic.GetFollowMeList";
    public static final String URL_DYNAMIC_REPLYDYNAMIC = URL_NAME + "Dynamic.ReplyDynamic";
    public static final String URL_DYNAMIC_DYNAMICREPLYLIST = URL_NAME + "Dynamic.DynamicReplyList";
    public static final String URL_DYNAMIC_GETFOLLOWOTHERLIST = URL_NAME + "Dynamic.GetFollowOthersList";
    public static final String URL_DYNAMIC_GETREPLYMESSAGECOUNT = URL_NAME + "Dynamic.GetReplyMessageCount";
    public static final String URL_DYNAMIC_MYDYNAMIC = URL_NAME + "Dynamic.MyDynamic";
    public static final String URL_DYNAMIC_ADDDYNAMIC = URL_NAME + "Dynamic.AddDynamic";
    public static final String URL_DYNAMIC_REPLYMESSAGE = URL_NAME + "Dynamic.ReplyMessage";
    //购物车
    public static final String URL_SHOPPINGCARTLIST = URL_NAME + "ShoppingCart.ShoppingCart";
    public static final String URL_GOODS_COLLECTGOODS = URL_NAME + "Goods.CollectGoods";
    public static final String URL_SHOPPINGCART_MOVETOCOLLECT = URL_NAME + "ShoppingCart.MoveToCollect";
    public static final String URL_SHOPPINGCART_DELSHOPPINGCART = URL_NAME + "ShoppingCart.DelShoppingCart";
    public static final String URL_SHOPPINGCART_EDITSHOPPINGCART = URL_NAME + "ShoppingCart.EditShoppingCart";
    public static final String URL_SHOPPING_SHOPPINGGOODS = URL_NAME + "Shopping.ShoppingGoods";
    public static final String URL_SHOPPING_CHECKORDER = URL_NAME + "Shopping.CheckOrder";
    public static final String URL_SHOPPING_PAYORDER = URL_NAME + "Shopping.PayOrder";
    public static final String URL_NOTIFY_FUQIANLA = URL_NAME + "Notify.FuQianLa";
    public static final String URL_SHOPPING_CHECKORDERSTATUS = URL_NAME + "Shopping.CheckOrderStatus";
    //首页
    public static final String URL_HOME_INDEX = URL_NAME + "Home.Index";
    public static final String URL_GOODSCATEGORY_GETCATEGORYLIST = URL_NAME + "GoodsCategory.GetCategoryList";
    public static final String URL_GOODSCATEGORY_GETCATEGORYGOODSLIST = URL_NAME + "GoodsCategory.GetCategoryGoodsList";
    public static final String URL_Customized_Index = URL_NAME + "Customized.Index";
    public static final String URL_GOODS_GOODSINFO = URL_NAME + "Goods.GoodsInfo";
    public static final String URL_CUSTOMIZED_REDESIGN = URL_NAME + "Customized.Redesign";
    public static final String URL_CENTER_RETURNSHAREURL = URL_NAME + "Center.ReturnShareUrl";
}
