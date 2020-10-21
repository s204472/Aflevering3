import java.util.Scanner;
import java.awt.geom.Line2D;
import java.awt.Color;

public class RaceTrack {
	public static final double SQUARE_SIZE = 0.5;
	public static final int MAP_SIZE = 50;
	public static final int Q_MAP = MAP_SIZE / 4;
	public static final int THREEQ_MAP = MAP_SIZE - MAP_SIZE / 4;
	public static final int HALF_MAP = MAP_SIZE / 2;
	
	

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setPenRadius(2.0 / 1000);
		StdDraw.setXscale(0, MAP_SIZE);
		StdDraw.setYscale(0, MAP_SIZE);
		drawMap();
		int carCount  = getPlayers();
		
		int[][] carPos = new int[carCount][2];
		int[][] lastMove = new int[carCount][2];
		boolean[][] win = new boolean[carCount][2];
		int[][] lastCarPos = new int [carCount][2];
		Color[] carColor = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE };
		
		for(int i = 0; i < carPos.length; i++) {
			carPos[i][0] = HALF_MAP;
			carPos[i][1] = THREEQ_MAP + Q_MAP/2;
		
			lastMove[i][0] = 0;
			lastMove[i][1] = 0;
			
			win[i][0] = false;
			win[i][1] = true;
			
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledCircle(carPos[i][0], carPos[i][1], 0.3);			
		}
		
		boolean gameover = false;
		
		 
		int moveCounter = 0;
		
		while(!gameover) {
			for(int i = 0; i < carPos.length; i++) {
				int nextMove = getMove(i);
				moveCounter++;
				lastCarPos[i][0] = carPos[i][0];
				lastCarPos[i][1] = carPos[i][1];

				carPos[i] = getNewPos(carPos[i], lastMove[i], nextMove);
				lastMove[i][0] = carPos[i][0] - lastCarPos[i][0];
				lastMove[i][1] = carPos[i][1] - lastCarPos[i][1];

				
				StdDraw.setPenColor(carColor[i]);
				StdDraw.filledCircle(carPos[i][0], carPos[i][1], 0.3);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.line(lastCarPos[i][0], lastCarPos[i][1], carPos[i][0], carPos[i][1]);
				if (checkCrash(carPos[i], lastCarPos[i])) {
					gameover = true;
					System.out.println("Gameover");
				}
				win[i] = checkWin(carPos[i], lastCarPos[i], win[i]);
				if (win[i][0]) {
					gameover = true;
					System.out.println("You won");
					System.out.println("You made " + moveCounter);
				}
			}	
		}
	}

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
	public static int getPlayers() {
    	Scanner input = new Scanner(System.in);
		int num = 0; 
		System.out.print("Enter number of players (max 5): ");
		while(true){
			while (!input.hasNextInt()){
				input.next();
				System.out.println("Wrong input, try again");
			}
			num = input.nextInt();
			if (num > 0 && num < 6) {
				return num;
			} else {
				System.out.println("Wrong input, try again");
			}
		}
    }
	
	public static int getMove(int playerNum) {
    	Scanner input = new Scanner(System.in);
		int num = 0; 
		System.out.print("Player " + (playerNum + 1) + " move: ");
		while(true){
			while (!input.hasNextInt()){
				input.next();
				System.out.println("Wrong input, try again");
			}
			num = input.nextInt();
			if (num > 0 && num < 10) {
				return num;
			} else {
				System.out.println("Wrong input, try again");
			}
		}
    }
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


