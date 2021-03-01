package com.lupus.command.framework.commands.arguments;

import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;

public class ArgumentList extends ArrayList<String> {

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
				try {
					E valueToReturn = (E) value.getObject(get(idx));
					if (valueToReturn == null)
						break;
					return valueToReturn;
				}
				catch(Exception ex){
					break;
				}
			}
		}
		throw new Exception("Nieprawidłowy argument nr "+(idx+1));
	}
}
