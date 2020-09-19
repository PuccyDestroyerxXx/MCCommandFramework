import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.SupCommand;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class SupCommandMock extends SupCommand {

	LupusCommand[] subCommands = {
		new LupusCommandMock()
	};
	public SupCommandMock() {
		super(
				RandomStringUtils.random(new Random().nextInt(200)),
				RandomStringUtils.random(new Random().nextInt(200)),
				RandomStringUtils.random(new Random().nextInt(200)),
				0,
				new LupusCommand[]{
						new LupusCommandMock()
				});
	}

	@Override
	protected boolean optionalOperations(CommandSender sender, String[] args) {
		System.out.println("Name:"+getName());
		System.out.println("Desc:"+getDescription());
		System.out.println("Usage:"+getDescription());
		return false;
	}
}
