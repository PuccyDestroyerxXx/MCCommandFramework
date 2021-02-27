package com.lupus.command.framework.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandMeta {
	private String name;
	private String usage;
	private String description;
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

	public CommandMeta addAlias(String alias) {
		aliases.add(alias);
		return this;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public List<String> getPermissions() {
		return permissions;
	}
}
