package gamestudio.game.core;

import gamestudio.entity.Score;

import java.util.Random;

public class Field {
    private final int WIDTH_OF_BOARD = 9;
    private final int HEIGHT_OF_BOARD = 9;
    private Tile[][] Board;
    private Tile[][] FilledBoard;
    private Boxes boxes;

    //private long startMills;

    public Field(Boxes boxes) {
        this.boxes = boxes;
    }

    //make empty field with tiles
    private void MakeEmptyField(Tile[][] FieldToNull) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                FieldToNull[i][j] = new Tile();
            }
        }
    }

    public Tile[][] getBoard() {
        return Board;
    }

    public void generate(int difficulty) {
        Board = new Tile[WIDTH_OF_BOARD][HEIGHT_OF_BOARD];
        FilledBoard = new Tile[WIDTH_OF_BOARD][HEIGHT_OF_BOARD];
        MakeEmptyField(getBoard());
        MakeEmptyField(this.FilledBoard);    //make new empty field
        NextCell(0, 0);               // can we create next cell(for row or col)
        CopyField();
        SetDefColor();
        MakeHoles(difficulty);               // set colors of boxes and make clear tiles without numbers
        // startMills = System.currentTimeMillis();
    }

    private void CopyField() {
        for (int row = 0; row < WIDTH_OF_BOARD; row++) {
            for (int col = 0; col < HEIGHT_OF_BOARD; col++) {
                FilledBoard[row][col].setValue(Board[row][col].getValue());
            }
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < WIDTH_OF_BOARD; row++) {
            for (int col = 0; col < HEIGHT_OF_BOARD; col++) {
                if (FilledBoard[row][col].getValue() != Board[row][col].getValue()) {
                    return false;
                }

            }
        }
        return true;
    }

    private boolean NextCell(int row, int col) {
        int[] toCheck = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        int tmp, current, lenToCheck = toCheck.length;

        for (int i = lenToCheck - 1; i > 0; i--) {
            current = random.nextInt(i);
            tmp = toCheck[current];
            toCheck[current] = toCheck[i];
            toCheck[i] = tmp;
        }
        int nextCol;
        int nextRow = row;
        for (int j : toCheck) {

            if (LegalMove(row, col, j)) {
                Board[row][col].setValue(j);
                if (col == 8) {
                    if (row == 8)
                        return true;
                    else {
                        nextCol = 0;
                        nextRow = row + 1;
                    }
                } else {
                    nextCol = col + 1;
                }
                if (NextCell(nextRow, nextCol))
                    return true;
            }
        }
        Board[row][col].setValue(0);
        return false;

    }

    public boolean LegalMove(int row, int col, int currentNumber) {
        for (int i = 0; i < 9; i++) {
            if (currentNumber == Board[row][i].getValue()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (currentNumber == Board[i][col].getValue()) {
                return false;
            }
        }
        if (IsInBox(row, col, currentNumber)) {
            return false;
        }
        return true;

    }

    private boolean IsInBox(int col, int row, int number) {
        int[] box = boxes.getBox(row, col);
        for (int i = 0; i < 9; i++) {
            int position = box[i];
            int tmpCol = position % 10;
            position = position / 10;
            int tmpRow = position % 10;
            if (number == Board[tmpRow][tmpCol].getValue()) {
                return true;
            }
        }
        return false;
    }

    private void MakeHoles(double holesToMake) {
        double remainingSquares = HEIGHT_OF_BOARD * WIDTH_OF_BOARD;
        double remainingHoles = holesToMake;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                double holeChance = remainingHoles / remainingSquares;
                if (Math.random() <= holeChance) {
                    Board[i][j].setValue(0);
                    Board[i][j].setState(true);
                    Board[i][j].setColorUsable(Color.WHITE);
                    remainingHoles--;
                }
                remainingSquares--;
            }
        }
    }

    private void SetDefColor() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int[] BoxToColor = boxes.getBox(i, j);
                if (BoxToColor == boxes.getTotalBoxes()[0]) {
                    this.Board[j][i].setColorDefault(Color.CYAN);
                } else if (BoxToColor == boxes.getTotalBoxes()[1]) {
                    this.Board[j][i].setColorDefault(Color.PURPLE);
                } else if (BoxToColor == boxes.getTotalBoxes()[2]) {
                    this.Board[j][i].setColorDefault(Color.YELLOW);
                } else if (BoxToColor == boxes.getTotalBoxes()[3]) {
                    this.Board[j][i].setColorDefault(Color.GREEN);
                } else if (BoxToColor == boxes.getTotalBoxes()[4]) {
                    this.Board[j][i].setColorDefault(Color.BRIGHT_GREEN);
                } else if (BoxToColor == boxes.getTotalBoxes()[5]) {
                    this.Board[j][i].setColorDefault(Color.BRIGHT_PURPLE);
                } else if (BoxToColor == boxes.getTotalBoxes()[6]) {
                    this.Board[j][i].setColorDefault(Color.BRIGHT_YELLOW);
                } else if (BoxToColor == boxes.getTotalBoxes()[7]) {
                    this.Board[j][i].setColorDefault(Color.BRIGHT_CYAN);
                } else if (BoxToColor == boxes.getTotalBoxes()[8]) {
                    this.Board[j][i].setColorDefault(Color.BLUE);
                }
            }
        }
    }

   /* public int getScore(){
        return (int)(System.currentTimeMillis() - startMills) / 1000
    }*/


}
