package com.robabrazado.aoc2022.day25;

import java.math.BigInteger;

public class BigSnafu {
	private static final BigInteger BIGINT_FIVE = BigInteger.valueOf(5);
	private static char[] SNAFU_DIGITS = {'=', '-', '0', '1', '2'};
	
	private final BigInteger value;
	
	private String snafuCache = null;
	
	public BigSnafu(BigInteger val) {
		this.value = val;
		return;
	}
	
	public BigSnafu(String snafuString) {
		this.value = intValue(snafuString);
		return;
	}
	
	public BigSnafu(long val) {
		this(BigInteger.valueOf(val));
	}
	
	public BigInteger intValue() {
		return this.value;
	}
	
	public static BigInteger intValue(String snafuString) {
		BigInteger result = BigInteger.ZERO;
		int len = snafuString.length();
		for (int i = 0; i < len; i++) {
			char c = snafuString.charAt(len - 1 - i);
			result = result.add(BIGINT_FIVE.pow(i).multiply(BigInteger.valueOf(fromSnafuDigit(c))));
		}
		return result;
	}
	
	public BigSnafu add(BigSnafu other) {
		return new BigSnafu(this.value.add(other.value));
	}
	
	// In the real world, at least, this could probably benefit from a lot of caching. We'll see how it goes.
	public String toSnafu() {
		if (snafuCache == null) {
			if (this.value.equals(BigInteger.ZERO)) { // A man can dream
				this.snafuCache = "0";
			} else {
				StringBuilder digits = new StringBuilder();
				BigInteger remaining = this.value;
				
				// Find the position of the most significant digit
				BigInteger magnitude = remaining.abs();
				int msdIndex = 0;
				BigInteger maxAtThisIndex = BIGINT_FIVE.pow(msdIndex).multiply(BigInteger.TWO);
				while (maxAtThisIndex.compareTo(magnitude) < 0) {
					maxAtThisIndex = maxAtThisIndex.add(BIGINT_FIVE.pow(++msdIndex).multiply(BigInteger.TWO));
				}
				
				// Loop from most significant digit to least and fill in values
				for (int digitIndex = msdIndex; digitIndex >= 0; digitIndex--) {
					BigInteger increment = BIGINT_FIVE.pow(digitIndex);
					BigInteger prevRange = digitIndex == 0 ? BigInteger.ZERO : increment.subtract(BigInteger.ONE).divide(BigInteger.TWO);
					BigInteger remainingAbs = remaining.abs();
					
					int digitValue;
					if (remainingAbs.compareTo(prevRange) <= 0) {
						digitValue = 0;
					} else if (remaining.abs().compareTo(increment.add(prevRange)) <= 0) {
						digitValue = remaining.signum();
					} else {
						digitValue = remaining.signum() * 2;
					}
					remaining = remaining.subtract(increment.multiply(BigInteger.valueOf(digitValue)));
					digits.append(toSnafuDigit(digitValue));
				}
				
				this.snafuCache = digits.toString();
			}
		}
		return this.snafuCache;
	}
	
	// This is ugly, but it's faster than searching arrays, etc.
	private static int fromSnafuDigit(char c) {
		int retVal;
		switch (c) {
		case '=':
			retVal = -2;
			break;
		case '-':
			retVal = -1;
			break;
		case '0':
			retVal = 0;
			break;
		case '1':
			retVal = 1;
			break;
		case '2':
			retVal = 2;
			break;
		default:
			throw new IllegalArgumentException("Invalid SNAFU digit: " + c);
		}
		return retVal;
	}
	
	private static char toSnafuDigit(int i) {
		return SNAFU_DIGITS[i + 2];
	}
	
	@Override
	public String toString() {
		return this.toSnafu();
	}
}
