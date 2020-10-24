import java.awt.Color;

/*
 * This is the Car class. It stores information about each Car object.
 * All Car attributes are initialized, and whenever a Car object is create the constructor is run.
 * */
public class Car {
	int[] carPos = new int[2];
    int[] lastMove = new int[2]; 
    boolean[] win = new boolean[2];
    int[] lastCarPos = new int[2];
    int moveCounter = 0;
    Color color;
    
	public Car(int startX, int startY, Color carColor) {
	    carPos[0] = startX;
	    carPos[1] = startY;
	    lastMove[0] = 0;
	    lastMove[1] = 0;
	    win[0] = false;
	    win[1] = true;
	    color = carColor;
	}

}
