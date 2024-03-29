package com.ting.nbfans.sevice.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ting.nbfans.bo.*;
import com.ting.nbfans.common.BilibiliUrl;
import com.ting.nbfans.dao.Vup;
import com.ting.nbfans.dao.VupFan;
import com.ting.nbfans.mapper.VupFanMapper;
import com.ting.nbfans.mapper.VupMapper;
import com.ting.nbfans.sevice.INbFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
            Date date = new Date();
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
                        vup.setUpdateTime(date);
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
        List<NbfansBO> nbfansBOListO = new ArrayList<>(all.size());

        Map<String, VupFan> newInfo = newFans.stream().collect(Collectors.toMap(VupFan::getUid, Function.identity(), (vupFan, vupFan2) -> vupFan));
        if (CollectionUtils.isEmpty(recordTime)) {
            for (Vup vup : all) {
                String vupId = vup.getId();
                VupFan vupFan = newInfo.get(vupId);
                String format = String.format(BilibiliUrl.RESULT_INFO_FORMAT, vup.getUserName(), vupFan.getFollower(), "+0", vupFan.getCaptainNum(), "+0");

                NbfansBO nbfansBO = new NbfansBO();
                nbfansBO.setUid(vupId);
                nbfansBO.setUserName(vup.getUserName());
                nbfansBO.setFinalFollower(0);
                nbfansBO.setAddFollowerNum(0);
                nbfansBO.setFinalCaptainNum(0);
                nbfansBO.setAddCaptainNum(0);
                nbfansBO.setFansGroupNum(0);
                nbfansBO.setAddGroupNum(0);
                nbfansBOListO.add(nbfansBO);
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
                int addGroupNum = newFan.getFansGroupNum() - Optional.ofNullable(oldFan.getFansGroupNum()).orElse(0);

                NbfansBO nbfansBO = new NbfansBO();
                nbfansBO.setUid(id);
                nbfansBO.setUserName(vup.getUserName());
                nbfansBO.setFinalFollower(newFan.getFollower());
                nbfansBO.setAddFollowerNum(addFollowerNum);
                nbfansBO.setFinalCaptainNum(newFan.getCaptainNum());
                nbfansBO.setAddCaptainNum(addCaptainNum);
                nbfansBO.setFansGroupNum(newFan.getFansGroupNum());
                nbfansBO.setAddGroupNum(addGroupNum);
                nbfansBOListO.add(nbfansBO);

            }
            if (!CollectionUtils.isEmpty(insertFans)) {
                this.vupFanMapper.saveAllAndFlush(insertFans);
            }
        }

        return this.sortListByAddFollower(nbfansBOListO);
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
                    CompletableFuture<ResultBO<UserFansBO>> fansResult = CompletableFuture.supplyAsync(() -> {
                        String fansUrl = String.format(BilibiliUrl.FANS_URL_FORMAT, uid);
                        String value = HttpUtil.get(fansUrl);
                        return JSON.parseObject(value, new TypeReference<ResultBO<UserFansBO>>() {
                        });
                    });

                    // 获取舰长数量
                    CompletableFuture<ResultBO<LiveInfoBO>> liveResult = CompletableFuture.supplyAsync(() -> {
                        String liveUrl = String.format(BilibiliUrl.LIVE_INFO_FORMAT, liveId, uid);
                        String liveUrlValue = HttpUtil.get(liveUrl);
                        return JSON.parseObject(liveUrlValue, new TypeReference<ResultBO<LiveInfoBO>>() {
                        });
                    });

                    // 获取粉丝团数量
                    CompletableFuture<ResultBO<FansGroupBO>> fansGroupResult = CompletableFuture.supplyAsync(() -> {
                        String fansGroupUrl = String.format(BilibiliUrl.FAN_GROUP_URL_FORMAT, uid);
                        String fansGroupValue = HttpUtil.get(fansGroupUrl);

                        return JSON.parseObject(fansGroupValue, new TypeReference<ResultBO<FansGroupBO>>() {
                        });
                    });

                    CompletableFuture.allOf(fansResult, liveResult, fansGroupResult).join();
                    ResultBO<UserFansBO> userFansBOResultBO = fansResult.get();
                    ResultBO<LiveInfoBO> liveInfoBOResultBO = liveResult.get();
                    ResultBO<FansGroupBO> fansGroupBOResultBO = fansGroupResult.get();

                    // 封装数据
                    VupFan info = new VupFan();
                    info.setUid(uid);
                    info.setRecordTime(nowDate);
                    info.setFollower(userFansBOResultBO.getData().getFollower());
                    LiveInfoBO data = liveInfoBOResultBO.getData();
                    info.setCaptainNum(data.getInfo().getNum());
                    info.setFansGroupNum(fansGroupBOResultBO.getData().getNum());

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

        List<NbfansBO> nbfansBOListO = new ArrayList<>(vupList.size());
        Map<String, VupFan> vupFanMap = Optional.ofNullable(oldList)
                .orElseGet(ArrayList::new)
                .stream().collect(Collectors.toMap(VupFan::getUid, Function.identity(), (vupFan, vupFan2) -> vupFan));

        for (Vup vup : vupList) {
            String id = vup.getId();
            VupFan vupFan = vupFanMap.get(id);
            if (vupFan != null) {
                Integer finalFollower = Optional.ofNullable(vupFan.getFinalFollower()).orElse(vupFan.getFollower());
                Integer finalCaptainNum = Optional.ofNullable(vupFan.getFinalCaptainNum()).orElse(vupFan.getCaptainNum());
                Integer finalGroupNum = Optional.ofNullable(vupFan.getFinalFansGroupNum()).orElse(vupFan.getFansGroupNum());
                int addFollowerNum = finalFollower - vupFan.getFollower();
                int addCaptainNum = finalCaptainNum - vupFan.getCaptainNum();
                int addGroupNum = finalGroupNum - vupFan.getFansGroupNum();

                NbfansBO nbfansBO = new NbfansBO();
                nbfansBO.setUid(id);
                nbfansBO.setUserName(vup.getUserName());
                nbfansBO.setFinalFollower(vupFan.getFinalFollower());
                nbfansBO.setAddFollowerNum(addFollowerNum);
                nbfansBO.setFinalCaptainNum(vupFan.getFinalCaptainNum());
                nbfansBO.setAddCaptainNum(addCaptainNum);
                nbfansBO.setFansGroupNum(vupFan.getFinalFansGroupNum());
                nbfansBO.setAddGroupNum(addGroupNum);
                nbfansBOListO.add(nbfansBO);


            } else {
                NbfansBO nbfansBO = new NbfansBO();
                nbfansBO.setUid(id);
                nbfansBO.setUserName("暂未收录" + vup.getUserName() + days + "时间段的数据：");
                nbfansBO.setFinalFollower(0);
                nbfansBO.setAddFollowerNum(0);
                nbfansBO.setFinalCaptainNum(0);
                nbfansBO.setFansGroupNum(0);
                nbfansBO.setAddGroupNum(0);
                nbfansBOListO.add(nbfansBO);


            }
        }

        return this.sortListByAddFollower(nbfansBOListO);
    }

    private String sortListByAddFollower(List<NbfansBO> nbfansBOListO) {
        return nbfansBOListO.stream()
                .sorted(Comparator.comparing(NbfansBO::getAddFollowerNum).reversed())
                .map(nbfansBO -> String.format(BilibiliUrl.RESULT_ALL_INFO_FORMAT,
                        nbfansBO.getUserName(),
                        nbfansBO.getFinalFollower(),
                        nbfansBO.getAddFollowerNum() >= 0 ? "+" + nbfansBO.getAddFollowerNum() : nbfansBO.getAddFollowerNum(),
                        nbfansBO.getFinalCaptainNum(),
                        nbfansBO.getAddCaptainNum() >= 0 ? "+" + nbfansBO.getAddCaptainNum() : nbfansBO.getAddCaptainNum(),
                        nbfansBO.getFansGroupNum(),
                        nbfansBO.getAddGroupNum() >= 0 ? "+" + nbfansBO.getAddGroupNum() : nbfansBO.getAddGroupNum())
                )
                .collect(Collectors.joining());
    }
}
