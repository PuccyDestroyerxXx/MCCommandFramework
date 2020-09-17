import com.lupus.command.framework.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class TestPlayerCommand extends PlayerCommand {
	public TestPlayerCommand(){
		super("TestPlayer",0);
	}
	@Override
	protected void run(Player executor, String[] args) {
		System.out.println("Testing player command");
		TestCommands.runPlayer = !TestCommands.runPlayer;
		System.out.println("End of the test");
	}
}
