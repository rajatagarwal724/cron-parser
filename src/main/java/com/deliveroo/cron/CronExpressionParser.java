package com.deliveroo.cron;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.Cron;
import com.deliveroo.cron.model.CronUnit;
import com.deliveroo.cron.parsers.CronField;
import com.deliveroo.cron.parsers.manager.IParserStrategyManager;

public class CronExpressionParser {

    private final IParserStrategyManager manager;

    public CronExpressionParser(IParserStrategyManager manager) {
        this.manager = manager;
    }

    public Cron parseCronString(String cronExpression) {
        if (StringUtils.isBlank(cronExpression)) {
            throw new InvalidCronExpression("Cron Expression can't be NULL or Empty");
        }

        var cronExpressions = cronExpression.split(StringUtils.SPACE);

        var cronFields = Arrays
                .stream(CronUnit.values())
                .map(toCronField(cronExpressions))
                .collect(Collectors.toList());

        var parsedCrons = cronFields
                .stream()
                .map(toParsedCronPair())
                .collect(Collectors.toList());

        return new Cron(parsedCrons);
    }

    private Function<CronField, Pair<CronUnit, List<Integer>>> toParsedCronPair() {
        return cronField -> new ImmutablePair<>(
                cronField.getCronUnit(),
                manager.parse(cronField.getCronUnit(), cronField.getExpression())
        );
    }

    private Function<CronUnit, CronField> toCronField(String[] cronExpressions) {
        return cronFieldName -> new CronField(
                cronFieldName, cronExpressions[cronFieldName.getOrder()]
        );
    }
}
