package com.exercise.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.exercise.configuration.GameConfiguration;
import com.exercise.models.Board;
import com.exercise.models.Player;
import com.exercise.models.Result;

/**
 * This class initiates the game.
 * Set up Chutes and Ladders on the board.
 * Set up Players.
 * Terminate if 2 < Number of players < 4
 */
public class GameInitiator {

	public static void main(String[] args) {

		System.out.println("Enter number of players playing the game:");
		Integer numberOfPlayers;
		GameConfiguration config = new GameConfiguration();
		setUpBoard();
		try {
			Scanner scanner = new Scanner(System.in);

			numberOfPlayers = Integer.parseInt(scanner.next());
			if (numberOfPlayers < GameConfiguration.MINIMUM_NUMBER_OF_PLAYERS
					|| numberOfPlayers > GameConfiguration.MAXIMUM_NUMBER_OF_PLAYERS) {
				System.out.println("Terminating! The number of players playing must be >2 and <4");
				return;
			}

			config.setNumberOfPlayers(numberOfPlayers);

			List<Player> players = new ArrayList<>();
			for (int i = 0; i < numberOfPlayers; i++) {
				Player player = new Player();
				System.out.println("Enter name for player:" + (i + 1));
				player.setName(scanner.next());
				players.add(player);
			}

			GameProcessor processor = new GameProcessor();
			
			Result result = processor.process(players);
			if (result != null) {
				System.out.println("Congratulations, " + result.getWinner().getName());
			} else {
				System.out.println("Bad luck, we could'nt find a winner as the amount of tries has lapsed.");
			}

		} catch (NumberFormatException e) {
			System.out.println("Terminating! Please enter a valid Integer. Try running again");
		}

	}

	
	private static void setUpBoard() {
		Board.setChute(setupChutes());
		Board.setLadder(setUpLadders());
	}

	
	private static Map<Integer, Integer> setupChutes() {
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

	private static Map<Integer, Integer> setUpLadders() {
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
