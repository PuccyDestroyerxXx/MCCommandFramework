package com.lupus.command.framework.commands.arguments;

import com.lupus.command.framework.messages.Message;

public class UInteger {
	private int integer;
	public UInteger(int integer) throws Exception {
		if (integer < 0)
			throw new Exception(Message.UINTEGER_ERROR.toString());
		this.integer = integer;
	}

	public int getInteger() {
		return integer;
	}
}
