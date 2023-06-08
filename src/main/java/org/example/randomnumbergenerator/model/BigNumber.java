package org.example.randomnumbergenerator.model;

import java.math.BigInteger;

public class BigNumber {

	private final BigInteger number;

	public BigNumber(BigInteger number) {
		this.number = number;
	}

	public BigInteger getNumber() {
		return number;
	}
}
