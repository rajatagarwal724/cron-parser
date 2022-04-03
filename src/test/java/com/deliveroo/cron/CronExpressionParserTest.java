//package com.deliveroo.cron;
//
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import com.deliveroo.cron.exception.InvalidCronExpression;
//import com.deliveroo.cron.model.CronParsedResponse;
//import com.deliveroo.cron.parsers.BoundIntervalsParser;
//import com.deliveroo.cron.parsers.FixedTimingsParser;
//import com.deliveroo.cron.parsers.NthIntervalParser;
//import com.deliveroo.cron.parsers.NumberParser;
//import com.deliveroo.cron.parsers.StarParser;
//import com.deliveroo.cron.parsers.manager.ParserStrategyManager;
//
//public class CronExpressionParserTest {
//    private static CronExpressionParser parser;
//
//    @BeforeAll
//    public static void init() {
//        ParserStrategyManager manager = new ParserStrategyManager();
//        manager.registerStrategy(new StarParser());
//        manager.registerStrategy(new NthIntervalParser());
//        manager.registerStrategy(new NumberParser());
//        manager.registerStrategy(new BoundIntervalsParser());
//        manager.registerStrategy(new FixedTimingsParser());
//        parser = new CronExpressionParser(manager);
//    }
//
//    @Test
//    public void testIfCronExpressionIsWorkingAsExpected() {
//        init();
//        String expected =
//                "minute        0 15 30 45\n" +
//                "hour          0\n" +
//                "day of month  1 15\n" +
//                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
//                "day of week   1 2 3 4 5\n" +
//                "command       /usr/bin/find\n";
//
//        String cronExpression = "*/15 0 1,15 * 1-5 /usr/bin/find";
//
//        CronParsedResponse response = parser.parseString(cronExpression);
//        Assertions.assertTrue(expected.equals(response.toString()));
//    }
//
//    private static Stream<Arguments> exceptionDataForParser() {
//        return Stream.of(
//                Arguments.of("**/15 0 1,15 * 1-5 /usr/bin/find"),
//                Arguments.of("*/67 0 1,15 * 1-5 /usr/bin/find"),
//                Arguments.of("* 0 1,15 * 10 /usr/bin/find"),
//                Arguments.of("* 0 1,* * 1-5 /usr/bin/find")
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("exceptionDataForParser")
//    public void testIfCronExpressionParserThrowsExceptionForInvalidInput(String cronExpression) {
//        Assertions.assertThrows(InvalidCronExpression.class, () -> {
//            parser.parseString(cronExpression);
//        });
//    }
//}
