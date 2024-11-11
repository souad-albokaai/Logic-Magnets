import java.util.*;

public class GameLogic {
    private Board board;
    private Set<Board> visitedStates;

    public GameLogic(Board board) {
        this.board = board;
        this.visitedStates = new HashSet<>();
    }

    // BFS Implementation
    public Board bfs() {
        Queue<Board> queue = new LinkedList<>();
        Map<Board, Board> parentMap = new HashMap<>();
        queue.add(board);
        visitedStates.add(board);
        parentMap.put(board, null);

        while (!queue.isEmpty()) {
            Board currentBoard = queue.poll();

            if (currentBoard.isGoalState()) {
                List<Board> solutionPath = buildSolutionPath(parentMap, currentBoard);
                printSolutionPath(solutionPath);
                return currentBoard;
            }

            List<Board> nextStates = getNextStates(currentBoard);
            for (Board nextState : nextStates) {
                if (!visitedStates.contains(nextState)) {
                    queue.add(nextState);
                    visitedStates.add(nextState);
                    parentMap.put(nextState, currentBoard);
                }
            }
        }
        return null;
    }


    public Board dfs() {
        Stack<Board> stack = new Stack<>();
        Map<Board, Board> parentMap = new HashMap<>();
        stack.push(board);
        visitedStates.add(board);
        parentMap.put(board, null);

        while (!stack.isEmpty()) {
            Board currentBoard = stack.pop();

            if (currentBoard.isGoalState()) {
                List<Board> solutionPath = buildSolutionPath(parentMap, currentBoard);
                printSolutionPath(solutionPath);
                return currentBoard;
            }

            List<Board> nextStates = getNextStates(currentBoard);
            for (Board nextState : nextStates) {
                if (!visitedStates.contains(nextState)) {
                    stack.push(nextState);
                    visitedStates.add(nextState);
                    parentMap.put(nextState, currentBoard);
                }
            }
        }
        return null;
    }

    private List<Board> buildSolutionPath(Map<Board, Board> parentMap, Board goal) {
        List<Board> path = new ArrayList<>();
        Board current = goal;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }


    private void printSolutionPath(List<Board> path) {
        int step = 1;
        for (Board board : path) {
            System.out.println("خطوة " + step + ":");
            board.printBoard();
            System.out.println("----------");
            step++;
        }
    }

    private List<Board> getNextStates(Board currentBoard) {
        List<Board> nextStates = new ArrayList<>();
        Position redMagnetPos = currentBoard.getCurrentRedMagnetPosition();
        Position pinkMagnetPos = currentBoard.getCurrentPinkMagnetPosition();

        if (redMagnetPos == null || pinkMagnetPos == null) {
            System.out.println("موقع المغناطيس الأحمر أو الوردي غير مهيأ.");
            return nextStates;
        }


        for (int i = 0; i < currentBoard.grid.length; i++) {
            for (int j = 0; j < currentBoard.grid[i].length; j++) {
                if (currentBoard.isWithinBounds(i, j) && !currentBoard.isCellOccupied(i, j)) {
                    Board newBoard = createBoardCopy(currentBoard);
                    newBoard.moveStone(redMagnetPos.getRow(), redMagnetPos.getCol(), i, j);
                    updateIronStones(newBoard, i, j, true);
                    nextStates.add(newBoard);
                }
            }
        }


        for (int i = 0; i < currentBoard.grid.length; i++) {
            for (int j = 0; j < currentBoard.grid[i].length; j++) {
                if (currentBoard.isWithinBounds(i, j) && !currentBoard.isCellOccupied(i, j)) {
                    Board newBoard = createBoardCopy(currentBoard);
                    newBoard.moveStone(pinkMagnetPos.getRow(), pinkMagnetPos.getCol(), i, j);
                    updateIronStones(newBoard, i, j, false);
                    nextStates.add(newBoard);
                }
            }
        }
        return nextStates;
    }

    // نسخة من اللوحة الحالية
    private Board createBoardCopy(Board currentBoard) {
        Board newBoard = new Board(currentBoard.grid.length, currentBoard.grid[0].length, currentBoard.getGoalPositions());
        for (int row = 0; row < currentBoard.grid.length; row++) {
            for (int col = 0; col < currentBoard.grid[row].length; col++) {
                Stone stone = currentBoard.getStone(row, col);
                if (stone != null) {
                    newBoard.placeStone(row, col, new Stone(stone.getColor(), stone.getType()));
                }
            }
        }
        return newBoard;
    }

    private void updateIronStones(Board newBoard, int magnetRow, int magnetCol, boolean isAttraction) {
        for (int i = 0; i < newBoard.grid.length; i++) {
            for (int j = 0; j < newBoard.grid[i].length; j++) {
                Stone stone = newBoard.getStone(i, j);
                if (stone != null && stone.getType().equals("Iron")) {
                    if (magnetRow == i || magnetCol == j) {
                        moveIronStone(newBoard, i, j, magnetRow, magnetCol, isAttraction);
                    }
                }
            }
        }
    }

    private void moveIronStone(Board newBoard, int ironRow, int ironCol, int magnetRow, int magnetCol, boolean moveTowards) {
        int newRow = ironRow;
        int newCol = ironCol;

        if (moveTowards) {
            if (ironRow < magnetRow) newRow++;
            else if (ironRow > magnetRow) newRow--;

            if (ironCol < magnetCol) newCol++;
            else if (ironCol > magnetCol) newCol--;
        } else {
            if (ironRow == magnetRow) {
                if (ironCol < magnetCol) newCol--;
                else newCol++;
            } else if (ironCol == magnetCol) {
                if (ironRow < magnetRow) newRow--;
                else newRow++;
            }
        }

        if (newBoard.isWithinBounds(newRow, newCol) && !newBoard.isCellOccupied(newRow, newCol)) {
            try {
                newBoard.moveStone(ironRow, ironCol, newRow, newCol);
            } catch (Exception e) {
                System.out.println("حدث خطأ أثناء محاولة تحريك الحجر: " + e.getMessage());
            }
        }
    }
}
