package com.deliveroo.cron.parsers.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronUnit;
import com.deliveroo.cron.parsers.Parser;

public class ParserStrategyManager implements IParserStrategyManager {

    private final Set<Parser> parsers = new HashSet<>();

    private static final String COMMA_DELIMITER = ",";

    @Override
    public void registerStrategy(Parser parser) {
        parsers.add(parser);
    }

    @Override
    public boolean removeStrategy(Parser parser) {
        return parsers.remove(parser);
    }

    @Override
    public List<Integer> parse(CronUnit cronUnit, String cronExpression) {

        var cronExpressions = cronExpression.split(COMMA_DELIMITER);

        var allTimingUnits = Arrays.stream(cronExpressions).map(expression -> {
                    var parser = findParser(cronUnit, expression);
                    return parser.getTimings(cronUnit, expression);
                }).flatMap(Collection::stream)
                .collect(Collectors.toList());

        var sortedTimingUnits = allTimingUnits
                .stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return sortedTimingUnits;
    }

    private Parser findParser(CronUnit cronUnit, String cronExpression) {
        Optional<Parser> parserOptional = parsers
                .stream()
                .filter(parser -> cronExpression.matches(parser.getRegex()))
                .findFirst();

        return parserOptional
                .orElseThrow(
                        () -> new InvalidCronExpression(cronUnit, cronExpression, "Invalid expression passed. Not able to parse the given input.")
                );
    }
}
