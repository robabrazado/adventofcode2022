package com.robabrazado.aoc2022.day21;

import java.math.BigInteger;
import java.util.Arrays;

public class Monkey {
	private final Type type;
	private final String name;
	private BigInteger number = null;
	private String[] operationNames = null;
	private String operator = null;
	
	private BigInteger[] operands = null;
	
	public Monkey(String name, String operator, String[] valueNames) {
		this.name = name;
		this.type = Type.OPERATION;
		this.operator = operator;
		this.operationNames = Arrays.copyOf(valueNames, valueNames.length);
		this.operands = new BigInteger[2];
		return;
	}
	
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
	
	public boolean isRoot() {
		return this.name.equals("root");
	}
	
	public boolean isHuman() {
		return this.name.equals("humn");
	}
	
	public boolean dependsOn(String name) {
		return this.operationNames != null && this.operationNames.length > 1 &&
			(this.operationNames[0].equals(name) || this.operationNames[1].equals(name));
	}
	
	public void replaceDependent(String replace, String with) {
		if (!this.dependsOn(replace)) {
			throw new IllegalArgumentException("Monkey " + this.name + " does not depend on monkey " + replace);
		}
		int replaceIdx = 0;
		if (this.operationNames[1].equals(replace)) {
			replaceIdx = 1;
		}
		this.operationNames[replaceIdx] = with;
		return;
	}
	
	public String toString() {
		StringBuilder strb = new StringBuilder(this.name);
		strb.append(": ");
		switch (this.type) {
		case NUMBER:
			strb.append(this.number.toString());
			break;
		case OPERATION:
			strb.append(this.operationNames[0]).append(" ").append(this.operator).append(" ").append(this.operationNames[1]);
			break;
		default:
			throw new IllegalStateException("Unrecognized type: " + this.type);
		}
		return strb.toString();
	}
	
	// Returns a new node expressed in terms of the specified name
	public Monkey equivalent(String name) {
		if (this.isRoot() || this.isHuman()) {
			throw new IllegalStateException("Do not call this method on root or humn");
		}
		switch (this.type) {
		case NUMBER:
			if (!this.name.equals(name)) {
				throw new IllegalArgumentException("Monkey " + name + " is a number monkey and can only be equivalent as itself");
			}
			if (this.number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
				throw new IllegalStateException("Monkey " + name + " has a value too big to make equivalent");
			}
			return new Monkey(name, this.number.intValue());
		case OPERATION:
			String left = this.operationNames[0];
			String right = this.operationNames[1];
			
			if (left.equals(name)) {
				return this.equivalentLeft();
			} else if (right.equals(name)) {
				return this.equivalentRight();
			} else {
				throw new IllegalArgumentException("Monkey " + this.name + " does not depend on monkey " + name);
			}
		default:
			throw new IllegalStateException("Unrecognized type for equivalence: " + this.type);
		}
		
	}
	
	private Monkey equivalentLeft() {
		switch (this.operator) {
		case "+":
			return new Monkey(this.operationNames[0], "-", new String[] {this.name, this.operationNames[1]});
		case "-":
			return new Monkey(this.operationNames[0], "+", new String[] {this.name, this.operationNames[1]});
		case "*":
			return new Monkey(this.operationNames[0], "/", new String[] {this.name, this.operationNames[1]});
		case "/":
			return new Monkey(this.operationNames[0], "*", new String[] {this.name, this.operationNames[1]});
		default:
			throw new IllegalStateException("Unrecognized operator: " + this.operator);
		}
	}
	
	private Monkey equivalentRight() {
		switch (this.operator) {
		case "+":
			return new Monkey(this.operationNames[1], "-", new String[] {this.name, this.operationNames[0]});
		case "-":
			return new Monkey(this.operationNames[1], "-", new String[] {this.operationNames[0], this.name});
		case "*":
			return new Monkey(this.operationNames[1], "/", new String[] {this.name, this.operationNames[0]});
		case "/":
			return new Monkey(this.operationNames[1], "/", new String[] {this.operationNames[0], this.name});
		default:
			throw new IllegalStateException("Unrecognized operator: " + this.operator);
		}
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
