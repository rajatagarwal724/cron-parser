package com.deliveroo.cron.parsers;

import java.util.ArrayList;
import java.util.List;

import com.deliveroo.cron.exception.InvalidCronExpression;

import com.deliveroo.cron.model.CronUnit;

public class FixedTimingsParser extends Parser {

    private static final String COMMA_DELIMITER = ",";

    @Override
    public List<Integer> getTimings(CronUnit cronUnit, String cronExpression) throws InvalidCronExpression {
        String[] fixedTimings = cronExpression.split(COMMA_DELIMITER);
        List<Integer> timings = new ArrayList<>();
        for(String fixedTime : fixedTimings) {
            int time = Integer.parseInt(fixedTime);
            if (!isInRange(cronUnit.getStartRange(), cronUnit.getEndRange(), time)) {
                throw new InvalidCronExpression(cronUnit, cronExpression, "Values passed are not in give range");
            }
            timings.add(time);
        }
        return timings;
    }

    @Override
    public String getRegex() {
        return "^\\d+(,\\d+)*$";
    }

    private boolean isValid(Integer value, CronUnit timeUnit) {
        return value >= timeUnit.getStartRange() && value <= timeUnit.getEndRange();
    }
}
