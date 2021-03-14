package com.lupus.command.framework.commands.arguments;

public class UInteger {
	private int integer;
	public UInteger(int integer) throws Exception {
		if (integer < 0)
			throw new Exception("Wartość musi być większa od zera");
		this.integer = integer;
	}

	public int getInteger() {
		return integer;
	}
}
