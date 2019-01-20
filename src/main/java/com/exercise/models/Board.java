package com.exercise.models;

import java.util.HashMap;
import java.util.Map;

/**
* This is a Model class that stores the Board configuration.
*/
public class Board {

	private static Map<Integer, Integer> chute = new HashMap();
	private static Map<Integer, Integer> ladder = new HashMap();

	public static Map<Integer, Integer> getChute() {
		return chute;
	}

	public static void setChute(Map<Integer, Integer> chute) {
		Board.chute = chute;
	}

	public static Map<Integer, Integer> getLadder() {
		return ladder;
	}

	public static void setLadder(Map<Integer, Integer> ladder) {
		Board.ladder = ladder;
	}

}
