package com.ting.nbfans;

import com.ting.nbfans.scheduled.FansScheduled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NbFansApplicationTests {

    @Autowired
    private FansScheduled fansScheduled;
    @Test
    void contextLoads() {
//        fansScheduled.getFans();
    }

}
