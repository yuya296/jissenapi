package com.example.jissenapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Deal {
    @Id @GeneratedValue @Getter
    private Long id;

    @Getter @Setter
    private LocalDate timestamp;

    @Getter @Setter
    private String code;

    @Getter @Setter
    private BigDecimal quantity;

    @Getter @Setter
    private BigDecimal bookValue;

    public Deal(String code, double quantity, double bookValue) {
        this.timestamp = LocalDate.now();
        this.code = code;
        this.quantity = new BigDecimal(quantity);
        this.bookValue = new BigDecimal(bookValue);
    }

    public Deal(Issue issue, double quantity, double bookValue) {
        this(issue.getCode(), quantity, bookValue);
    }

}
