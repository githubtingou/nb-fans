package com.ting.nbfans.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ting.nbfans.BO.ResultBO;
import com.ting.nbfans.BO.UserFansBO;
import com.ting.nbfans.BO.UserInfoBO;
import com.ting.nbfans.common.BilibiliUrl;
import com.ting.nbfans.dao.Vup;
import com.ting.nbfans.dao.VupFans;
import com.ting.nbfans.mapper.VupFansMapper;
import com.ting.nbfans.mapper.VupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FansScheduled {

    @Autowired
    private VupMapper vupMapper;

    @Autowired
    private VupFansMapper vupFansMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void getFans() {

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<VupFans> vupFans = this.vupFansMapper.findByRecordTime(nowDate);
        Iterable<Vup> all = this.vupMapper.findAll();
        List<VupFans> fans = new ArrayList<>();
        for (Vup vup : all) {
            String uid = vup.getUid();
            String fansUrl = String.format(BilibiliUrl.FANS_URL_FORMAT, vup.getUid());
            ResponseEntity<Object> forEntity = this.restTemplate.getForEntity(fansUrl, Object.class);
            ResultBO<UserFansBO> result = JSON.parseObject(JSON.toJSONString(forEntity.getBody()), new TypeReference<ResultBO<UserFansBO>>() {
            });
            if (vupFans == null) {

            }
        }

    }

    public static void main(String[] args) {
    }
}
