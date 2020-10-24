import java.util.Random;
import java.util.Scanner;

public class RandomWalk {
	public static final double RADIUS = 0.5;

	/* 
	 * This method initializes the grid, and the simulation of the particle is run in the while-loop.
	 * */
	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 800);
		int n = getInput();
		StdDraw.setXscale(-n, n);
		StdDraw.setYscale(-n, n);
		StdDraw.setPenRadius(2.0/1000);
		Random rand = new Random();
		int x = 0;
		int y = 0;
		int steps = 0;
		StdDraw.filledCircle(x, y, RADIUS);
		System.out.println("Position = (" + x + "," + y + ")");
		while(x < n && x > -n && y < n && y > -n) {
			int dir = rand.nextInt(4);
			if (dir == 0) {
				y++;
			} else if (dir == 1) {
				y--;
			} else if (dir == 2) {
				x++;
			} else if (dir == 3) {
				x--;
			}
			steps++;
			StdDraw.filledCircle(x, y, RADIUS);
			System.out.println("Position = (" + x + "," + y + ")");
		}
		System.out.println("Total number of steps = " + steps);

	}
	/* 
     * This method prompts the user for the size of the grid. 
     * The method handles wrong inputs, by using the try-catch-block and if-statements.
     * The method returns an int above 1.
     * */
	public static int getInput() {
        Scanner input = new Scanner(System.in);
        int num = 0;
        try {
            System.out.print("Enter size of grid: ");
            num = input.nextInt();
            if (num >= 2) {
                return num/2;
            } else {
                System.out.println("Wrong input, try again");
                return getInput();
            }
        } catch (Exception e) {
            System.out.println("Wrong input, try again");
            return getInput();
        }
    }

}
