import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private GameLogic logic;



    public void setupStage(int stage, List<Position> goals) {
        Stone redMagnet = new Stone("Red Magnetic", "Magnet");
        Stone pinkMagnet = new Stone("Pink Magnetic", "Magnet");
        Stone ironStone = new Stone("Iron", "Iron");

        switch (stage) {
            case 1:
                board = new Board(3, 3, goals);
                board.placeStone(2, 2, redMagnet);
                System.out.println("تم وضع المغناطيس الأحمر في (2, 2)");
                board.placeStone(1, 0, ironStone);
                board.placeStone(0, 1, pinkMagnet);
                goals.add(new Position(2, 0));
                break;
            case 2:
                board = new Board(3, 3, goals);
                board.placeStone(0, 0, redMagnet);
                System.out.println("تم وضع المغناطيس الأحمر في (0, 0)");
                board.placeStone(0, 2, pinkMagnet);
                System.out.println("تم وضع المغناطيس الوردي في (0, 2)");
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
        List<Position> primaryGoals = new ArrayList<>();
        setupStage(1, primaryGoals);

        logic = new GameLogic(board);
        Scanner scanner = new Scanner(System.in);

        while (!board.isGoalState()) {
            board.printBoard();
            System.out.println("ماذا تريد أن تفعل؟");
            System.out.println("1. البحث باستخدام BFS");
            System.out.println("2. البحث باستخدام DFS");
            System.out.println("0. الخروج");

            int choice = scanner.nextInt();
            if (choice == 0) break;

            Board resultBoard = null;
            switch (choice) {
                case 1:
                    resultBoard = logic.bfs();
                    break;
                case 2:
                    resultBoard = logic.dfs();
                    break;
                default:
                    System.out.println("اختيار غير صحيح.");
            }

            if (resultBoard != null && resultBoard.isGoalState()) {
                System.out.println("تم العثور على الحل.");
                resultBoard.printBoard();
                break;
            } else {
                System.out.println("لم يتم العثور على حل.");
            }
        }
        scanner.close();
    }


}
