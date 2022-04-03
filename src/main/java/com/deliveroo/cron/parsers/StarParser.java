package com.deliveroo.cron.parsers;

import java.util.List;

import com.deliveroo.cron.model.CronUnit;

public class StarParser extends Parser {

    @Override
    public List<Integer> getTimings(CronUnit timeUnit, String cronExpression) {
        return getCronTimings(timeUnit, 1);
    }

    @Override
    public String getRegex() {
        return "^\\*$";
    }
}
