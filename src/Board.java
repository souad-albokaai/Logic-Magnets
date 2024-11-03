import java.util.List;

public class Board {
    Stone[][] grid;
    private List<Position> goalPositions;

    public Board(int rows, int cols, List<Position> goalPositions) {
        grid = new Stone[rows][cols];
        this.goalPositions = goalPositions;
    }
    public void placeStone(int row, int col, Stone stone) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            grid[row][col] = stone;
        }
    }
    public boolean isGoalState() {
        for (Position pos : goalPositions) {
            if (grid[pos.getRow()][pos.getCol()] == null) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    System.out.print("[" + grid[i][j] + "]");
                } else {
                    System.out.print("[   ]");
                }
            }
            System.out.println();
        }
    }

    public boolean moveStone(int currentRow, int currentCol, int newRow, int newCol) {
        if (isWithinBounds(newRow, newCol) && grid[newRow][newCol] == null && grid[currentRow][currentCol] != null) {
            grid[newRow][newCol] = grid[currentRow][currentCol];
            grid[currentRow][currentCol] = null;
            return true;
        }
        return false;
    }

    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public boolean isCellOccupied(int row, int col) {
        return grid[row][col] != null;
    }

    public Stone getStone(int row, int col) {
        return isWithinBounds(row, col) ? grid[row][col] : null;
    }

    public Position getCurrentRedMagnetPosition() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Stone stone = getStone(i, j);
                if (stone != null && stone.getColor().equals("Red Magnetic")) {
                    return new Position(i, j);
                }
            }
        }
        return null; // exception
    }

    public Position getCurrentPinkMagnetPosition() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Stone stone = getStone(i, j);
                if (stone != null && stone.getColor().equals("Pink Magnetic")) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }
}
