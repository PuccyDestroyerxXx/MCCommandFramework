package com.lupus.command.framework.commands.arguments;

import com.google.common.collect.ForwardingList;

import java.util.List;

public class ArgumentList extends ForwardingList<String> {

	private boolean isOutOfBounds(int idx){
		return idx < 0 || idx >= size();
	}
	@SuppressWarnings("unchecked")
	public <E> E getArg(Class<? extends E> clazz,int idx) throws Exception {
		if (isOutOfBounds(idx)){
			return null;
		}
		for (ArgumentType value : ArgumentType.values()) {
			if (value.checkIfComplies(clazz)) {
				return (E)value.getObject(get(idx));
			}
		}
		throw new Exception("Nieprawidłowy argument");
	}
	@Override
	protected List<String> delegate() {
		return null;
	}
}
