package com.example.jissenapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Position {
    @Id
    @Getter @Setter
    private String code;

    @Getter @Setter
    private BigDecimal quantity;

    @Getter @Setter
    private BigDecimal bookValue;

    public Position(String code, double quantity, double bookValue/*, double marketValue*/) {
        this.code = code;
        this.quantity = new BigDecimal(quantity);
        this.bookValue = new BigDecimal(bookValue);
    }

    public Position(Issue issue, double quantity, double bookValue) {
        this(issue.getCode(), quantity, bookValue);
    }
}
