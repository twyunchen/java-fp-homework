package com.yunchen.functionalprogramming;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.function.Function;

@SpringBootApplication
public class FunctionalProgrammingApplication implements CommandLineRunner {

	private final Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice;

	public FunctionalProgrammingApplication(Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice) {
		this.calculateFinalPriceForListingPrice = calculateFinalPriceForListingPrice;
	}

	public static void main(String[] args) {
		SpringApplication.run(FunctionalProgrammingApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(calcFinalPrice(new BigDecimal(100)));
		System.out.println(calcFinalPrice(new BigDecimal(90)));
	}

	private BigDecimal calcFinalPrice(BigDecimal listingPrice) {
		return calculateFinalPriceForListingPrice.apply(listingPrice);
	}

}
