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

public class BoundIntervalsParserTest {

    BoundIntervalsParser parser = new BoundIntervalsParser();

    private static Stream<Arguments> dataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "1-5", Arrays.asList(1, 2, 3, 4, 5)),
                Arguments.of(CronUnit.HOUR, "1-5", Arrays.asList(1, 2, 3, 4, 5)),
                Arguments.of(CronUnit.DAY_OF_MONTH, "1-5", Arrays.asList(1, 2, 3, 4, 5)),
                Arguments.of(CronUnit.MONTH, "1-5", Arrays.asList(1, 2, 3, 4, 5)),
                Arguments.of(CronUnit.DAY_OF_WEEK, "1-5", Arrays.asList(1, 2, 3, 4, 5)),
                Arguments.of(CronUnit.DAY_OF_WEEK, "1-2,3-5", Arrays.asList(1, 2, 3, 4, 5))
        );
    }

    private static Stream<Arguments> exceptionDataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "3-2"),
                Arguments.of(CronUnit.MINUTE, "10-61"),
                Arguments.of(CronUnit.HOUR, "3-2"),
                Arguments.of(CronUnit.DAY_OF_MONTH, "3-2"),
                Arguments.of(CronUnit.MONTH, "3-2"),
                Arguments.of(CronUnit.DAY_OF_WEEK, "3-2"),
                Arguments.of(CronUnit.DAY_OF_WEEK, "1-2,3-2")
        );
    }

    @ParameterizedTest
    @MethodSource("dataForParser")
    void testIfBoundIntervalsParserIsWorkingAsExpected(CronUnit timeUnit, String cronExpression, List<Integer> expectedList) {
        List<Integer> actualList = parser.getTimings(timeUnit, cronExpression);
        Assertions.assertTrue(actualList.equals(expectedList));
    }

    @ParameterizedTest
    @MethodSource("exceptionDataForParser")
    void testIfBoundIntervalsThrowsExceptionForInvalidInput(CronUnit timeUnit, String cronExpression) {
        Assertions.assertThrows(InvalidCronExpression.class, () -> {
            parser.getTimings(timeUnit, cronExpression);
        });
    }
}