package com.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.server.services.DailyValueService;

@RestController
public class DailyValueController {

    private final DailyValueService dailyValueService;

    @Autowired
    public DailyValueController(DailyValueService dailyValueService) {
        this.dailyValueService = dailyValueService;
    }
}
