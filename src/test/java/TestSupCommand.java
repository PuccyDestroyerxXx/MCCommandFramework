import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.SupCommand;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class TestSupCommand extends SupCommand {

	LupusCommand[] subCommands = {
		new TestLupusCommand()
	};
	public TestSupCommand() {
		super(
				RandomStringUtils.random(new Random().nextInt(200)),
				RandomStringUtils.random(new Random().nextInt(200)),
				RandomStringUtils.random(new Random().nextInt(200)),
				0,
				new LupusCommand[]{
						new TestLupusCommand()
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
