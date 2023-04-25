package com.ting.nbfans.common;


import java.time.format.DateTimeFormatter;

public interface BilibiliUrl {


    /**
     * 获取粉丝数
     */
    String FANS_URL_FORMAT = "https://api.bilibili.com/x/relation/stat?vmid=%s&jsonp=jsonp";

    /**
     * 获取用户信息
     */
    String USER_INFO_FORMAT = "https://api.bilibili.com/x/space/wbi/acc/info?mid=%s";

    /**
     * 获取舰长数量
     * <p>
     * roomid:直播间id
     * ruid:uid
     */
    String LIVE_INFO_FORMAT = "https://api.live.bilibili.com/xlive/app-room/v2/guardTab/topList?roomid=%s&page=1&ruid=%s&page_size=1";

    /**
     * 粉丝团数量
     */
    String FAN_GROUP_URL_FORMAT = "https://api.live.bilibili.com/xlive/general-interface/v1/rank/getFansMembersRank?ruid=%s&page=1&page_size=1";


    /**
     * 模板
     */
    String RESULT_BASIC_INFO_FORMAT = "%s：粉丝数量%s（%s）\n";

    /**
     * 模板
     */
    String RESULT_INFO_FORMAT = "%s：粉丝数量%s（%s） 舰长数量：%s（%s）\n";

    /**
     * 模板
     */
    String RESULT_ALL_INFO_FORMAT = "%s：粉丝数量%s（%s） 舰长数量：%s（%s） 粉丝团数量：%s（%s）\n";

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");


}
