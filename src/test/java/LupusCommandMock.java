import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.arguments.ArgumentList;
import org.bukkit.command.CommandSender;

public class LupusCommandMock extends LupusCommand {
	public LupusCommandMock(){
		super("TestCMD",0);

	}
	@Override
	public void run(CommandSender sender, ArgumentList argumentList) {

	}
}
