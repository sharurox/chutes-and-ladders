package com.exercise.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeastOnce;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.exercise.configuration.GameConfiguration;
import com.exercise.models.Board;
import com.exercise.models.Player;
import com.exercise.models.Result;

/**
 * Test for GameProcessor.java
 *
 */
@PrepareForTest(GameProcessor.class)
@RunWith(PowerMockRunner.class)
public class GameProcessorTest {

	PrintStream out = mock(PrintStream.class);

	Random mockForRandom = mock(Random.class);
	
	List<Player> players = Stream.of(new Player("player1", 0), new Player("player2", 0))
			.collect(Collectors.toList());

	private void setUpScannerInputs(String data) {
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		System.setOut(out);
	}
	
	/**
	 * This test validates that when players correctly roll the dice,no one misses turn.
	 * @throws Exception
	 */
	@Test
	public void correctlyRollDice_noOneMissesTurnTest() throws Exception {
		String validInput = GameConfiguration.ONLY_VALID_KEY + "\n";
		PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mockForRandom);
		when(mockForRandom.nextInt(GameConfiguration.MAXIMUM_RAND_DICE_VALUE)).thenReturn(5);
		StringBuffer input = new StringBuffer();
		// add all valid inputs
		for (int i = 0; i < 50; i++) {
			input.append(validInput);
		}

		setUpScannerInputs(input.toString());
		Board.setChute(setUpChute());
		Board.setLadder(setUpLadder());
		GameProcessor processor = new GameProcessor();
		Result result = processor.process(players);
		verify(out, times(0)).println("You miss your turn as you provided"
				+ " a wrong input by entering :wrongInput");
		verify(out, times(16)).println("player1,press r to roll ");
		verify(out, times(15)).println("player2,press r to roll ");
		verify(out, times(1)).println("Climbing the Ladder to :100");
		verify(out).println("Winner is :player1");
		assertEquals(new Player("player1",100),result.getWinner());
	}

	/**
	 * This test validates that when a player in-correctly rolls the dice,he misses the turn.
	 * @throws Exception
	 */
	@Test
	public void wronglyRollDice_shouldMissTurnTest() throws Exception {
		String validInput = GameConfiguration.ONLY_VALID_KEY + "\n";
		PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mockForRandom);
		when(mockForRandom.nextInt(GameConfiguration.MAXIMUM_RAND_DICE_VALUE)).thenReturn(5);
		// add a wrong output followed by all valid inputs
		StringBuffer input = new StringBuffer("wrongInput\n");
		for (int i = 0; i < 50; i++) {
			input.append(validInput);
		}

		setUpScannerInputs(input.toString());
		Board.setChute(setUpChute());
		Board.setLadder(setUpLadder());
		GameProcessor processor = new GameProcessor();
		Result result = processor.process(players);
		verify(out, times(1)).println("You miss your turn as you provided a "
				+ "wrong input by entering :wrongInput");
		verify(out, times(16)).println("player1,press r to roll ");
		verify(out, times(16)).println("player2,press r to roll ");
		verify(out, times(1)).println("Climbing the Ladder to :100");
		verify(out).println("Winner is :player2");
		assertEquals(new Player("player2", 100),result.getWinner());
	}
	
	/**
	 * This test validates that when the players have exhausted their maximum key presses
	 *  ,no winner is found.
	 * @throws Exception
	 */
	@Test
	public void exhaustMaximumDiceRolls_couldNotFindAWinnerTest() throws Exception {
		String validInput = GameConfiguration.ONLY_VALID_KEY + "\n";
		PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mockForRandom);
		when(mockForRandom.nextInt(GameConfiguration.MAXIMUM_RAND_DICE_VALUE)).thenReturn(5);
		StringBuffer input = new StringBuffer();

		for (int i = 0; i < 200; i++) {
			input.append(validInput);
		}

		setUpScannerInputs(input.toString());
		Board.setChute(setUpChute());
		Board.setLadder(new HashMap<Integer,Integer>(){{
			put(1, 2);
		}});
		GameProcessor processor = new GameProcessor();
		Result result = processor.process(players);
		verify(out, times(0)).println("You miss your turn as you provided "
				+ "a wrong input by entering :wrongInput");
		verify(out, times(50)).println("player1,press r to roll ");
		verify(out, times(50)).println("player2,press r to roll ");
		verify(out, times(0)).println("Climbing the Ladder to :100");
		verify(out).println("Couldnt find a unanimous winner since the count for each player has exceeded 50");
		assertEquals(null, result);
	}

	/**
	 * This test validates that when the players come across ladders,they are climbing up 
	 * and when they come across chutes,they are sloping down.
	 * @throws Exception
	 */
	@Test
	public void laddersAndChutesFoundAlongTheWay_mustDisplaySlopingDownAndClimbingUpTest() throws Exception {
		String validInput = GameConfiguration.ONLY_VALID_KEY + "\n";
		PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mockForRandom);
		when(mockForRandom.nextInt(GameConfiguration.MAXIMUM_RAND_DICE_VALUE)).thenReturn(6);
		StringBuffer input = new StringBuffer();
		for (int i = 0; i < 200; i++) {
			input.append(validInput);
		}

		setUpScannerInputs(input.toString());
		Board.setChute(new HashMap<Integer,Integer>(){{
			put(12, 5);
		}});
		Board.setLadder(new HashMap<Integer,Integer>(){{
			put(11, 75);
			put(87,100);
		}});
		GameProcessor processor = new GameProcessor();
		Result result = processor.process(players);
		verify(out, times(0)).println("You miss your turn as you provided a wrong input by entering :wrongInput");
		verify(out, times(2)).println("Sloping down to : 5");
		verify(out, times(2)).println("Climbing the Ladder to :75");
		verify(out, times(1)).println("Climbing the Ladder to :100");
		verify(out).println("Winner is :player1");
		assertEquals(new Result(new Player("player1",100)), result);
	}
	
	/**
	 * This test validates that when the players have rolled over the winning point,
	 * they don't move
	 * @throws Exception
	 */
	@Test
	public void rollOverMoreThanHundred_playerDoesNotMoveTest() throws Exception {

		String validInput = GameConfiguration.ONLY_VALID_KEY + "\n";
		PowerMockito.whenNew(Random.class).withAnyArguments().thenReturn(mockForRandom);
		when(mockForRandom.nextInt(GameConfiguration.MAXIMUM_RAND_DICE_VALUE)).thenReturn(6);
		StringBuffer input = new StringBuffer();
		for (int i = 0; i < 200; i++) {
			input.append(validInput);
		}

		setUpScannerInputs(input.toString());
		Board.setChute(new HashMap<Integer,Integer>(){{
			put(13, 5);
		}});
		Board.setLadder(new HashMap<Integer,Integer>(){{
			put(12, 98);
		}});
		GameProcessor processor = new GameProcessor();
		Result result = processor.process(players);
		verify(out, times(0)).println("You miss your turn as you provided a wrong input by entering :wrongInput");
		verify(out, times(0)).println("Sloping down to : 5");
		verify(out, times(2)).println("Climbing the Ladder to :98");
		verify(out, atLeastOnce()).println("You rolled 6 and sum is greater than 100 "
				+ "and hence cannot be counted.Roll next time");
		assertEquals(null, result);
		assertEquals(98, players.get(0).getRunningTotal());
		assertEquals(98, players.get(1).getRunningTotal());
	}
	
	private static Map<Integer, Integer> setUpChute() {
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

	private static Map<Integer, Integer> setUpLadder() {
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
