package com.deliveroo.cron.model;

/**
 * Enumerates cron field names.
 */
public enum CronUnit {

    MINUTE(0, "minute", 0, 59),
    HOUR(1, "hour", 0, 23),
    DAY_OF_MONTH(2, "day of month",1, 31),
    MONTH(3, "month",1, 12),
    DAY_OF_WEEK(4, "day of week",1, 7);

    private final int order;

    private final String name;
    private final int startRange;
    private final int endRange;

    /**
     * Constructor.
     *  @param order - specified order between cron fields.
     *              Used to be able to compare fields and sort them
     * @param name
     * @param startRange
     * @param endRange
     */
    CronUnit(final int order, final String name, final int startRange, final int endRange) {
        this.order = order;
        this.name = name;
        this.startRange = startRange;
        this.endRange = endRange;
    }

    /**
     * Returns the order number that corresponds to the field.
     *
     * @return order number - int
     */
    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public int getStartRange() {
        return startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public boolean isInRange(final int value) {
        return value >= getStartRange() && value <= getEndRange();
    }
}
