package com.yunchen.functionalprogramming;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@Component
public class ApplyDiscountFunction implements BiFunction<BigDecimal, BigDecimal, BigDecimal> {

    @Override
    public BigDecimal apply(BigDecimal discountRate, BigDecimal amount) {
        return amount.add(amount.multiply(discountRate).negate());
    }
}
