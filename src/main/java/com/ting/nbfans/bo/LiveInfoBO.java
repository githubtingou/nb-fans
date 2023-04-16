package com.ting.nbfans.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 直播信息
 */
@Data
public class LiveInfoBO {

    @JsonProperty("info")
    private InfoDTO info;
    @JsonProperty("list")
    private List<ListDTO> list;
    @JsonProperty("top3")
    private List<Top3DTO> top3;
    @JsonProperty("my_follow_info")
    private MyFollowInfoDTO myFollowInfo;
    @JsonProperty("guard_warn")
    private GuardWarnDTO guardWarn;
    @JsonProperty("exist_benefit")
    private Boolean existBenefit;
    @JsonProperty("remind_benefit")
    private String remindBenefit;

    @NoArgsConstructor
    @Data
    public static class InfoDTO {
        @JsonProperty("num")
        private Integer num;
        @JsonProperty("page")
        private Integer page;
        @JsonProperty("now")
        private Integer now;
        @JsonProperty("achievement_level")
        private Integer achievementLevel;
        @JsonProperty("anchor_guard_achieve_level")
        private Integer anchorGuardAchieveLevel;
    }

    @NoArgsConstructor
    @Data
    public static class MyFollowInfoDTO {
        @JsonProperty("guard_level")
        private Integer guardLevel;
        @JsonProperty("accompany_days")
        private Integer accompanyDays;
        @JsonProperty("expired_time")
        private String expiredTime;
        @JsonProperty("auto_renew")
        private Integer autoRenew;
        @JsonProperty("renew_remind")
        private RenewRemindDTO renewRemind;
        @JsonProperty("medal_info")
        private MedalInfoDTO medalInfo;
        @JsonProperty("rank")
        private Integer rank;
        @JsonProperty("ruid")
        private String ruid;
        @JsonProperty("face")
        private String face;

        @NoArgsConstructor
        @Data
        public static class RenewRemindDTO {
            @JsonProperty("content")
            private String content;
            @JsonProperty("type")
            private Integer type;
            @JsonProperty("hint")
            private String hint;
        }

        @NoArgsConstructor
        @Data
        public static class MedalInfoDTO {
            @JsonProperty("medal_name")
            private String medalName;
            @JsonProperty("medal_level")
            private Integer medalLevel;
            @JsonProperty("medal_color_start")
            private Integer medalColorStart;
            @JsonProperty("medal_color_end")
            private Integer medalColorEnd;
            @JsonProperty("medal_color_border")
            private Integer medalColorBorder;
        }
    }

    @NoArgsConstructor
    @Data
    public static class GuardWarnDTO {
        @JsonProperty("is_warn")
        private Integer isWarn;
        @JsonProperty("warn")
        private String warn;
        @JsonProperty("expired")
        private Integer expired;
        @JsonProperty("will_expired")
        private Integer willExpired;
        @JsonProperty("address")
        private String address;
    }

    @NoArgsConstructor
    @Data
    public static class ListDTO {
        @JsonProperty("uid")
        private String uid;
        @JsonProperty("ruid")
        private String ruid;
        @JsonProperty("rank")
        private Integer rank;
        @JsonProperty("username")
        private String username;
        @JsonProperty("face")
        private String face;
        @JsonProperty("is_alive")
        private Integer isAlive;
        @JsonProperty("guard_level")
        private Integer guardLevel;
        @JsonProperty("guard_sub_level")
        private Integer guardSubLevel;
        @JsonProperty("medal_info")
        private MedalInfoDTO medalInfo;

        @NoArgsConstructor
        @Data
        public static class MedalInfoDTO {
            @JsonProperty("medal_name")
            private String medalName;
            @JsonProperty("medal_level")
            private Integer medalLevel;
            @JsonProperty("medal_color_start")
            private Integer medalColorStart;
            @JsonProperty("medal_color_end")
            private Integer medalColorEnd;
            @JsonProperty("medal_color_border")
            private Integer medalColorBorder;
        }
    }

    @NoArgsConstructor
    @Data
    public static class Top3DTO {
        @JsonProperty("uid")
        private String uid;
        @JsonProperty("ruid")
        private String ruid;
        @JsonProperty("rank")
        private Integer rank;
        @JsonProperty("username")
        private String username;
        @JsonProperty("face")
        private String face;
        @JsonProperty("is_alive")
        private Integer isAlive;
        @JsonProperty("guard_level")
        private Integer guardLevel;
        @JsonProperty("guard_sub_level")
        private Integer guardSubLevel;
        @JsonProperty("medal_info")
        private MedalInfoDTO medalInfo;

        @NoArgsConstructor
        @Data
        public static class MedalInfoDTO {
            @JsonProperty("medal_name")
            private String medalName;
            @JsonProperty("medal_level")
            private Integer medalLevel;
            @JsonProperty("medal_color_start")
            private Integer medalColorStart;
            @JsonProperty("medal_color_end")
            private Integer medalColorEnd;
            @JsonProperty("medal_color_border")
            private Integer medalColorBorder;
        }
    }
}
