package com.deliveroo.cron.parsers;

import java.util.List;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronUnit;

public class NthIntervalParser extends Parser {

    private static final String NTH_INTERVAL_FORMAT = "*/";

    @Override
    public List<Integer> getTimings(CronUnit timeUnit, String cronExpression) throws InvalidCronExpression {
        String intervalString = cronExpression.substring(NTH_INTERVAL_FORMAT.length());

        int interval = Integer.parseInt(intervalString);

        if (!isInRange(timeUnit.getStartRange(), timeUnit.getEndRange(), interval)) {
            throw new InvalidCronExpression(timeUnit, cronExpression, "Values passed are not in give range");
        }

        return getCronTimings(timeUnit, interval);
    }

    @Override
    public String getRegex() {
        return "^\\*/\\d+$";
    }

}
