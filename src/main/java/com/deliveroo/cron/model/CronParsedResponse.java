package com.deliveroo.cron.model;


import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import lombok.AllArgsConstructor;
import lombok.Data;
import static java.lang.String.format;

@Data
@AllArgsConstructor
public class CronParsedResponse {

    private Cron cron;
    private String command;

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(cron.asString());
//        cron.forEach((pair) -> b.append(format("%-14s%s\n", pair.left.getName(), printList(pair.right))));

        b.append(format("%-14s%s\n", "command", command));
        return b.toString();
    }

    private String printList(List<Integer> integers) {
        return integers.stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
