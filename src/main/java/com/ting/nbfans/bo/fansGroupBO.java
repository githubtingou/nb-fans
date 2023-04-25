package com.ting.nbfans.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class fansGroupBO {

    @JsonProperty("item")
    private List<ItemDTO> item;
    @JsonProperty("num")
    private Integer num;
    @JsonProperty("medal_status")
    private Integer medalStatus;

    @NoArgsConstructor
    @Data
    public static class ItemDTO {
        @JsonProperty("user_rank")
        private String userRank;
        @JsonProperty("uid")
        private String uid;
        @JsonProperty("name")
        private String name;
        @JsonProperty("face")
        private String face;
        @JsonProperty("score")
        private Integer score;
        @JsonProperty("medal_name")
        private String medalName;
        @JsonProperty("level")
        private Integer level;
        @JsonProperty("target_id")
        private String targetId;
        @JsonProperty("special")
        private String special;
        @JsonProperty("guard_level")
        private Integer guardLevel;
        @JsonProperty("medal_color_start")
        private Integer medalColorStart;
        @JsonProperty("medal_color_end")
        private Integer medalColorEnd;
        @JsonProperty("medal_color_border")
        private Integer medalColorBorder;
        @JsonProperty("guard_icon")
        private String guardIcon;
        @JsonProperty("honor_icon")
        private String honorIcon;
    }
}
