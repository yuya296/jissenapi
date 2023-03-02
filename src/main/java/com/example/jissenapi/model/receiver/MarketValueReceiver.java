package com.example.jissenapi.model.receiver;

import jakarta.persistence.Id;

import java.math.BigDecimal;

public class MarketValueReceiver {
    @Id
    private String code;
    private BigDecimal marketValue;

    public MarketValueReceiver() {}

    public MarketValueReceiver(String code, BigDecimal marketValue) {
        this.code = code;
        this.marketValue = marketValue;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }
}
