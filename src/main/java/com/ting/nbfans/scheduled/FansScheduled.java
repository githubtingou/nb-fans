package com.ting.nbfans.scheduled;

import com.ting.nbfans.common.BilibiliUrl;
import com.ting.nbfans.dao.VupFan;
import com.ting.nbfans.mapper.VupFanMapper;
import com.ting.nbfans.sevice.INbFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FansScheduled {

    @Autowired
    private INbFansService nbFansService;

    @Autowired
    private VupFanMapper vupFanMapper;

    @Scheduled(cron = "1 0 0 * * ?")
    public void getFans() {
        List<VupFan> fans = this.nbFansService.getFans(true);

        // 跟前一天的数据用户历史数据的对比
        List<VupFan> preFansList = new ArrayList<>();
        for (VupFan fan : fans) {
            String uid = fan.getUid();
            String recordTime = fan.getRecordTime();
            LocalDate parse = LocalDate.parse(recordTime, BilibiliUrl.FORMATTER);
            String preRecordTime = parse.plusDays(-1).format(BilibiliUrl.FORMATTER);
            VupFan preVupFun = this.vupFanMapper.getByRecordTimeAndAndUid(preRecordTime, uid);
            if (preVupFun != null) {
                preVupFun.setFinalCaptainNum(fan.getCaptainNum());
                preVupFun.setFinalFollower(fan.getFollower());
                preVupFun.setFinalFansGroupNum(fan.getFansGroupNum());
                preFansList.add(preVupFun);
            }

        }
        if (!CollectionUtils.isEmpty(preFansList)) {
            this.vupFanMapper.saveAllAndFlush(preFansList);
        }
    }


}
