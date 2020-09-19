import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.PlayerSupCommand;

public class SupPlayerCMDMock extends PlayerSupCommand {
	public SupPlayerCMDMock(){
		super("TestPlayerSup",0,new LupusCommand[]{
				new PlayerCommandMock()
		});
	}
}
