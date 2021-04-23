package com.lupus.command.framework.messages;

import com.lupus.command.LupusCommandFrameWork;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

public enum Message {
	HELPER_COMMAND,
	PLAYER_ONLY,
	TIME_LIMIT,
	USAGE_DESC_TEMPLATE,
	USAGE_PATTERN,
	COMMAND_SHOW_PATTERN,
	UINTEGER_ERROR,
	INCORRECT_ARGUMENT,
	;
	private String text;

	Message(){
		text = this.name();
	}

	public static void load(){
		FileConfiguration conf;
		conf = getConfig(new File(LupusCommandFrameWork.getInstance().getDataFolder(),"messages.yml"));

		for (Message value : values()) {
			value.setText(
					conf.getString(
							value.name(),
							value.name()
					)
			);
		}
	}
	private static FileConfiguration getConfig(File file){
		LupusCommandFrameWork.getInstance().saveResource("messages.yml",false);
		return YamlConfiguration.loadConfiguration(file);
	}
	private void setText(String textReplacement){
		if (textReplacement.isEmpty()) {
			return;
		}
		this.text = ChatColor.translateAlternateColorCodes('&',textReplacement.replace("\\n","\n"));
	}
	public String toString(){
		return text;
	}
	public String toString(MessageReplaceQuery query){
		return toString(query.getQuery());
	}
	public String toString(Map<String,String> replaceMap){
		String copiedText = text;
		for (Map.Entry<String, String> stringStringEntry : replaceMap.entrySet())
			copiedText = copiedText.replace(
					"%"+stringStringEntry.getKey()+"%",
					stringStringEntry.getValue()
			);
		return ChatColor.translateAlternateColorCodes('&',copiedText);
	}
}
