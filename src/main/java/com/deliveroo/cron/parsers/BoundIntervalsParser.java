package com.deliveroo.cron.parsers;

import java.util.ArrayList;
import java.util.List;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronUnit;

public class BoundIntervalsParser extends Parser {

    private static final String DASH_DELIMITER = "-";

    @Override
    public List<Integer> getTimings(CronUnit cronUnit, String cronExpression) throws InvalidCronExpression {
        String[] boundIntervals = cronExpression.split(",");
        List<Integer> result = new ArrayList<>();
        for(String boundInterval : boundIntervals) {
            String[] intervals = boundInterval.split(DASH_DELIMITER);
            Integer startInterval = Integer.valueOf(intervals[0]);
            Integer endInterval = Integer.valueOf(intervals[1]);

            if(!isValid(startInterval, endInterval, cronUnit)) {
                throw new InvalidCronExpression(cronUnit, cronExpression, "Values passed are not in give range");
            }
            result.addAll(getCronTimings(startInterval, endInterval, 1));
        }
        return result;
    }

    @Override
    public String getRegex() {
        return "^\\d+-\\d+(,\\d+-\\d+)*$";
    }

    private boolean isValid(Integer startInterval, Integer endInterval, CronUnit cronUnit) {
        return isInRange(cronUnit.getStartRange(), cronUnit.getEndRange(), startInterval) &&
                isInRange(cronUnit.getStartRange(), cronUnit.getEndRange(), endInterval) &&
                startInterval <= endInterval;
    }
}
