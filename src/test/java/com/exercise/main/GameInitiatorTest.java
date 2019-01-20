package com.exercise.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.exercise.models.Board;
import com.exercise.models.Player;
import com.exercise.models.Result;


@PrepareForTest(GameInitiator.class)
@RunWith(PowerMockRunner.class)
public class GameInitiatorTest {

	PrintStream out = mock(PrintStream.class);
	
	GameProcessor gameProcessor = mock(GameProcessor.class);
    
	private void provideInput(String data) {
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		System.setOut(out);
	}

	@Test
	public void validNumberOfPlayes_shouldCongratulateTest() throws Exception {
		
        PowerMockito.whenNew(GameProcessor.class)
            .withAnyArguments().thenReturn(gameProcessor);

		StringBuffer input = new StringBuffer("2\n").append("poorni\n").append("sharu");
		provideInput(input.toString());
		when(gameProcessor.process(Mockito.anyList())).thenReturn(new Result(new Player("poorni",100)));
		GameInitiator.main(null);
		verify(out).println("Congratulations, poorni");
		assertTrue(Board.getChute().equals(getExpectedChutes()));
		assertTrue(Board.getLadder().equals(getExpectedLadders()));
		
		
	}
	
	@Test
	public void validNumberOfPlayes_noWinnerFoundTest() throws Exception {
		
        PowerMockito.whenNew(GameProcessor.class)
            .withAnyArguments().thenReturn(gameProcessor);

		StringBuffer input = new StringBuffer("2\n").append("poorni\n").append("sharu");
		provideInput(input.toString());
		when(gameProcessor.process(Mockito.anyList())).thenReturn(null);
		GameInitiator.main(null);
		verify(out).println("Bad luck, we could'nt find a winner as the amount of tries has lapsed.");
		assertTrue(Board.getChute().equals(getExpectedChutes()));
		assertTrue(Board.getLadder().equals(getExpectedLadders()));
		
	}
	
	@Test
	public void lessThanValidNumberOfPlayers_shouldTerminateTest() {
		StringBuffer input = new StringBuffer("1\n").append("poorni\n").append("sharu");
		provideInput(input.toString());
		GameInitiator.main(null);
		verify(out).println("Terminating! The number of players playing must be >2 and <4");
		verify(gameProcessor,never()).process(Mockito.anyList());
		assertTrue(Board.getChute().equals(getExpectedChutes()));
		assertTrue(Board.getLadder().equals(getExpectedLadders()));
	}
	
	@Test
	public void moreThanValidNumberOfPlayers_shouldTerminateTest() {
		StringBuffer input = new StringBuffer("5\n");
		provideInput(input.toString());
		GameInitiator.main(null);
		verify(out).println("Terminating! The number of players playing must be >2 and <4");
		verify(gameProcessor,never()).process(Mockito.anyList());
		assertTrue(Board.getChute().equals(getExpectedChutes()));
		assertTrue(Board.getLadder().equals(getExpectedLadders()));
		
	}
	
	@Test
	public void invalidKeyPress_shouldTerminateTest() {
		StringBuffer input = new StringBuffer("someinvalidinput\n");
		provideInput(input.toString());
		GameInitiator.main(null);
		verify(out).println("Terminating! Please enter a valid Integer. Try running again");
		verify(gameProcessor,never()).process(Mockito.anyList());
		assertTrue(Board.getChute().equals(getExpectedChutes()));
		assertTrue(Board.getLadder().equals(getExpectedLadders()));
		
	}
	
	private static Map<Integer, Integer> getExpectedChutes() {
		Map<Integer, Integer> chute = new HashMap();
		chute.put(16, 6);
		chute.put(47, 26);
		chute.put(49, 11);
		chute.put(56, 53);
		chute.put(62, 19);
		chute.put(64, 60);
		chute.put(87, 24);
		chute.put(93, 73);
		chute.put(95, 75);
		chute.put(98, 78);
		return chute;
	}

	private static Map<Integer, Integer> getExpectedLadders() {
		Map<Integer, Integer> ladder = new HashMap();
		ladder.put(1, 38);
		ladder.put(4, 14);
		ladder.put(9, 31);
		ladder.put(21, 42);
		ladder.put(28, 84);
		ladder.put(36, 44);
		ladder.put(51, 67);
		ladder.put(71, 91);
		ladder.put(80, 100);
		return ladder;
	}
	
}
