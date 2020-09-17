import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.PlayerSupCommand;

public class TestSupPlayerCMD extends PlayerSupCommand {
	public TestSupPlayerCMD(){
		super("TestPlayerSup",0,new LupusCommand[]{
				new TestPlayerCommand()
		});
	}
}
