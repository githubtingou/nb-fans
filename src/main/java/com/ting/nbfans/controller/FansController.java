package com.ting.nbfans.controller;

import com.ting.nbfans.sevice.INbFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "nbfans")
public class FansController {

    @Autowired
    private INbFansService nbFansService;

    @GetMapping(value = "/add")
    public void addVup(@RequestParam(value = "uid") String uid) {
        this.nbFansService.add(uid);
    }
    @GetMapping(value = "/getAll")
    public String getAll() {
       return this.nbFansService.getAll();
    }

}
