package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.tabcompletions.LupusTabFunction;
import com.lupus.command.framework.commands.tabcompletions.TabFunctions;
import com.lupus.command.framework.messages.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CommandMeta {
	private String name;
	private String usage;
	private String description;
	private String permissionDenied = Message.NO_PERMISSION.toString();
	private final List<String> permissions = new ArrayList<>();
	private final List<String> aliases = new ArrayList<>();
	private final HashMap<Integer, LupusTabFunction> tabFunctions = new HashMap<>();
	private int argumentAmount;

	public String getName() {
		return name;
	}

	public int getArgumentAmount() {
		return argumentAmount;
	}

	public String getDescription() {
		return description;
	}

	public String getUsage() {
		return usage;
	}
	public String getPermissionDenied(){
		return permissionDenied;
	}
	public LupusTabFunction getTabFunction(int arg){
		if (arg < 0)
			return null;
		return tabFunctions.get(arg);
	}

	public CommandMeta setName(String name) {
		this.name = name;
		return this;
	}

	public CommandMeta setArgumentAmount(int argumentAmount) {
		this.argumentAmount = argumentAmount;
		return this;
	}

	public CommandMeta setDescription(String description) {
		this.description = description;
		return this;
	}
	public CommandMeta addTabFunction(int argument,LupusTabFunction tabFunction){
		if (tabFunction == null)
			return this;
		tabFunctions.put(argument,tabFunction);
		return this;
	}
	public CommandMeta addTabFunctions(LupusTabFunction[] tabFunctions){
		if (tabFunctions == null)
			return this;
		for (int i = 0, tabFunctionLength = tabFunctions.length; i < tabFunctionLength; i++) {
			LupusTabFunction lupusTabFunction = tabFunctions[i];
			this.tabFunctions.put(i+1, lupusTabFunction);
		}
		return this;
	}
	public CommandMeta addTabFunction(int arg,String id){
		tabFunctions.put(arg,TabFunctions.inst().getTabFunction(id));
		return this;
	}

	public CommandMeta setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public CommandMeta addPermission(String permissionNode) {
		permissions.add(permissionNode);
		return this;
	}
	public CommandMeta addPermissions(Collection<String> permissionsNode) {
		permissions.addAll(permissionsNode);
		return this;
	}

	public CommandMeta addAlias(String alias) {
		aliases.add(alias);
		return this;
	}
	public CommandMeta addAliases(Collection<String> aliases) {
		this.aliases.addAll(aliases);
		return this;
	}
	public CommandMeta clearAliases(){
		this.aliases.clear();
		return this;
	}
	public CommandMeta clearPermissions(){
		this.permissions.clear();
		return this;
	}
	public CommandMeta setPermissionDeniedMessage(String message){
		permissionDenied = message;
		return this;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public List<String> getPermissions() {
		return permissions;
	}
}
