package com.team4.planit.statistic;


import lombok.Getter;

@Getter
public enum StatisticPeriodCode {
    DAY("Day"), WEEK("Week"), MONTH("Month"), YEAR("Year");

    final private String name;
    public String getName() {
        return name;
    }
    private StatisticPeriodCode(String name){
        this.name = name;
    }
}
