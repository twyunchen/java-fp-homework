package com.yunchen.functionalprogramming;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.BiPredicate;

@Component
public class ApplyDiscountRule implements BiPredicate<BigDecimal, BigDecimal> {

    @Override
    public boolean test(BigDecimal canApplyDiscountMinAmount, BigDecimal amount) {
        return amount.compareTo(canApplyDiscountMinAmount) >= 0 ;
    }
}
