import com.lupus.command.framework.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class PlayerCommandMock extends PlayerCommand {
	public PlayerCommandMock(){
		super("TestPlayer",0);
	}
	public static int b=0;
	@Override
	protected void run(Player executor, String[] args) {
		b = 1;
	}
}
