package com.deliveroo.cron.parsers.manager;

import java.util.List;

import com.deliveroo.cron.model.CronUnit;
import com.deliveroo.cron.parsers.Parser;

public interface IParserStrategyManager {

    void registerStrategy(Parser parser);

    boolean removeStrategy(Parser parser);

    List<Integer> parse(CronUnit cronUnit, String cronExpression);
}
