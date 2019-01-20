package com.exercise.configuration;


import lombok.Data;

/**
* This is a class that'd set up configuration into the game
*/
@Data
public class GameConfiguration {

	private int numberOfPlayers;
	public final static int WINNING_POINT = 100;
	public final static int MAXIMUM_NUMBER_OF_PLAYERS = 4;
	public final static int MINIMUM_NUMBER_OF_PLAYERS = 2;
	public static final int MAXIMUM_DICE_VALUE = 6;
	public static final int MINIMUM_DICE_VALUE = 1;
	public static final String ONLY_VALID_KEY = "r";
	public static final int MAXIMUM_COUNT_TO_EXIT_FROM_GAME_PER_PERSON = 50;

}
