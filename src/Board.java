import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Board {
    Stone[][] grid;
    private List<Position> goalPositions;

    public Board(int rows, int cols, List<Position> goalPositions) {
        grid = new Stone[rows][cols];
        this.goalPositions = goalPositions;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;

        return Arrays.deepEquals(this.grid, board.grid) && this.goalPositions.equals(board.goalPositions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(Arrays.deepHashCode(grid), goalPositions);
    }

    public List<Position> getGoalPositions() {
        return this.goalPositions;
    }
    public void placeStone(int row, int col, Stone stone) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            grid[row][col] = stone;
        }
    }
    public boolean isGoalState() {
        for (Position pos : goalPositions) {
            Stone stone = grid[pos.getRow()][pos.getCol()];

            if (stone == null || !(stone.getType().equals("Iron") || stone.getType().equals("Magnet"))) {
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

    public Stone[][] getBoardArray() {
        return this.grid;
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
                if (grid[i][j] != null && grid[i][j].getType().equals("Magnet") && grid[i][j].getColor().equals("Red Magnetic")) {
                    System.out.println("تم العثور على المغناطيس الأحمر في (" + i + ", " + j + ")");
                    return new Position(i, j);
                }
            }
        }
        System.out.println("لم يتم العثور على المغناطيس الأحمر.");
        return null;
    }

    public Position getCurrentPinkMagnetPosition() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null && grid[i][j].getType().equals("Magnet") && grid[i][j].getColor().equals("Pink Magnetic")) {
                    System.out.println("تم العثور على المغناطيس الوردي في (" + i + ", " + j + ")");
                    return new Position(i, j);
                }
            }
        }
        System.out.println("لم يتم العثور على المغناطيس الوردي.");
        return null;
    }

}