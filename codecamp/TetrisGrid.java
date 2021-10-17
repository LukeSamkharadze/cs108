public class TetrisGrid {
	private boolean[][] grid;

	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}

	public void clearRows() {
		for (var y = 0; y < grid[0].length; y++) {
			var isRowFull = true;
			for (var col : grid) isRowFull = isRowFull && col[y];

			if (isRowFull) {
				for (var col : grid) col[y] = false;
				for (var yy = y; yy < grid[0].length - 1; yy++)
					for (var x = 0; x < grid.length; x++)
						grid[x][yy] = grid[x][yy + 1];
				for (var col : grid) col[grid[0].length - 1] = false;
				y--;
			}
		}
	}

	boolean[][] getGrid() {
		return this.grid;
	}
}
