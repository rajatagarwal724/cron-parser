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

public class NthIntervalParserTest {
    NthIntervalParser parser = new NthIntervalParser();

    private static Stream<Arguments> dataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "*/15", Arrays.asList(0, 15, 30, 45)),
                Arguments.of(CronUnit.HOUR,   "*/12", Arrays.asList(0,12)),
                Arguments.of(CronUnit.DAY_OF_MONTH, "*/10", Arrays.asList(1,11,21,31)),
                Arguments.of(CronUnit.MONTH,"*/2", Arrays.asList(1,3,5,7,9,11)),
                Arguments.of(CronUnit.DAY_OF_WEEK, "*/1", Arrays.asList(1,2,3,4,5,6,7))
        );
    }

    private static Stream<Arguments> exceptionDataForParser() {
        return Stream.of(
                Arguments.of(CronUnit.MINUTE, "*/70"),
                Arguments.of(CronUnit.HOUR,   "*/25"),
                Arguments.of(CronUnit.DAY_OF_MONTH, "*/45"),
                Arguments.of(CronUnit.MONTH,"*/22"),
                Arguments.of(CronUnit.DAY_OF_WEEK, "*/10")
        );
    }

    @ParameterizedTest
    @MethodSource("dataForParser")
    void testIfNthIntervalParserIsWorkingAsExpected(CronUnit timeUnit, String cronExpression, List<Integer> expectedList) {
        List<Integer> actualList = parser.getTimings(timeUnit, cronExpression);
        Assertions.assertTrue(actualList.equals(expectedList));
    }

    @ParameterizedTest
    @MethodSource("exceptionDataForParser")
    void testIfNthIntervalParserThrowsExceptionForInvalidInput(CronUnit timeUnit, String cronExpression) {
        Assertions.assertThrows(InvalidCronExpression.class, () -> {
            parser.getTimings(timeUnit, cronExpression);
        });
    }
}
