import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private GameLogic logic;
    private int currentStage = 1;

    public Game() {
        setupStage(currentStage);
        logic = new GameLogic(board);
    }

    public void setupStage(int stage) {
        List<Position> goals = new ArrayList<>();
        Stone redMagnet = new Stone("Red Magnetic", "Magnet");
        Stone pinkMagnet = new Stone("Pink Magnetic", "Magnet");
        Stone ironStone = new Stone("Iron", "Iron");

        switch (stage) {
            case 1:
                board = new Board(3, 3, goals);
                board.placeStone(0, 0, redMagnet);
                board.placeStone(2, 2, ironStone);
                goals.add(new Position(1, 1));
                break;

            case 2:
                board = new Board(3, 3, goals);
                board.placeStone(0, 0, redMagnet);
                board.placeStone(0, 2, pinkMagnet);
                board.placeStone(2, 1, ironStone);
                goals.add(new Position(1, 1));
                goals.add(new Position(2, 2));
                break;

            default:
                System.out.println("Stage not implemented.");
                break;
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (!board.isGoalState()) {
            board.printBoard();
            System.out.println("ماذا تريد أن تفعل؟");
            System.out.println("1. تحريك المغناطيس الأحمر");
            System.out.println("2. تحريك المغناطيس الوردي");
            System.out.println("0. الخروج");

            int choice = scanner.nextInt();
            if (choice == 0) break;

            System.out.print("أدخل المكان الجديد (row, col): ");
            int newRow = scanner.nextInt();
            int newCol = scanner.nextInt();

            Board currentBoard = null;
            switch (choice) {
                case 1:
                    currentBoard = logic.moveRedMagnet(currentBoard.getCurrentRedMagnetPosition().getRow(),
                            currentBoard.getCurrentRedMagnetPosition().getCol(),
                            newRow, newCol);
                    break;
                case 2:
                    currentBoard = logic.movePinkMagnet(currentBoard.getCurrentPinkMagnetPosition().getRow(),
                            currentBoard.getCurrentPinkMagnetPosition().getCol(),
                            newRow, newCol);
                    break;
                default:
                    System.out.println("اختيار غير صحيح.");
            }


            if (currentBoard != null) {
                currentBoard.printBoard();
            }
        }
        if (board.isGoalState()) {
            System.out.println("تهانينا! لقد حققت الهدف.");
        }
        scanner.close();
    }

}