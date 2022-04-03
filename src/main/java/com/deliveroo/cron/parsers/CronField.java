package com.deliveroo.cron.parsers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.deliveroo.cron.model.CronUnit;

public class CronField {

    private final CronUnit cronUnit;

    private final String expression;

    public CronField(final CronUnit field, final String expression) {
        this.cronUnit = field;
        this.expression = expression;
    }

    public static Comparator<CronField> createFieldTypeComparator() {
        return Comparator.comparingInt(o -> o.getCronUnit().getOrder());
    }

    private String printList(List<Integer> integers) {
        return integers.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public CronUnit getCronUnit() {
        return cronUnit;
    }

    public String getExpression() {
        return expression;
    }
}
