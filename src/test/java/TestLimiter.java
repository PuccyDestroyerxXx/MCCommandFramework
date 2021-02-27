import com.lupus.command.framework.commands.CommandLimiter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class TestLimiter {
	@Test
	public void testLimiter(){
		UUID randomPlayerUUID = UUID.randomUUID();
		CommandLimiter.INSTANCE.addLimit(randomPlayerUUID,"TestCMD",System.currentTimeMillis()+10000);
		assertTrue(CommandLimiter.INSTANCE.hasLimit(randomPlayerUUID,"TestCMD"));
	}
}
