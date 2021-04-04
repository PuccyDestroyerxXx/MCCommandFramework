package com.lupus.command.framework.commands.arguments;

import com.lupus.command.framework.messages.Message;
import com.lupus.command.framework.messages.MessageReplaceQuery;

import java.util.ArrayList;

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
		var mrq = new MessageReplaceQuery().add("arg",(idx+1)+"");
		throw new Exception(Message.INCORRECT_ARGUMENT.toString(mrq));
	}
}
