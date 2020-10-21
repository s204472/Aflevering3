import java.util.Random;
import java.util.Scanner;

public class RandomWalk {
	public static final double RADIUS = 0.5;

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
	
	public static int getInput() {
        Scanner input = new Scanner(System.in);
        int num = 0;
        try {
            System.out.print("Enter size of grid: ");
            num = input.nextInt();
            if (num > 1) {
                return num;
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
