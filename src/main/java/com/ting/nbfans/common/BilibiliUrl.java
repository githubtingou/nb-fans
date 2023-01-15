package com.ting.nbfans.common;


public interface BilibiliUrl {


    /**
     * 获取粉丝数
     */
    String FANS_URL_FORMAT = "https://api.bilibili.com/x/relation/stat?vmid=%s&jsonp=jsonp";

    /**
     * 获取用户信息
     */
    String USER_INFO_FORMAT = "https://api.bilibili.com/x/space/wbi/acc/info?mid=%s";
}
