public class FillSquares {

	private int[][] arr;
	private int side;

	// 1   2   3   4
	// 12  13  14  5
	// 11  16  15  6
	// 10  9   8   7
	private int fillBorder(int bSize, int index) {
		System.out.println("fillBorder size: " + bSize + " index " + index);
		int startX = (side - bSize) / 2;
		int startY = startX;
		System.out.println("startX: " + startX + " startY " + startY);

		if (bSize == 1) {
			if ( side % 2 == 1) {
				arr[startX][startY] = ++index;
				return index;
			} else {
				return index;
			}
		}

		System.out.println(toString());

		for (int xo = 0; xo < bSize; xo++) {
			arr[startX + xo][startY] = ++index;
		}

		System.out.println(toString());

		for (int yo = 1; yo < bSize; yo++) {
			arr[startX + bSize -1][startY + yo] = ++index;
		}

		System.out.println(toString());

		for (int xo = bSize - 2; xo >= 0; xo--) {
			arr[startX + xo][startY + bSize - 1] = ++index;
		}

		System.out.println(toString());

		for (int yo = bSize - 2; yo > 0; yo--) {
			arr[startX][startY + yo] = ++index;
		}

		System.out.println(toString());

		return index;
	}

	public String toString() {
		String res = "";
		for(int i = 0 ; i < side; ++i) {
			String line = "";
			for (int j = 0 ; j < side; ++j) {
				line += " " + arr[i][j];
			}
			res += "\n" + line;
		}
		return res;
	}

	public void fill(int side) {
		this.side = side;
		arr = new int[side][side];

		// side == 0, 0 border
		// side == 1. 1 border
		// side == 2  1 border
		// side == 3  2 borders;
		int index = 0;
		for (int s = side; s > 0 ; s-=2) {
			index = fillBorder(s, index);
		}

	}

	public static void main(String[] args) {
		FillSquares fs = new FillSquares();
		System.out.println(fs);
		fs.fill(5);
		System.out.println(fs);
	}
}
