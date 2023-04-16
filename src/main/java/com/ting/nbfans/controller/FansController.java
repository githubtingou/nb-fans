package com.ting.nbfans.controller;

import com.ting.nbfans.common.BilibiliUrl;
import com.ting.nbfans.sevice.INbFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = "nbfans")
public class FansController {

    @Autowired
    private INbFansService nbFansService;

    /**
     * 添加主播
     *
     * @param uid
     * @return
     */
    @GetMapping(value = "/add")
    public String addVup(@RequestParam(value = "uid") String uid) {
        this.nbFansService.add(uid);
        return "加入成功";
    }

    /**
     * 获取当天的数据
     *
     * @return
     */
    @GetMapping(value = "/getAll")
    public String getAll() {
        return this.nbFansService.getAll();
    }

    /**
     * 根据时间获取数据，默认为昨天数据
     *
     * @param days
     * @return
     */
    @GetMapping(value = "/getDay")
    public String getDay(@RequestParam(value = "days", required = false) String days) {
        return this.nbFansService.getDay(Optional.ofNullable(days).orElse(LocalDate.now().plusDays(-1).format(BilibiliUrl.FORMATTER)));
    }
}
