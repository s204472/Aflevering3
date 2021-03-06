import java.util.Scanner;
import java.awt.geom.Line2D;
import java.awt.Color;

public class RaceTrack {
	public static final double SQUARE_SIZE = 0.5;
	public static final int MAP_SIZE = 20;
	public static final int Q_MAP = MAP_SIZE / 4;
	public static final int THREEQ_MAP = MAP_SIZE - MAP_SIZE / 4;
	public static final int HALF_MAP = MAP_SIZE / 2;
	public static final int MAX_PLAYERS = 4;

	
	/*
	 * This is the main method. The game is initialized from this method.
	 * Variables is set, functions like getPlayers, and drawMap is called.
	 * The gameloop is then entered.
	 * */
	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setPenRadius(2.0 / 1000);
		StdDraw.setXscale(0, MAP_SIZE);
		StdDraw.setYscale(0, MAP_SIZE);
		drawMap();
		int carCount  = getPlayers();
		
		Car[] cars = new Car[carCount];
		Color[] carColor = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };
		
		for (int i = 0; i < carCount; i++) {
			cars[i] = new Car(HALF_MAP, THREEQ_MAP + Q_MAP / 2 + i - 1, carColor[i % carColor.length]);
			StdDraw.setPenColor(cars[i].color);
			StdDraw.filledCircle(cars[i].carPos[0], cars[i].carPos[1], 0.3);	
		}

		boolean gameover = false;		
		 

		StdDraw.setPenColor(StdDraw.RED);
		
		
		// This is the gameloop. The loop runs until a crash or a player has won.
		while(!gameover) {
			for(int i = 0; i < carCount; i++) {
				int nextMove = getMove(i);
				cars[i].moveCounter++;
				cars[i].lastCarPos[0] = cars[i].carPos[0];
				cars[i].lastCarPos[1] = cars[i].carPos[1];

				cars[i].carPos = getNewPos(cars[i].carPos, cars[i].lastMove, nextMove);
				cars[i].lastMove[0] = cars[i].carPos[0] - cars[i].lastCarPos[0];
				cars[i].lastMove[1] = cars[i].carPos[1] - cars[i].lastCarPos[1];

				StdDraw.setPenColor(cars[i].color);
				StdDraw.filledCircle(cars[i].carPos[0], cars[i].carPos[1], 0.3);		
				StdDraw.line(cars[i].lastCarPos[0], cars[i].lastCarPos[1], cars[i].carPos[0], cars[i].carPos[1]);
				
				if (checkCrash(cars[i].carPos, cars[i].lastCarPos)) {
					gameover = true;
					System.out.println("Gameover");
					break;
				}
				cars[i].win = checkWin(cars[i].carPos, cars[i].lastCarPos, cars[i].win);
				
				if (cars[i].win[0]) {
					gameover = true;
					System.out.println("Player " + (i + 1) + " won in " + cars[i].moveCounter + " moves");
					break;
				}
			}	
		}
	}

	/*
	 * This method draws the game map. It is done using nested for loops, running from 0, to MAP_SIZE.
	 * */
	public static void drawMap() {
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				if (!(i >= Q_MAP && i < THREEQ_MAP && j >= Q_MAP && j < THREEQ_MAP)) {
					StdDraw.setPenColor(StdDraw.GRAY);
					StdDraw.filledSquare(i + SQUARE_SIZE, j + SQUARE_SIZE, SQUARE_SIZE);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.square(i + SQUARE_SIZE, j + SQUARE_SIZE, SQUARE_SIZE);
				}
			}

		}
		StdDraw.setPenRadius(5.0 / 1000);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.line(HALF_MAP, MAP_SIZE, HALF_MAP, THREEQ_MAP);
	}
	
	
	/* 
     * This method prompts the user for the amount of players the game should be played with. 
     * The method handles wrong inputs, by using the try-catch-block and if-statements.
     * The method returns an int, between 1, and MAX_PLAYERS
     * */
	public static int getPlayers() {
        Scanner input = new Scanner(System.in);
        int num = 0;
        try {
            System.out.print("Enter number of players (max " + MAX_PLAYERS + "): ");
            num = input.nextInt();
            if (num > 0 && num < MAX_PLAYERS + 1) {
                return num;
            } else {
                System.out.println("Wrong input, try again");
                return getPlayers();
            }
        } catch (Exception e) {
            System.out.println("Wrong input, try again");
            return getPlayers();
        }
	    
    }
	
	/* 
     * This method prompts the user to enter the car's next move. 
     * The method handles wrong inputs, by using the try-catch-block and if-statements.
     * The method returns an int, between 1, and 9
     * */
	public static int getMove(int playerNum) {
		Scanner input = new Scanner(System.in);
        int num = 0;
        try {
            System.out.print("Player " + (playerNum + 1) + " move: ");
            num = input.nextInt();
            if (num > 0 && num < 10) {
                return num;
            } else {
                System.out.println("Wrong input, try again");
                return getMove(playerNum);
            }
        } catch (Exception e) {
            System.out.println("Wrong input, try again");
            return getMove(playerNum);
        }
		
		
		
		
    }
	
	/*
	 * This method translates the userinput from 1-9, to car movements.
	 * The new car position is returned as a int array of x,y.
	 * */
	public static int[] getNewPos(int[] carPos, int[] lastMove, int nextMove) {
		int newXmove = 0;
		int newYmove = 0;
		
		if (nextMove == 7 || nextMove == 4 || nextMove == 1){
			newXmove = -1;
		} else if (nextMove == 8 || nextMove == 5 || nextMove == 2){
			newXmove = 0;
		} else if (nextMove == 9 || nextMove == 6 || nextMove == 3){
			newXmove = 1;
		}
		if (nextMove == 7 || nextMove == 8 || nextMove == 9){
			newYmove = 1;
		} else if (nextMove == 4 || nextMove == 5 || nextMove == 6){
			newYmove = 0;
		} else if (nextMove == 1 || nextMove == 2 || nextMove == 3){
			newYmove = -1;
		}	
		
		carPos[0] = carPos[0] + lastMove[0] + newXmove;
		carPos[1] = carPos[1] + lastMove[1] + newYmove;
		
		return carPos;
	}
	
	
	/*
	 * This method is run everytime a car moves. It checks whether the car has crashed.
	 * The method returns true if car has crashed. False is returned otherwise.
	 * */
	public static boolean checkCrash(int[] carPos, int[] lastCarPos) {
		if (carPos[0] < 0 || carPos[0] > MAP_SIZE || 
				carPos[1] < 0 || carPos[1] > MAP_SIZE) {
			return true;
		} 	
		Line2D move = new Line2D.Float(lastCarPos[0], lastCarPos[1], carPos[0], carPos[1]);
		Line2D line1 = new Line2D.Float(Q_MAP, THREEQ_MAP, THREEQ_MAP, THREEQ_MAP);
		Line2D line2 = new Line2D.Float(Q_MAP, Q_MAP, THREEQ_MAP, Q_MAP);
		Line2D line3 = new Line2D.Float(Q_MAP, Q_MAP, Q_MAP, THREEQ_MAP);
		Line2D line4 = new Line2D.Float(THREEQ_MAP, Q_MAP, THREEQ_MAP, THREEQ_MAP);
		
		if(move.intersectsLine(line1) || move.intersectsLine(line2) || move.intersectsLine(line3) || move.intersectsLine(line4)) {
			return true;
		}

		return false;
	}
	
	/*
	 * This method is run everytime a car moves. It checks whether the car has crossed the win-line.
	 * It handles players crossing, the line from the wrong side as well. 
	 * */
	public static boolean[] checkWin (int[] carPos, int[] lastCarPos, boolean[] win) {
		Line2D move = new Line2D.Float(lastCarPos[0], lastCarPos[1], carPos[0], carPos[1]);
		Line2D winLine = new Line2D.Float(HALF_MAP, MAP_SIZE, HALF_MAP, THREEQ_MAP);
		
		if(move.intersectsLine(winLine)) {
			if (carPos[0] - lastCarPos[0] < 0) {
				win[1] = false;
				return win;
			} else if (carPos[0] - lastCarPos[0] > 0 && !win[1]) {
				win[1] = true;
				return win;
			} else if (carPos[0] - lastCarPos[0] > 0 && win[1] && lastCarPos[0] != HALF_MAP) {
				win[0] = true;
				return win;
			}
		} 
		return win;
	}
	
}


