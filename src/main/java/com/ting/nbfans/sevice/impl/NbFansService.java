package com.ting.nbfans.sevice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ting.nbfans.BO.ResultBO;
import com.ting.nbfans.BO.UserInfoBO;
import com.ting.nbfans.common.BilibiliUrl;
import com.ting.nbfans.dao.Vup;
import com.ting.nbfans.mapper.VupMapper;
import com.ting.nbfans.sevice.INbFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NbFansService implements INbFansService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VupMapper vupMapper;

    @Override
    public void add(String uid) {
        if (StringUtils.hasLength(uid)) {
            String[] uidList = uid.split(",");

            List<Vup> vupList = new ArrayList<>();
            for (String item : uidList) {
                String userInfoList = String.format(BilibiliUrl.USER_INFO_FORMAT, item);
                ResponseEntity<Object> forEntity = this.restTemplate.getForEntity(userInfoList, Object.class);
                Object entityBody = forEntity.getBody();
                ResultBO<UserInfoBO> result = JSON.parseObject(JSON.toJSONString(entityBody), new TypeReference<ResultBO<UserInfoBO>>() {
                });
                UserInfoBO data = result.getData();
                Vup vup = new Vup();
                vup.setUid(String.valueOf(data.getMid()));
                vup.setUserName(data.getName());
                vupList.add(vup);
            }

            if (!CollectionUtils.isEmpty(vupList)) {
                this.vupMapper.saveAll(vupList);
            }
        } else {
            throw new RuntimeException("uid不能为空");
        }
    }

    @Override
    public String getAll() {



        return null;
    }
}
