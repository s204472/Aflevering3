import java.util.Scanner;
import java.awt.geom.Line2D;

public class RaceTrack {
	public static final double SQUARE_SIZE = 0.5;
	public static final int MAP_SIZE = 20;
	

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setPenRadius(2.0 / 1000);
		StdDraw.setXscale(0, MAP_SIZE);
		StdDraw.setYscale(0, MAP_SIZE);
		drawMap(MAP_SIZE, MAP_SIZE);
		
		int[] carPos = new int[2];
		int[] lastMove = new int[2];
		
		carPos[0] = MAP_SIZE / 2;
		carPos[1] = MAP_SIZE - MAP_SIZE / 4 + 3;
		
		lastMove[0] = 0;
		lastMove[1] = 0; 
		
		
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(carPos[0], carPos[1], 0.3);
		boolean gameover = false;
		boolean[] win = {false, true};
		
		int moveCounter = 0;
		
		while(!gameover) {
			int nextMove = getMove();
			moveCounter ++;
			int[] lastCarPos = {carPos[0], carPos[1]};

			carPos = getNewPos(carPos, lastMove, nextMove);
			lastMove[0] = carPos[0] - lastCarPos[0];
			lastMove[1] = carPos[1] - lastCarPos[1];
			
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledCircle(carPos[0], carPos[1], 0.3);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.line(lastCarPos[0], lastCarPos[1], carPos[0], carPos[1]);
			if (checkCrash(carPos, lastCarPos)) {
				gameover = true;
				System.out.println("Gameover");
			}
			win = checkWin(carPos, lastCarPos, win); 
			System.out.println(win[0] + "" + win[1]);
			if (win[0]) {
				gameover = true;
				System.out.println("You won");
				System.out.println("You made " + moveCounter);
			}
			
		}
		
		
	}

	public static void drawMap(int sizeX, int sizeY) {

		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (!(i >= sizeX / 4 && i < sizeX - sizeX / 4 && j >= sizeY / 4 && j < sizeY - sizeY / 4)) {
					StdDraw.setPenColor(StdDraw.GRAY);
					StdDraw.filledSquare(i + SQUARE_SIZE, j + SQUARE_SIZE, SQUARE_SIZE);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.square(i + SQUARE_SIZE, j + SQUARE_SIZE, SQUARE_SIZE);
				}
			}

		}
		StdDraw.setPenRadius(5.0 / 1000);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.line(sizeX/2, sizeY, sizeX/2, sizeY - sizeY / 4);
		StdDraw.line(sizeX/2 - 1, sizeY, sizeX/2 - 1, sizeY - sizeY / 4);
	}
	
	public static int getMove() {
    	Scanner input = new Scanner(System.in);
		int num = 0; 
		System.out.print("Enter move: ");
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
		Line2D line1 = new Line2D.Float(5, 15, 15, 15);
		Line2D line2 = new Line2D.Float(5, 5, 15, 5);
		Line2D line3 = new Line2D.Float(5, 5, 5, 15);
		Line2D line4 = new Line2D.Float(15, 5, 15, 15);
		
		if(move.intersectsLine(line1) || move.intersectsLine(line2) || move.intersectsLine(line3) || move.intersectsLine(line4)) {
			return true;
		}

		return false;
	}
	
	public static boolean[] checkWin (int[] carPos, int[] lastCarPos, boolean[] win) {
		Line2D move = new Line2D.Float(lastCarPos[0], lastCarPos[1], carPos[0], carPos[1]);
		Line2D winLine = new Line2D.Float(MAP_SIZE/2 - 1, MAP_SIZE, MAP_SIZE/2 - 1, MAP_SIZE - MAP_SIZE / 4);
		
		if(move.intersectsLine(winLine)) {
			if (carPos[0] - lastCarPos[0] < 0) {
				win[1] = false;
				return win;
			} else if (carPos[0] - lastCarPos[0] > 0 && !win[1]) {
				win[1] = true;
				return win;
			} else if (carPos[0] - lastCarPos[0] > 0 && win[1] && lastCarPos[0] != MAP_SIZE/2 - 1) {
				win[0] = true;
				return win;
			}
		} 
		return win;
	}
	
}