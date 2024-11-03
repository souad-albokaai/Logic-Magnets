public class GameLogic {
    private Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    public Board moveRedMagnet(int currentRow, int currentCol, int newRow, int newCol) {
        System.out.println("Trying to move red magnet to (" + newRow + ", " + newCol + ")");
        if (board.isCellOccupied(newRow, newCol)) {
            System.out.println("لا يمكن تحريك المغناطيس الأحمر إلى خانة مشغولة!");
            return board;
        }
        boolean moved = board.moveStone(currentRow, currentCol, newRow, newCol);
        if (moved) {
            updateIronStones(newRow, newCol, true);
        } else {
            System.out.println("لم يتم التحريك. تأكد من أن الخانة فارغة ومن الإحداثيات الصحيحة.");
        }
        return board;
    }

    public Board movePinkMagnet(int currentRow, int currentCol, int newRow, int newCol) {
        System.out.println("Trying to move pink magnet to (" + newRow + ", " + newCol + ")");
        if (board.isCellOccupied(newRow, newCol)) {
            System.out.println("لا يمكن تحريك المغناطيس الوردي إلى خانة مشغولة!");
            return board;
        }
        boolean moved = board.moveStone(currentRow, currentCol, newRow, newCol);
        if (moved) {
            updateIronStones(newRow, newCol, false);
        } else {
            System.out.println("لم يتم التحريك. تأكد من أن الخانة فارغة ومن الإحداثيات الصحيحة.");
        }
        return board;
    }

    private void updateIronStones(int magnetRow, int magnetCol, boolean isAttraction) {
        for (int i = 0; i < board.grid.length; i++) {
            for (int j = 0; j < board.grid[i].length; j++) {
                Stone stone = board.getStone(i, j);
                if (stone != null && stone.getType().equals("Iron")) {
                    if (magnetRow == i || magnetCol == j) {
                        moveIronStone(i, j, magnetRow, magnetCol, isAttraction);
                    }
                }
            }
        }
    }

    private void moveIronStone(int ironRow, int ironCol, int magnetRow, int magnetCol, boolean moveTowards) {
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
        if (board.isWithinBounds(newRow, newCol) && !board.isCellOccupied(newRow, newCol)) {
            board.moveStone(ironRow, ironCol, newRow, newCol);
        } else {
            System.out.println("لا يمكن تحريك الحجر الحديدي، الخلية الجديدة خارج الحدود أو مشغولة.");
        }
    }
}
