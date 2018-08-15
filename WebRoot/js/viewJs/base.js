var base = {
    companyId : "37096FDBAC854830" || "",
    appCode: "QMTNRK_YUNSHI",
    //图片压缩参数
    img160 : "?x-oss-process=image/resize,w_160/auto-orient,1",
    img240 : "?x-oss-process=image/resize,w_240/auto-orient,1",
    img320 : "?x-oss-process=image/resize,w_320/auto-orient,1",
    img540 : "?x-oss-process=image/resize,w_540/auto-orient,1",
    //域名
    apiDomainName : "http://114.116.66.108:8081/BTVManage/api/",
    // 打开环境
    viewType : "other",
    //上传服务地址
    uploadApi : "https://demo-mty.yunshicloud.com/rms-upload/",
    // 默认头像
    defaultImg : "img/default_img.png",
    /**
     * @returns 获取用户信息
     */
    userInfo : function() {
        var userInfo = localStorage.getItem(this.userCacheId);
        if (!userInfo) {
            userInfo = {
                "isLogin" : "0",
                "openid" : OnairJsUtilNew.guid(),
                "headimgurl" : this.defaultHead,
                "nickname" : "游客" + OnairJsUtilNew.guid().substring(2, 7)
            };
            localStorage.setItem(this.userCacheId, JSON.stringify(userInfo));
        }
        if (typeof userInfo == "string") {
            userInfo = JSON.parse(userInfo);
        }
//		userInfo.isLogin = 1;
        return userInfo;
    },
    /**
     * 生成访问详情的地址
     * @param docid
     *    文稿id
     * @returns {String}
     */
    generateDetailUrl: function (item) {
        sessionStorage.setItem("goBack",window.location.href);
        //判断是否有外链
        var url = "detail.html?&companyId=COMPANYID&docid=DOCID&title=TITLE";
        if(item.h5type && item.h5type == 1 && item.h5contexturl){
            url = item.h5contexturl;
        }else{
            url = url.replace("COMPANYID", this.companyId).replace("DOCID", item.docid).replace("TITLE", item.title);
        }
        return url;
    },
    /**
     * 重庆活动的活动请求地址公共参数
     * @param url
     * 	请求后台的地址
     * @param commonParam
     * 	请求的公共参数
     * @returns
     */
    ugcRequestUrl : function(url,commonParam){
        url = url || "";
        //设置默认参数
        var commonParam = $.extend({
            "companyId":this.companyId,
            "appCode":"weixin",
            "userId":this.userInfo().openid,
            "serviceCode":"weixin",
            "versionId":"v1"
        },commonParam);
        //替换公共参数
        var commonPath = "companyId/appCode/userId/serviceCode/versionId/",
            commonPath = commonPath.replace("companyId",commonParam.companyId)
                .replace("appCode",commonParam.appCode)
                .replace("userId",commonParam.userId)
                .replace("serviceCode",commonParam.serviceCode)
                .replace("versionId",commonParam.versionId);
        return this.ugcApi + commonPath + url;
    },
    /**
     * 生成请求后台的参数
     * @param url
     *    请求后台的地址
     * @param commonParam
     *    请求的公共参数
     * @returns
     */
    requestUrl: function (url, commonParam) {
        url = url || "";
        //设置默认参数
        var commonParam = $.extend({
            "companyId": this.companyId,
            "appCode": "weixin",
            "userId": this.userInfo().openid,
            "serviceCode": "weixin",
            "versionId": "v1"
        }, commonParam);
        //替换公共参数
        var commonPath = "lhapi/appCode/versionId/companyId/userId/serviceCode/v1/api/",
            commonPath = commonPath.replace("companyId", commonParam.companyId)
                .replace("appCode", commonParam.appCode)
                .replace("userId", commonParam.userId)
                .replace("serviceCode", commonParam.serviceCode)
                .replace("versionId", commonParam.versionId);
        return this.apiDomainName + commonPath + url;
    },
    /**
     * 是否是第一眼app
     * ture:是,false:否
     */
    isApp : function(){
        var ua = navigator.userAgent.toLowerCase();
        if (/iphone|ipad|ipod/.test(ua)) {
            try {
                window.webkit.messageHandlers.sendSpecialAction.postMessage("isApp");
                return true;
            } catch (e) {

            }
        } else if (/android/.test(ua)) {
            try {
                objFirsteye.sendSpecialAction("isApp");
                return true;
            } catch (e) {

            }
        }
        return false;
    }
};

/**
 * app传用户信息
 * @param isLogin 1:登录状态，0：未登录状态
 * @param userId
 * @param headImgUrl
 * @param nickName
 */
function loginUser(isLogin,userId,headImgUrl,nickName) {
    var user = {
        "isLogin" : 0,
        "openid":OnairJsUtilNew.guid(),
        "headimgurl": base.defaultHead,
        "nickname":"游客" + OnairJsUtilNew.guid().substring(2, 7)
    };
    if(isLogin){
        user.isLogin = isLogin;
    }
    if(userId && userId != null && $.trim(userId) != "") {
        user.openid = userId;
    }
    if(headImgUrl && headImgUrl != null && $.trim(headImgUrl) != "") {
        user.headimgurl = headImgUrl;
    }
    if(nickName && nickName != null && $.trim(nickName) != "") {
        user.nickname = nickName;
    }

    localStorage.setItem(base.userCacheId,JSON.stringify(user));
    if(isLogin == 1){
        $(".lopopup").addClass("hide");
    }
}


$(function(){
    //关闭分享
    $(".share_zdl").click(function () {
        $(".share").addClass("hide");
        $(".j_main_video_wrap video").removeClass("hide");
    });
});
