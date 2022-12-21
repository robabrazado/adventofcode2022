package com.robabrazado.aoc2022.day21;

import java.math.BigInteger;

public class Monkey {
	private final Type type;
	private final String name;
	private BigInteger number = null;
	private String[] operationNames = null;
	private String operator = null;
	
	private BigInteger[] operands = null;
	
	public Monkey(String name, String operation) {
		this.name = name;
		this.type = Type.OPERATION;
		this.operationNames = new String[2];
		this.operands = new BigInteger[2];
		
		String[] parts = operation.split(" ");
		this.operationNames[0] = parts[0];
		this.operator = parts[1];
		this.operationNames[1]= parts[2];
		return;
	}
	
	public Monkey(String name, int number) {
		this.name = name;
		this.type = Type.NUMBER;
		this.number = BigInteger.valueOf(number);
		return;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isReady() {
		return this.number != null;
	}
	
	public BigInteger shout() {
		if (number == null) {
			throw new IllegalStateException("Monkey is not ready to shout");
		}
		return this.number;
	}
	
	public String getValueName(int i) {
		return this.operationNames[i];
	}
	
	public boolean hasValue(int i) {
		return this.operands[i] != null;
	}
	
	public void setValue(int i, BigInteger val) {
		this.operands[i] = val;
		this.evaluate();
		return;
	}
	
	private void evaluate() {
		BigInteger operand1 = this.operands[0];
		BigInteger operand2 = this.operands[1];
		
		if (operand1 != null && operand2 != null) {
			switch (this.operator) {
			case "+":
				this.number = operand1.add(operand2);
				break;
			case "-":
				this.number = operand1.subtract(operand2);
				break;
			case "*":
				this.number = operand1.multiply(operand2);
				break;
			case "/":
				this.number = operand1.divide(operand2);
				break;
			default:
				throw new RuntimeException("Unrecognized operator: " + this.operator);
			}
		}
		return;
	}
	
	public enum Type {
		NUMBER, OPERATION;
	}
}
