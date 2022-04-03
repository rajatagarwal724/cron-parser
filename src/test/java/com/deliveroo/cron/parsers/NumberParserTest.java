package com.deliveroo.cron.parsers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.deliveroo.cron.exception.InvalidCronExpression;
import com.deliveroo.cron.model.CronUnit;

public class NumberParserTest {
    NumberParser parser = new NumberParser();

    private static Stream<Arguments> dataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "1", Arrays.asList(1)),
                Arguments.of(CronUnit.HOUR, "1", Arrays.asList(1)),
                Arguments.of(CronUnit.DAY_OF_MONTH, "1", Arrays.asList(1)),
                Arguments.of(CronUnit.MONTH, "1", Arrays.asList(1)),
                Arguments.of(CronUnit.DAY_OF_WEEK,"1", Arrays.asList(1))
        );
    }

    private static Stream<Arguments> exceptionDataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "70"),
                Arguments.of(CronUnit.HOUR, "25"),
                Arguments.of(CronUnit.DAY_OF_MONTH, "35"),
                Arguments.of(CronUnit.MONTH, "15"),
                Arguments.of(CronUnit.DAY_OF_WEEK, "8")
        );
    }

    @ParameterizedTest
    @MethodSource("dataForParser")
    void testIfNumberParserIsWorkingAsExpected(CronUnit timeUnit, String cronExpression, List<Integer> expectedList) {
        List<Integer> actualList = parser.getTimings(timeUnit, cronExpression);
        Assertions.assertTrue(actualList.equals(expectedList));
    }

    @ParameterizedTest
    @MethodSource("exceptionDataForParser")
    void testIfNumberParserThrowsExceptionForInvalidInput(CronUnit timeUnit, String cronExpression) {
        Assertions.assertThrows(InvalidCronExpression.class, () -> {
            parser.getTimings(timeUnit, cronExpression);
        });
    }
}
