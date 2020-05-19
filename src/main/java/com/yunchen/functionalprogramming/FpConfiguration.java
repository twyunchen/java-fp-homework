package com.yunchen.functionalprogramming;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@Configuration
public class FpConfiguration {

    @Bean
    public Function<BigDecimal, BigDecimal> calcFinalPrice(
            @Value("${discount.rate}") String discountRateString,
            @Value("${tax.rate}") String taxRateString,
            @Value("${discount.minAmount}") String minDiscountAmountString,
            BiPredicate<BigDecimal, BigDecimal> applyDiscountRuleFunction,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscountFunction,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTaxFunction,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        var discountRate = new BigDecimal(discountRateString);
        var taxRate = new BigDecimal(taxRateString);
        var minDiscountAmount = new BigDecimal(minDiscountAmountString);

        var applyDiscount = curry(applyDiscountFunction).apply(discountRate);
        var applyTax = curry(applyTaxFunction).apply(taxRate);
        var applyRule = curry(applyDiscountRuleFunction).apply(minDiscountAmount);

        var calcFinalPrice = curry(calculateFinalPriceFunction)
                .apply(applyRule)
                .apply(applyDiscount)
                .apply(applyTax);

        return calcFinalPrice;
    }

    private Function<BigDecimal, Function<BigDecimal, BigDecimal>> curry(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        return t -> u -> function.apply(t, u);
    }

    private Function<BigDecimal, Predicate<BigDecimal>> curry(BiPredicate<BigDecimal, BigDecimal> function) {
        return minAmount -> listingPrice -> function.test(minAmount, listingPrice);
    }

    private Function<Predicate<BigDecimal>, Function<Function<BigDecimal, BigDecimal>, Function<Function<BigDecimal, BigDecimal>
            , Function<BigDecimal, BigDecimal>>>>
    curry(CalculateFinalPriceFunction function) {
        return rule -> applyDiscount -> applyTax -> listingPrice -> rule.test(listingPrice) ?
                function.apply(applyDiscount, applyTax, listingPrice) :
                function.apply(p -> p, applyTax, listingPrice);
    }
}
