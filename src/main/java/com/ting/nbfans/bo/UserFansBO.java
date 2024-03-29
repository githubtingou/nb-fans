package com.ting.nbfans.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserFansBO {

    @JsonProperty("mid")
    private String mid;
    @JsonProperty("following")
    private Integer following;
    @JsonProperty("whisper")
    private Integer whisper;
    @JsonProperty("black")
    private Integer black;
    @JsonProperty("follower")
    private Integer follower;
}
