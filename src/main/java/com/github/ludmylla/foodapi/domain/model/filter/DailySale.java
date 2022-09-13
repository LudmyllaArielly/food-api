package com.github.ludmylla.foodapi.domain.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailySale {

    private Date date;
    private Long priceTotal;
    private BigDecimal totalBilled;

}
