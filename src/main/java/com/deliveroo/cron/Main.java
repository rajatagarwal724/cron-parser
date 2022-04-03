package com.deliveroo.cron;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronParsedResponse;
import com.deliveroo.cron.parsers.*;
import com.deliveroo.cron.parsers.manager.IParserStrategyManager;
import com.deliveroo.cron.parsers.manager.ParserStrategyManager;

public class Main {

    private static final CronExpressionParser cronExpressionParser = new CronExpressionParser(createDefaultParser());

    public static void main(String[] args) {
//        if (args.length != 1 || args[0].split(" ").length != 6) {
//            printUsage();
//            System.err.println("ERROR: Arguments passed to the program: " + Arrays.stream(args).collect(Collectors.joining(" ")));
//            return;
//        }

        args = new String[] {"*/15,*/10 0 30 * 1-5 /usr/bin/find"};

        System.out.println("Request : " + args[0]);

        String[] expression = args[0].split(StringUtils.SPACE);

        final String cronExpression = getCronExpression(expression);
        final String command = expression[expression.length - 1];

        try{
            var cron = cronExpressionParser.parseCronString(cronExpression);
            var response = new CronParsedResponse(cron, command);
            System.out.println(response);
        } catch (InvalidCronExpression ex) {
            System.err.println(ex.getMessage());
            System.out.println("\n");
            printUsage();
        }
    }

    private static String getCronExpression(String[] expression) {
        return IntStream.range(0, expression.length - 1)
                .boxed()
                .map(index -> expression[index])
                .collect(Collectors.joining(StringUtils.SPACE));
    }

    private static IParserStrategyManager createDefaultParser() {
        var parserStrategyManager = new ParserStrategyManager();

        var parsers = List.of(
                new StarParser(),
                new NumberParser(),
                new FixedTimingsParser(),
                new NthIntervalParser(),
                new BoundIntervalsParser()
        );

        parsers.forEach(parserStrategyManager::registerStrategy);

        return parserStrategyManager;
    }

    private static void printUsage() {
        System.out.println("USAGE: ");
        System.out.println("Pass the cron expression arguments in the same order");
        System.out.println("[minute] [hour] [day of month] [month] [day of week] [command]");
        System.out.println("Example: */15 0 1,15 * 1-5 /usr/bin/find");
        System.out.println();
    }
}
