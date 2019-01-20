# Chute And Ladder game

A simple desktop application to play the Chute and Ladder game.

# Rules 

<br>Each player takes turns to roll the dice.</br>
<br>Move counter forward the number of spaces shown on the dice.</br>
<br>If your counter lands at ladder, you can move up to the top of the ladder.</br>
<br>If your counter lands on the head of a slope, you must slide down to the bottom of the chute.</br>
<br>The first player whose counter is 100,wins.</br>

## Assumptions

<br>Minimum of 2 and Maximum of 4 can play the game.</br>
<br>Game stops if less than 2 or more than 4 players are entered<br>
<br>To roll the dice,you press 'r' with your keyboard.If the player presses any other key, the player loses the turn</br>
<br>Maximum value a player can roll the dice is '6' per iteration.</br>
<br>Game stops after each player finished 50 iterations,and if no winner is found.</br>


### Prerequisites

You'd need JDK 1.8 , Maven and Lombok plugin on your IDE


## Installing Lombok on your IDE

If you are using intelliJ, follow the following instructions : 

```
https://projectlombok.org/setup/intellij
```

If you are using eclipse, follow the following instructions:

```
https://projectlombok.org/setup/eclipse
```


## Running the application

```
mvn clean install
```

### Running tests


```
mvn test
```

### Playing the game

<br> On your IDE:</br>

```
Run the GameInitiator.java as a Java application
```

<br> From command line:</br>

```
 java -jar target/chutes-and-ladders-1.0.0.jar
```

