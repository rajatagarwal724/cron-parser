package com.deliveroo.cron.parsers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.deliveroo.cron.model.CronUnit;

public class StarParserTest {

    StarParser parser = new StarParser();

    private static Stream<Arguments> dataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "*", IntStream.range(0, 60).boxed().collect(Collectors.toList())),
                Arguments.of(CronUnit.HOUR, "*", IntStream.range(0, 24).boxed().collect(Collectors.toList())),
                Arguments.of(CronUnit.DAY_OF_MONTH, "*", IntStream.range(1, 32).boxed().collect(Collectors.toList())),
                Arguments.of(CronUnit.MONTH, "*", IntStream.range(1, 13).boxed().collect(Collectors.toList())),
                Arguments.of(CronUnit.DAY_OF_WEEK, "*", IntStream.range(1, 8).boxed().collect(Collectors.toList()))
        );
    }

    @ParameterizedTest
    @MethodSource("dataForParser")
    void testIfStarParserThrowsExceptionForInvalidInput(CronUnit timeUnit, String cronExpression, List<Integer> expectedList) {
        List<Integer> actualList = parser.getTimings(timeUnit, cronExpression);
        Assertions.assertTrue(actualList.equals(expectedList));
    }
}
