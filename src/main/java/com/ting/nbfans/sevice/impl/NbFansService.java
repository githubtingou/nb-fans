package com.ting.nbfans.sevice.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ting.nbfans.bo.LiveInfoBO;
import com.ting.nbfans.bo.ResultBO;
import com.ting.nbfans.bo.UserFansBO;
import com.ting.nbfans.bo.UserInfoBO;
import com.ting.nbfans.common.BilibiliUrl;
import com.ting.nbfans.dao.Vup;
import com.ting.nbfans.dao.VupFan;
import com.ting.nbfans.mapper.VupFanMapper;
import com.ting.nbfans.mapper.VupMapper;
import com.ting.nbfans.sevice.INbFansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NbFansService implements INbFansService {


    @Autowired
    private VupMapper vupMapper;

    @Autowired
    private VupFanMapper vupFanMapper;

    @Autowired
    @Qualifier(value = "executor")
    private ThreadPoolExecutor executor;

    @Override
    public void add(String uid) {
        if (StringUtils.hasLength(uid)) {
            // 获取用户信息
            String[] uidList = uid.split(",");
            CountDownLatch downLatch = new CountDownLatch(uidList.length);
            List<Vup> vupList = new ArrayList<>();
            for (String item : uidList) {
                executor.execute(() -> {
                    try {
                        String userInfoList = String.format(BilibiliUrl.USER_INFO_FORMAT, item);
                        String entityBody = HttpUtil.get(userInfoList);
                        ResultBO<UserInfoBO> result = JSON.parseObject(entityBody, new TypeReference<ResultBO<UserInfoBO>>() {
                        });
                        UserInfoBO data = result.getData();
                        Vup vup = new Vup();
                        vup.setId(String.valueOf(data.getMid()));
                        vup.setUserName(data.getName());
                        vup.setLiveId(String.valueOf(data.getLiveRoom().getRoomid()));
                        vupList.add(vup);
                    } finally {
                        downLatch.countDown();

                    }
                });
            }
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("获取用户信息失败");
            }
            if (!CollectionUtils.isEmpty(vupList)) {
                this.vupMapper.saveAllAndFlush(vupList);
            }
        } else {
            throw new RuntimeException("uid不能为空");
        }
    }

    @Override
    public String getAll() {
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<VupFan> recordTime = this.vupFanMapper.findByRecordTime(nowDate);
        List<VupFan> newFans = this.getFans(false);
        List<Vup> all = this.vupMapper.findAll();
        if (CollectionUtils.isEmpty(all)) {
            return "没有内置数据，请先内置数据";
        }
        StringBuilder stringBuilder = new StringBuilder();

        Map<String, VupFan> newInfo = newFans.stream().collect(Collectors.toMap(VupFan::getUid, Function.identity(), (vupFan, vupFan2) -> vupFan));
        if (CollectionUtils.isEmpty(recordTime)) {
            for (Vup vup : all) {
                VupFan vupFan = newInfo.get(vup.getId());
                stringBuilder.append(String.format(BilibiliUrl.RESULT_INFO_FORMAT, vup.getUserName(), vupFan.getFollower(), "+0", vupFan.getCaptainNum(), "+0"));
            }
            this.vupFanMapper.saveAllAndFlush(newFans);

        } else {
            Map<String, VupFan> oldInfo = recordTime.stream().collect(Collectors.toMap(VupFan::getUid, Function.identity(), (vupFan, vupFan2) -> vupFan));
            List<VupFan> insertFans = new ArrayList<>();
            for (Vup vup : all) {
                String id = vup.getId();
                VupFan newFan = newInfo.get(id);
                VupFan oldFan = oldInfo.get(id);
                if (oldFan == null) {
                    oldFan = newFan;
                    insertFans.add(newFan);
                }
                int addFollowerNum = newFan.getFollower() - Optional.ofNullable(oldFan.getFollower()).orElse(0);
                int addCaptainNum = newFan.getCaptainNum() - Optional.ofNullable(oldFan.getCaptainNum()).orElse(0);
                stringBuilder.append(String.format(BilibiliUrl.RESULT_INFO_FORMAT,
                        vup.getUserName(),
                        newFan.getFollower(),
                        addFollowerNum >= 0 ? "+" + addFollowerNum : addFollowerNum,
                        newFan.getCaptainNum(),
                        addCaptainNum >= 0 ? "+" + addCaptainNum : addCaptainNum));
            }
            if (!CollectionUtils.isEmpty(insertFans)) {
                this.vupFanMapper.saveAllAndFlush(insertFans);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public List<VupFan> getFans(Boolean isInsert) {

        List<Vup> allVup = this.vupMapper.findAll();
        if (CollectionUtils.isEmpty(allVup)) {
            return new ArrayList<>();
        }

        String nowDate = LocalDate.now().format(BilibiliUrl.FORMATTER);
        List<VupFan> fanList = this.vupFanMapper.findByRecordTime(nowDate);

        Map<String, VupFan> fansMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(fanList)) {
            fansMap = fanList.stream().collect(Collectors.toMap(VupFan::getUid, Function.identity(), ((vupFan, vupFan2) -> vupFan)));
        }

        CountDownLatch downLatch = new CountDownLatch(allVup.size());
        List<VupFan> fans = new ArrayList<>();
        Map<String, VupFan> finalFansMap = fansMap;
        for (Vup vup : allVup) {
            executor.execute(() -> {
                try {
                    String uid = vup.getId();
                    String liveId = vup.getLiveId();

                    // 获取关注数量
                    String fansUrl = String.format(BilibiliUrl.FANS_URL_FORMAT, uid);
                    String value = HttpUtil.get(fansUrl);
                    ResultBO<UserFansBO> result = JSON.parseObject(value, new TypeReference<ResultBO<UserFansBO>>() {
                    });

                    // 获取舰长数量
                    String liveUrl = String.format(BilibiliUrl.LIVE_INFO_FORMAT, liveId, uid);
                    String liveUrlValue = HttpUtil.get(liveUrl);
                    ResultBO<LiveInfoBO> liveResult = JSON.parseObject(liveUrlValue, new TypeReference<ResultBO<LiveInfoBO>>() {
                    });

                    // 封装数据
                    VupFan info = new VupFan();
                    info.setUid(uid);
                    info.setRecordTime(nowDate);
                    info.setFollower(result.getData().getFollower());
                    LiveInfoBO data = liveResult.getData();
                    info.setCaptainNum(data.getInfo().getNum());
                    if (finalFansMap.get(uid) != null) {
                        info.setId(finalFansMap.get(uid).getId());
                    }
                    fans.add(info);
                } catch (Exception e) {
                    e.getStackTrace();
                    System.out.println("CountDownLatch获取数据失败," + e);
                } finally {
                    downLatch.countDown();

                }
            });


        }
        try {
            downLatch.await();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("获取数据失败," + e);

        }
        if (!CollectionUtils.isEmpty(fans) && !Boolean.FALSE.equals(isInsert)) {
            this.vupFanMapper.saveAllAndFlush(fans);

        }
        return fans;
    }

    @Override
    public String getDay(String days) {
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        days = Optional.ofNullable(days).orElse(LocalDate.now().plusDays(-1).format(BilibiliUrl.FORMATTER));
        // 如果是当天直接返回实时数据
        if (Objects.equals(nowDate, days)) {
            return this.getAll();
        }

        List<VupFan> oldList = this.vupFanMapper.findByRecordTime(days);

        List<Vup> vupList = this.vupMapper.findAll();
        if (CollectionUtils.isEmpty(vupList)) {
            return "没有内置用户";
        }

        Map<String, VupFan> vupFanMap = Optional.ofNullable(oldList)
                .orElseGet(ArrayList::new)
                .stream().collect(Collectors.toMap(VupFan::getUid, Function.identity(), (vupFan, vupFan2) -> vupFan));

        StringBuilder stringBuilder = new StringBuilder();
        for (Vup vup : vupList) {
            String id = vup.getId();
            VupFan vupFan = vupFanMap.get(id);
            if (vupFan != null) {
                Integer finalFollower = Optional.ofNullable(vupFan.getFinalFollower()).orElse(vupFan.getFollower());
                Integer finalCaptainNum = Optional.ofNullable(vupFan.getFinalCaptainNum()).orElse(vupFan.getCaptainNum());
                int addFollowerNum = finalFollower - vupFan.getFollower();
                int addCaptainNum = finalCaptainNum - vupFan.getCaptainNum();
                stringBuilder.append(String.format(BilibiliUrl.RESULT_INFO_FORMAT,
                        vup.getUserName(),
                        vupFan.getFinalFollower(),
                        addFollowerNum >= 0 ? "+" + addFollowerNum : addFollowerNum,
                        vupFan.getFinalFollower(),
                        addCaptainNum >= 0 ? "+" + addCaptainNum : addCaptainNum));
            } else {
                stringBuilder.append(String.format(BilibiliUrl.RESULT_INFO_FORMAT,
                        "暂未收录" + vup.getUserName() + days + "时间段的数据：",
                        0,
                        0,
                        0,
                        0));
            }
        }
        return stringBuilder.toString();
    }

}
