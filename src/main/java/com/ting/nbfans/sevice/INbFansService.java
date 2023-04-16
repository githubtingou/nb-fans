package com.ting.nbfans.sevice;


import com.ting.nbfans.dao.VupFan;

import java.util.List;

public interface INbFansService {

    /**
     * 新增
     *
     * @param uid
     */
    void add(String uid);

    /**
     * 获取
     *
     * @return
     */
    String getAll();

    /**
     * 获取关注数量和舰长数量
     *
     * @param isInsert
     * @return
     */
    List<VupFan> getFans(Boolean isInsert);

    /**
     * 获取俩天的对比
     *
     * @param days
     * @return
     */
    String getDay(String days);
}
