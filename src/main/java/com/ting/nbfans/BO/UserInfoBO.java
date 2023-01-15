package com.ting.nbfans.BO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInfoBO {

    @JsonProperty("mid")
    private Integer mid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("face")
    private String face;
    @JsonProperty("face_nft")
    private Integer faceNft;
    @JsonProperty("face_nft_type")
    private Integer faceNftType;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("rank")
    private Integer rank;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("jointime")
    private Integer jointime;
    @JsonProperty("moral")
    private Integer moral;
    @JsonProperty("silence")
    private Integer silence;
    @JsonProperty("coins")
    private Integer coins;
    @JsonProperty("fans_badge")
    private Boolean fansBadge;
    @JsonProperty("fans_medal")
    private FansMedalDTO fansMedal;
    @JsonProperty("official")
    private OfficialDTO official;
    @JsonProperty("vip")
    private VipDTO vip;
    @JsonProperty("pendant")
    private PendantDTO pendant;
    @JsonProperty("nameplate")
    private NameplateDTO nameplate;
    @JsonProperty("user_honour_info")
    private UserHonourInfoDTO userHonourInfo;
    @JsonProperty("is_followed")
    private Boolean isFollowed;
    @JsonProperty("top_photo")
    private String topPhoto;
    @JsonProperty("theme")
    private ThemeDTO theme;
    @JsonProperty("sys_notice")
    private SysNoticeDTO sysNotice;
    @JsonProperty("live_room")
    private LiveRoomDTO liveRoom;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("school")
    private SchoolDTO school;
    @JsonProperty("profession")
    private ProfessionDTO profession;
    @JsonProperty("tags")
    private Object tags;
    @JsonProperty("series")
    private SeriesDTO series;
    @JsonProperty("is_senior_member")
    private Integer isSeniorMember;
    @JsonProperty("mcn_info")
    private Object mcnInfo;
    @JsonProperty("gaia_res_type")
    private Integer gaiaResType;
    @JsonProperty("gaia_data")
    private Object gaiaData;
    @JsonProperty("is_risk")
    private Boolean isRisk;
    @JsonProperty("elec")
    private ElecDTO elec;
    @JsonProperty("contract")
    private Object contract;

    @NoArgsConstructor
    @Data
    public static class FansMedalDTO {
        @JsonProperty("show")
        private Boolean show;
        @JsonProperty("wear")
        private Boolean wear;
        @JsonProperty("medal")
        private MedalDTO medal;

        @NoArgsConstructor
        @Data
        public static class MedalDTO {
            @JsonProperty("uid")
            private Integer uid;
            @JsonProperty("target_id")
            private Long targetId;
            @JsonProperty("medal_id")
            private Integer medalId;
            @JsonProperty("level")
            private Integer level;
            @JsonProperty("medal_name")
            private String medalName;
            @JsonProperty("medal_color")
            private Integer medalColor;
            @JsonProperty("intimacy")
            private Integer intimacy;
            @JsonProperty("next_intimacy")
            private Integer nextIntimacy;
            @JsonProperty("day_limit")
            private Integer dayLimit;
            @JsonProperty("medal_color_start")
            private Integer medalColorStart;
            @JsonProperty("medal_color_end")
            private Integer medalColorEnd;
            @JsonProperty("medal_color_border")
            private Integer medalColorBorder;
            @JsonProperty("is_lighted")
            private Integer isLighted;
            @JsonProperty("light_status")
            private Integer lightStatus;
            @JsonProperty("wearing_status")
            private Integer wearingStatus;
            @JsonProperty("score")
            private Integer score;
        }
    }

    @NoArgsConstructor
    @Data
    public static class OfficialDTO {
        @JsonProperty("role")
        private Integer role;
        @JsonProperty("title")
        private String title;
        @JsonProperty("desc")
        private String desc;
        @JsonProperty("type")
        private Integer type;
    }

    @NoArgsConstructor
    @Data
    public static class VipDTO {
        @JsonProperty("type")
        private Integer type;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("due_date")
        private Long dueDate;
        @JsonProperty("vip_pay_type")
        private Integer vipPayType;
        @JsonProperty("theme_type")
        private Integer themeType;
        @JsonProperty("label")
        private LabelDTO label;
        @JsonProperty("avatar_subscript")
        private Integer avatarSubscript;
        @JsonProperty("nickname_color")
        private String nicknameColor;
        @JsonProperty("role")
        private Integer role;
        @JsonProperty("avatar_subscript_url")
        private String avatarSubscriptUrl;
        @JsonProperty("tv_vip_status")
        private Integer tvVipStatus;
        @JsonProperty("tv_vip_pay_type")
        private Integer tvVipPayType;

        @NoArgsConstructor
        @Data
        public static class LabelDTO {
            @JsonProperty("path")
            private String path;
            @JsonProperty("text")
            private String text;
            @JsonProperty("label_theme")
            private String labelTheme;
            @JsonProperty("text_color")
            private String textColor;
            @JsonProperty("bg_style")
            private Integer bgStyle;
            @JsonProperty("bg_color")
            private String bgColor;
            @JsonProperty("border_color")
            private String borderColor;
            @JsonProperty("use_img_label")
            private Boolean useImgLabel;
            @JsonProperty("img_label_uri_hans")
            private String imgLabelUriHans;
            @JsonProperty("img_label_uri_hant")
            private String imgLabelUriHant;
            @JsonProperty("img_label_uri_hans_static")
            private String imgLabelUriHansStatic;
            @JsonProperty("img_label_uri_hant_static")
            private String imgLabelUriHantStatic;
        }
    }

    @NoArgsConstructor
    @Data
    public static class PendantDTO {
        @JsonProperty("pid")
        private Integer pid;
        @JsonProperty("name")
        private String name;
        @JsonProperty("image")
        private String image;
        @JsonProperty("expire")
        private Integer expire;
        @JsonProperty("image_enhance")
        private String imageEnhance;
        @JsonProperty("image_enhance_frame")
        private String imageEnhanceFrame;
    }

    @NoArgsConstructor
    @Data
    public static class NameplateDTO {
        @JsonProperty("nid")
        private Integer nid;
        @JsonProperty("name")
        private String name;
        @JsonProperty("image")
        private String image;
        @JsonProperty("image_small")
        private String imageSmall;
        @JsonProperty("level")
        private String level;
        @JsonProperty("condition")
        private String condition;
    }

    @NoArgsConstructor
    @Data
    public static class UserHonourInfoDTO {
    }

    @NoArgsConstructor
    @Data
    public static class ThemeDTO {
    }

    @NoArgsConstructor
    @Data
    public static class SysNoticeDTO {
    }

    @NoArgsConstructor
    @Data
    public static class LiveRoomDTO {
        @JsonProperty("roomStatus")
        private Integer roomStatus;
        @JsonProperty("liveStatus")
        private Integer liveStatus;
        @JsonProperty("url")
        private String url;
        @JsonProperty("title")
        private String title;
        @JsonProperty("cover")
        private String cover;
        @JsonProperty("roomid")
        private Integer roomid;
        @JsonProperty("roundStatus")
        private Integer roundStatus;
        @JsonProperty("broadcast_type")
        private Integer broadcastType;
        @JsonProperty("watched_show")
        private WatchedShowDTO watchedShow;

        @NoArgsConstructor
        @Data
        public static class WatchedShowDTO {
            @JsonProperty("switch")
            private Boolean switchX;
            @JsonProperty("num")
            private Integer num;
            @JsonProperty("text_small")
            private String textSmall;
            @JsonProperty("text_large")
            private String textLarge;
            @JsonProperty("icon")
            private String icon;
            @JsonProperty("icon_location")
            private String iconLocation;
            @JsonProperty("icon_web")
            private String iconWeb;
        }
    }

    @NoArgsConstructor
    @Data
    public static class SchoolDTO {
        @JsonProperty("name")
        private String name;
    }

    @NoArgsConstructor
    @Data
    public static class ProfessionDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("department")
        private String department;
        @JsonProperty("title")
        private String title;
        @JsonProperty("is_show")
        private Integer isShow;
    }

    @NoArgsConstructor
    @Data
    public static class SeriesDTO {
        @JsonProperty("user_upgrade_status")
        private Integer userUpgradeStatus;
        @JsonProperty("show_upgrade_window")
        private Boolean showUpgradeWindow;
    }

    @NoArgsConstructor
    @Data
    public static class ElecDTO {
        @JsonProperty("show_info")
        private ShowInfoDTO showInfo;

        @NoArgsConstructor
        @Data
        public static class ShowInfoDTO {
            @JsonProperty("show")
            private Boolean show;
            @JsonProperty("state")
            private Integer state;
            @JsonProperty("title")
            private String title;
            @JsonProperty("icon")
            private String icon;
            @JsonProperty("jump_url")
            private String jumpUrl;
        }
    }
}
