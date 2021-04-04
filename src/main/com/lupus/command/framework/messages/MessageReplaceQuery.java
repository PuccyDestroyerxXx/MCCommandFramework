package com.lupus.command.framework.messages;

import java.util.HashMap;
import java.util.Map;

public class MessageReplaceQuery {
	private final HashMap<String,String> query = new HashMap<>();
	public MessageReplaceQuery add(String key,String value){
		query.put(key, value);
		return this;
	}
	public Map<String,String> getQuery(){
		return query;
	}
}
