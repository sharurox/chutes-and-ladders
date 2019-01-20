package com.exercise.main;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.exercise.configuration.GameConfiguration;
import com.exercise.models.Board;
import com.exercise.models.Player;
import com.exercise.models.Result;

/**
 * This Class is responsible for playing the game. You miss a chance if you do
 * not roll the configurable 'validKey'. You roll over configurable
 * 'Winning_point',you don't move. Game exits if player reaches the
 * 'Winning_Point'. Game also exits if no winner is found after the maximum
 * count per player is reached(config based).
 */
public class GameProcessor {

	public Result process(List<Player> players) {
		boolean isWinnerFound = false;
		final String validKey = GameConfiguration.ONLY_VALID_KEY;

		int exitConditionIfNoWinnerIsFound = players.size()
				* GameConfiguration.MAXIMUM_COUNT_TO_EXIT_FROM_GAME_PER_PERSON;
		int count = 0;
		Map<Integer, Integer> chute = Board.getChute();
		Map<Integer, Integer> ladder = Board.getLadder();
		Scanner scanner = new Scanner(System.in);
		do {

			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				System.out.println(p.getName() + ",press " + validKey + " to roll ");
				String input = scanner.next();

				switch (input) {
				case validKey:
					int diceValue = rollDice();
					count++;
					int runningTotal = p.getRunningTotal() + diceValue;
					if (runningTotal > 100) {
						System.out.println("You rolled " + diceValue
								+ " and sum is greater than 100 and hence cannot be counted.Roll next time");
						continue;
					}
					System.out.println(
							p.getName() + ":" + p.getRunningTotal() + "-->" + (p.getRunningTotal() + diceValue));

					if (chute.containsKey(runningTotal)) {
						runningTotal = chute.get(runningTotal);
						System.out.println("Sloping down to : " + runningTotal);
					}

					if (ladder.containsKey(runningTotal)) {
						runningTotal = ladder.get(runningTotal);
						System.out.println("Climbing the Ladder to :" + runningTotal);
					}

					if (runningTotal == GameConfiguration.WINNING_POINT) {
						System.out.println("Winner is :" + p.getName());
						isWinnerFound = true;
						Player winner = new Player(p.getName(), runningTotal);
						Result result = new Result(winner);
						return result;

					}
					p.setRunningTotal(runningTotal);
					break;
				default:
					System.out.println("You miss your turn as you provided a wrong input by entering :" + input);
				}

			}
		} while (!isWinnerFound && count < exitConditionIfNoWinnerIsFound);
		System.out.println("Couldnt find a unanimous winner since the count for each player has exceeded "
				+ GameConfiguration.MAXIMUM_COUNT_TO_EXIT_FROM_GAME_PER_PERSON);
		return null;
	}

	/**
	 * @return random number 'n' where n is bound between a configurable Min and Max
	 *         value
	 */
	private int rollDice() {
		Random r = new Random();
		int n = r.nextInt(GameConfiguration.MAXIMUM_DICE_VALUE + 1);
		return (n == 0 ? GameConfiguration.MINIMUM_DICE_VALUE : n);
	}
}
