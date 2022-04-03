package com.deliveroo.cron.exception;

import com.deliveroo.cron.model.CronUnit;

public class InvalidCronExpression extends RuntimeException {

    public InvalidCronExpression(String message) {
        super(message);
    }

    public InvalidCronExpression(CronUnit cronUnit, String cronExpression, String message) {
//        StringBuilder errorMessage = new StringBuilder();
//        errorMessage.append("The expresssion " + cronExpression + " passed for time unit " + timeUnit.getName() + " is invalid. \n");
//        errorMessage.append("Accepted range for time unit " + timeUnit.getName() + " is [" + timeUnit.getStartRange() +"-" + timeUnit.getEndRange() +"]. \n");
//        errorMessage.append("Error message is : " + message);

        throw new InvalidCronExpression(message);
    }
}
