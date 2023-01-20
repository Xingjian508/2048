import java.util.*;

public class Main
{
    public static void main(String[] args) {

        GameState game = new GameState();
        Interface ux = new Interface();
        boolean running = true;

        while (running == true) {
            game.printBoard();
            running = handleCommand(game, ux);
        }

        System.out.println("Game over!");
    }

    public static boolean handleCommand(GameState game, Interface ux) {
        String uI = ux.getInput();
        if (uI.equals("a")) {
            game.moveLeft();
            game.getRidLeft();
            game.moveLeft();
        }
        else if (uI.equals("d")) {
            game.moveRight();
            game.getRidRight();
            game.moveRight();
        }
        else if (uI.equals("w")) {
            game.moveUp();
            game.getRidUp();
            game.moveUp();
        }
        else if (uI.equals("s")) {
            game.moveDown();
            game.getRidDown();
            game.moveDown();
        }
        else {
            System.out.println("This is not the correct input.");
            return false;
        }

        game.randomAdd();

        if (game.determineFull()) {
            return false;
        }
        else {
            return true;
        }
    }
}

class Interface
{
    Scanner input = new Scanner(System.in);

    public String getInput() {
        System.out.println("Next step: ");
        String uI = this.input.nextLine();
        return uI;
    }
}

class GameState
{
    private int[][] board = new int[4][4];
    private Random rand = new Random();

    public GameState() {
        // 1. Initialize, set two spots as "2" and/or "4".
        Random rando = this.rand;
        int cx1 = rando.nextInt(4);
        int cy1 = rando.nextInt(4);
        int cx2 = rando.nextInt(4);
        int cy2 = rando.nextInt(4);

        if (cx1 == cx2 && cy1 == cy2) {
            cx1 += 1;
            if (cx1 >= 4) {
                cx1 -= 4;
            }
        }

        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                int value = decideTwoFour();
                if (i == cx1 && j == cy1) {
                    this.board[i][j] = 2;
                }
                else if (i == cx2 && j == cy2) {
                    this.board[i][j] = value;
                }
                else {
                    this.board[i][j] = 0;
                }
            }
        }
    }

    public int decideTwoFour() {
        Random rando = this.rand;
        int decider = rando.nextInt(4);
        if (decider == 0) {
            decider = 4;
        }
        else {
            decider = 2;
        }
        return decider;
    }

    public void printBoard() {
        System.out.print(" ");
        for (int i=0; i<4; i++) {
            System.out.print("--");
        }
        System.out.println("-");
        for (int i=0; i<4; i++) {
            System.out.print("|");
            System.out.print(" ");
            for (int j=0; j<4; j++) {
                System.out.print(this.board[i][j]);
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.print(" ");
        for (int i=0; i<4; i++) {
            System.out.print("--");
        }
        System.out.println("-");
    }

    public boolean determineFull() {
        boolean allFull = true;
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (this.board[i][j] == 0) {
                    allFull = false;
                    break;
                }
            }
        }
        return allFull;
    }

    public void randomAdd() {
        Random rando = this.rand;
        int x1 = rando.nextInt(4);
        int y1 = rando.nextInt(4);

        while (this.board[x1][y1] != 0) {
            x1 = rando.nextInt(4);
            y1 = rando.nextInt(4);
        }

        board[x1][y1] = decideTwoFour();
    }

    public void getRidLeft() {
        board = this.board;
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                if (board[i][j] == board[i][j+1]) {
                    board[i][j] = board[i][j] * 2;
                    board[i][j+1] = 0;
                }
            }
        }
    }

    public void getRidRight() {
        board = this.board;
        for (int i=0; i<4; i++) {
            for (int j=3; j>=1; j--) {
                if (board[i][j] == board[i][j-1]) {
                    board[i][j] = board[i][j-1] * 2;
                    board[i][j-1] = 0;
                }
            }
        }
    }

    public void getRidUp() {
        board = this.board;
        for (int j=0; j<4; j++) {
            for (int i=0; i<3; i++) {
                if (board[i][j] == board[i+1][j]) {
                    board[i][j] = board[i][j] * 2;
                    board[i+1][j] = 0;
                }
            }
        }
    }

    public void getRidDown() {
        board = this.board;
        for (int j=0; j<4; j++) {
            for (int i=3; i>=1; i--) {
                if (board[i][j] == board[i-1][j]) {
                    board[i][j] = board[i-1][j] * 2;
                    board[i-1][j] = 0;
                }
            }
        }
    }

    public void moveLeft() {
        board = this.board;
        for (int i=0; i<4; i++) {
            int leftMost = 0;
            for (int j=0; j<4; j++) {
                while (leftMost < 3 && board[i][leftMost] != 0) {
                    leftMost += 1;
                }
                if (leftMost < j) {
                    board[i][leftMost] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }

    public void moveRight() {
        board = this.board;
        for (int i=0; i<4; i++) {
            int rightMost = 3;
            for (int j=3; j>=0; j--) {
                while (rightMost > 0 && board[i][rightMost] != 0) {
                    rightMost -= 1;
                }
                if (rightMost > j) {
                    board[i][rightMost] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }

    public void moveUp() {
        board = this.board;
        for (int j=0; j<4; j++) {
            int upMost = 0;
            for (int i=0; i<4; i++) {
                while (upMost < 3 && board[upMost][j] != 0) {
                    upMost += 1;
                }
                if (upMost < i) {
                    board[upMost][j] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }

    public void moveDown() {
        board = this.board;
        for (int j=0; j<4; j++) {
            int downMost = 3;
            for (int i=3; i>=0; i--) {
                while (downMost > 0 && board[downMost][j] != 0) {
                    downMost -= 1;
                }
                if (downMost > i) {
                    board[downMost][j] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }
}

// Work of Xingjian Wang, CS & Math double major, class of 2026 @ UCI.