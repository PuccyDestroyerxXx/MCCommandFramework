import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.PlayerCommand;
import com.lupus.command.framework.commands.PlayerSupCommand;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;

public class TestCommands {
	TestLupusCommand lupusCommand = new TestLupusCommand();
	TestSupCommand supCommand = new TestSupCommand();
	public static boolean run = true;
	public static boolean runPlayer = true;
	public static ServerMock mock = MockBukkit.mock();
	@Test
	public void TestLupusCommand_Success(){

		lupusCommand.run(mock.getConsoleSender(),new String[]{});
		Assert.assertFalse(run);
		LupusCommand[] cmds = supCommand.getSubCommands();

		// Check if we have no suprises at all
		Assert.assertEquals(cmds.length,1);
		// Check if we have no suprises with executing commands in SupCommand
		String name = cmds[0].getName();

		supCommand.run(mock.getConsoleSender(),new String[]{name});

		Assert.assertTrue(run);
	}
	@Test
	public void TestPlayerCommands_Success(){
		PlayerCommand playerCommand = new TestPlayerCommand();
		Player player = mock.addPlayer();
		playerCommand.run(player,new String[]{});
		Assert.assertFalse(runPlayer);

		PlayerSupCommand supCommand = new TestSupPlayerCMD();
		supCommand.run(player,new String[]{"TestPlayer"});
		Assert.assertTrue(runPlayer);

	}
}
