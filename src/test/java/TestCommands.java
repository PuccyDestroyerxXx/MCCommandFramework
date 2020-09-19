import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.PlayerCommand;
import com.lupus.command.framework.commands.PlayerSupCommand;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {
	LupusCommandMock lupusCommand = new LupusCommandMock();
	SupCommandMock supCommand = new SupCommandMock();
	public static boolean run = true;
	public static boolean runPlayer = true;
	public static ServerMock mock = MockBukkit.mock();
	@Test
	public void LupusCommandMock_RunVariableNegate_Success(){
		run = true;
		lupusCommand.run(mock.getConsoleSender(),new String[]{});
		assertFalse(run);
	}
	@Test
	public void SupCommandMock_RunSubCommand_Success(){
		run = false;
		LupusCommand[] cmds = supCommand.getSubCommands();

		// Check if we have no suprises at all
		assertEquals(cmds.length,1);
		// Check if we have no suprises with executing commands in SupCommand
		String name = cmds[0].getName();

		supCommand.run(mock.getConsoleSender(),new String[]{name});

		assertTrue(run);
	}
	@Test
	public void MockPlayerCommand_RunPlayerNegate_Success(){
		PlayerCommand playerCommand = new PlayerCommandMock();
		Player player = mock.addPlayer();
		playerCommand.run(player,new String[]{});
		assertFalse(runPlayer);

		PlayerSupCommand supCommand = new SupPlayerCMDMock();
		supCommand.run(player,new String[]{"TestPlayer"});
		assertTrue(runPlayer);

	}
}
