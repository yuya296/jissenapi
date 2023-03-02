package com.example.jissenapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Issue {

    @Id
    @Getter @Setter
    private String code;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private BigDecimal rate;

    @Getter @Setter
    private LocalDate maturity;

    @Getter @Setter
    private int couponTimes;

    @Getter @Setter
    private BigDecimal marketValue;

    public Issue(String code, String name, BigDecimal rate, LocalDate maturity, int couponTimes) {
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.maturity = maturity;
        this.couponTimes = couponTimes;
    }

    public Issue(String code, String name, double rate, int yyyy, int mm, int dd, int couponTimes) {
        this(code, name, new BigDecimal(rate), LocalDate.of(yyyy,mm,dd), couponTimes);
    }
}