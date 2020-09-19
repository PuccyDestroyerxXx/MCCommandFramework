import com.lupus.command.framework.commands.LupusCommand;
import org.bukkit.command.CommandSender;

public class LupusCommandMock extends LupusCommand {
	public LupusCommandMock(){
		super("TestCMD",0);

	}
	@Override
	public void run(CommandSender sender, String[] args) {
		System.out.println("Running command");
		TestCommands.run = !TestCommands.run;
		System.out.println("Command stopped");
	}
}
