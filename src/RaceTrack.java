
public class RaceTrack {
	public static final double SQUARE_SIZE = 0.5;
	public static final int MAP_SIZE = 20;

	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setPenRadius(2.0 / 1000);
		StdDraw.setXscale(0, MAP_SIZE);
		StdDraw.setYscale(0, MAP_SIZE);
		drawMap(MAP_SIZE, MAP_SIZE);
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
	}
}
