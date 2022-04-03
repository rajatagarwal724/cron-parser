package com.deliveroo.cron.parsers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronUnit;

public abstract class Parser {

    protected List<Integer> getCronTimings(CronUnit cronUnit, Integer increment) {
        return getCronTimings(cronUnit.getStartRange(), cronUnit.getEndRange(), increment);
    }

    protected List<Integer> getCronTimings(Integer startRange, Integer endRange, Integer increment) {
        return IntStream
                .iterate(startRange, next -> next <= endRange, next -> next + increment)
                .boxed()
                .collect(Collectors.toList());
    }

    public boolean isInRange(final Integer startRange, final Integer endRange, final int value) {
        return value >= startRange && value <= endRange;
    }

    public abstract List<Integer> getTimings(CronUnit cronUnit, String cronExpression) throws InvalidCronExpression;

    public abstract String getRegex();
}
