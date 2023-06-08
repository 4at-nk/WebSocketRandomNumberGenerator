package org.example.randomnumbergenerator;

import java.math.BigInteger;
import java.util.UUID;

public class RandomBigIntegerGenerator {

	public BigInteger generate() {
		return new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16);
	}
}
