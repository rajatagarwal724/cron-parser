package com.deliveroo.cron.parsers;

import java.util.List;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronUnit;

public class NumberParser extends Parser {

    @Override
    public List<Integer> getTimings(CronUnit cronUnit, String cronExpression) throws InvalidCronExpression {
        int value = Integer.parseInt(cronExpression);
        if (!isInRange(cronUnit.getStartRange(), cronUnit.getEndRange(), value)) {
            throw new InvalidCronExpression(cronUnit, cronExpression, "Values passed are not in give range");
        }

        return List.of(Integer.valueOf(cronExpression));
    }

    @Override
    public String getRegex() {
        return "^\\d+$";
    }
}
