package com.ting.nbfans.BO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResultBO<T> {
    private Integer code;
    private String message;
    private Integer ttl;
    private T data;
}
