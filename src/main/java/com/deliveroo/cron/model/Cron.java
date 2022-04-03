package com.deliveroo.cron.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import static java.lang.String.format;

public class Cron {

    private final List<Pair<CronUnit, List<Integer>>> fields;

    private String asString;

    public Cron(final List<Pair<CronUnit, List<Integer>>> fields) {
        this.fields = fields;
    }

    public String asString() {
        if (StringUtils.isNotBlank(asString)) {
            return asString;
        }
        StringBuffer parsedCronBuilder = new StringBuffer();

        fields.forEach((cronField) -> parsedCronBuilder.append(format("%-14s%s\n", cronField.getLeft().getName(), printList(cronField.getRight()))));
        asString = parsedCronBuilder.toString();
        return asString;
    }

    private String printList(List<Integer> integers) {
        return integers.stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
