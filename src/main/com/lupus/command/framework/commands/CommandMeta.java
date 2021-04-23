package com.lupus.command.framework.commands;

import com.lupus.command.framework.messages.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandMeta {
	private String name;
	private String usage;
	private String description;
	private String permissionDenied = Message.NO_PERMISSION.toString();
	private final List<String> permissions = new ArrayList<>();
	private final List<String> aliases = new ArrayList<>();
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
