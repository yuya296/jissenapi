package com.example.jissenapi.model.receiver;

import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class AggregatedDataReceiver {
    @Id
    String      code;
    String      name;
    LocalDate   maturity;
    BigDecimal  rate;
    Integer     couponTimes;
    BigDecimal  quantity;
    BigDecimal  bookValue;
    Optional<BigDecimal> marketValue;
    Optional<BigDecimal> profitAndLoss;


    public AggregatedDataReceiver() {
    }

    public AggregatedDataReceiver(String code,
                                  String name,
                                  LocalDate maturity,
                                  BigDecimal rate,
                                  Integer couponTimes,
                                  BigDecimal quantity,
                                  BigDecimal bookValue,
                                  Optional<BigDecimal> marketValue,
                                  Optional<BigDecimal> profitAndLoss) {
        this.code = code;
        this.name = name;
        this.maturity = maturity;
        this.rate = rate;
        this.couponTimes = couponTimes;
        this.quantity = quantity;
        this.bookValue = bookValue;
        this.marketValue = marketValue;
        this.profitAndLoss = profitAndLoss;
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Integer getCouponTimes() {
        return couponTimes;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public Optional<BigDecimal> getMarketValue() {
        return marketValue;
    }

    public Optional<BigDecimal> getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaturity(LocalDate maturity) {
        this.maturity = maturity;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setCouponTimes(Integer couponTimes) {
        this.couponTimes = couponTimes;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public void setMarketValue(Optional<BigDecimal> marketValue) {
        this.marketValue = marketValue;
    }

    public void setProfitAndLoss(Optional<BigDecimal> profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

}
