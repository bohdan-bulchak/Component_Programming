package gamestudio.game.consoleui;


import gamestudio.game.core.*;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.entity.Comment;
import gamestudio.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private Field field;
    private GameState state;
    private Scanner scanner = new Scanner(System.in);

    private int difficulty;
    private int points;
    private long time;

    private static final String NONE = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_PURPLE = "\u001B[95m";
    private static final String BRIGHT_CYAN = "\u001B[96m";

    @Autowired
    private ScoreService scoreService;/* = new ScoreServiceJDBC();*/
    @Autowired
    private RatingService ratingService;/* = new RatingServiceJDBC();*/
    @Autowired
    private CommentService commentService;/* = new CommentServiceJDBC();*/

    public ConsoleUI(Field field, ScoreService scoreService, RatingService ratingService, CommentService commentService){
        this.field = field;
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    public ConsoleUI(Field field){
        this.field=field;
    }

    public void Play(Field field, Boxes boxes) {
       /* this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
        */
        this.state = GameState.PLAYING;
        this.field = field;
        this.points = 0;
        this.time = System.currentTimeMillis();
        DisplayStart();
        DisplayText();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        field.generate(difficulty);
        GameLoop(boxes);
        Display(field.getBoard(), boxes);
        System.out.println(BLUE + "You win!!!! \n" + BLUE);
        DisplayEnd(true);
        scanner.close();


    }

    private void DisplayStart() {
        System.out.println(YELLOW + "\n" + "--->Jigsaw Sudoku<---");
        System.out.println(YELLOW + "-----------------------------------------------" + NONE);

    }

    private void DisplayText() {
        System.out.println(GREEN + "-Welcome to the gamestudio.game 'Jigsaw Sudoku'-");
        System.out.println(YELLOW + "-----------------------------------------------" + NONE);
        do {
            System.out.println(GREEN + "Choose your difficulty" + RED + "(Set number from 1 to 54)");
            try {
                difficulty = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(GREEN + "You have set wrong number");
            }
        } while (difficulty < 1 || difficulty > 54);

        System.out.println(YELLOW + "-----------------------------------------------" + NONE);
        System.out.println(GREEN + "The format of a game: " + RED + "Use format 'AB1', where: A is a row, B is a collum, 1 is a number that you want to put in.");
        System.out.println(YELLOW + "-----------------------------------------------");
        System.out.println(GREEN + "So, if you want to write number" + RED + " 5 " + GREEN + "in a tile that on a" + RED + " B(second)row " + GREEN + "and" + RED + " E(fifth) collum, " + GREEN + "You have to use format" + RED + " 'BE5'. ");
        System.out.println(YELLOW + "-----------------------------------------------");
        System.out.println(BLUE + "---So Let's Start!---");
        System.out.println(YELLOW + "-----------------------------------------------");
        System.out.println(YELLOW + "---GOOD LUCK---");
        System.out.println(YELLOW + "-----------------------------------------------" + NONE);
    }

    private void GameLoop(Boxes boxes) {
        do {
            Display(this.field.getBoard(), boxes);
            HandleInput();
            if (field.isSolved()) {
                state = GameState.SOLVED;
            }
        } while (state == GameState.PLAYING);
    }

    private void Display(Tile[][] Board, Boxes boxes) {
        char[][] BoxesToDisplay = boxes.getPrintingField();
        int rowOfBoard = 0;
        DisplayCollumCharOfPosition();

        char rowCharToDisplay = 'A';
        for (int i = 0; i < 19; i++) {
            int colOfBoard = 0;
            if (i % 2 != 0) {
                System.out.print(rowCharToDisplay + " ");
                rowCharToDisplay++;
            } else System.out.print("  ");
            for (int j = 0; j < 19; j++) {
                if (BoxesToDisplay[i][j] == '0') {
                    if (Board[rowOfBoard][colOfBoard].getColorUsable() == Color.RED) {
                        System.out.print(NONE + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                    } else if (Board[rowOfBoard][colOfBoard].getColorUsable() == Color.WHITE) {
                        System.out.print(NONE + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                    } else switch (Board[rowOfBoard][colOfBoard].getColorDefault()) {
                        case CYAN -> System.out.print(CYAN + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case PURPLE -> System.out.print(PURPLE + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case YELLOW -> System.out.print(YELLOW + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case GREEN -> System.out.print(GREEN + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case BRIGHT_CYAN -> System.out.print(BRIGHT_CYAN + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case BRIGHT_PURPLE -> System.out.print(BRIGHT_PURPLE + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case BRIGHT_YELLOW -> System.out.print(BRIGHT_YELLOW + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case BLUE -> System.out.print(BLUE + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        case BRIGHT_GREEN -> System.out.print(BRIGHT_GREEN + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");
                        //default -> System.out.print(NONE + Board[rowOfBoard][colOfBoard].getValue() + NONE + " ");

                    }
                    colOfBoard++;
                } else
                    System.out.print(BoxesToDisplay[i][j] + " ");
            }
            if (colOfBoard == 9)
                rowOfBoard++;
            System.out.println();
        }
        System.out.print(BLUE + "(<EXIT> Exit, <AB1> Enter number in tile)");
    }

    private void DisplayCollumCharOfPosition() {
        char colCharToDisplay = 'A';
        System.out.print("  ");
        for (int i = 2; i < 21; i++) {
            if (i % 2 != 0) {
                System.out.print(colCharToDisplay + " ");
                colCharToDisplay++;
            } else System.out.print("  ");
        }
        System.out.println();
    }

    private void HandleInput() {
        String StringInput = scanner.nextLine().toUpperCase().replaceAll("\\s", "");
        PatternCompile(StringInput);
    }

    private void PatternCompile(String input) {
        if (input.equals("EXIT")) {
            DisplayEnd(false);
        }
        if (Pattern.compile("([A-I])([A-I])([0-9])").matcher(input).matches()) {
            int row = (int) input.charAt(0) - 65;  //ASCII
            int col = (int) input.charAt(1) - 65;
            int numberToWrite = (int) input.charAt(2) - 48;
            if (field.getBoard()[row][col].getState()) {
                if (field.LegalMove(row, col, numberToWrite)) {
                    field.getBoard()[row][col].setColorUsable(Color.WHITE);
                    field.getBoard()[row][col].setValue(numberToWrite);
                } else {
                    field.getBoard()[row][col].setColorUsable(Color.RED);
                    field.getBoard()[row][col].setValue(numberToWrite);
                }
            } else {
                    System.out.println(RED + "You can't change this tile" + NONE);
            }

        } else {
            System.out.println(RED + "Wrong input, try again with format AB1(A-row,B-collum,1-number)" + NONE);
        }
    }

    private void DisplayEnd(boolean win) {
        String name;

        setScore(time);
        if (win) {
            //System.out.println();
            System.out.println("You win!");
            System.out.println(BLUE + "Your points -> " + this.points + NONE);
            System.out.println("Enter your name:");
            name = scanner.nextLine();

            Score score = new Score("jigsaw",name,this.points,new Date());

            scoreService.addScore(score);

            printTopScores();

            RateOn(name,"jigsaw");

            CommentOn(name,"jigsaw");

        } else {
            System.out.println(RED + "---Unfortunately, You lose.---");
        }
        System.out.println("---Thanks for playing, Bye---");
        System.exit(0);
    }

    private void printTopScores(){
        System.out.println(BLUE + "Scoreboard:../n" + NONE);
        List<Score> topScores = scoreService.getTopScores("jigsaw");
        for(Score score : topScores){
            System.out.printf("%s %d\n", score.getPlayer(), score.getPoints());
        }
    }

    private void setScore(long time){
        time = System.currentTimeMillis() - time;
        time = time / 1000L;
        if(time < 300){
            this.points +=(100+difficulty*5);

        }
        else if(time < 600){
            this.points +=(50+difficulty*4);
        }
        else if(time < 900){
            this.points +=(30+difficulty*3);
        }
    }

    private void RateOn(String name, String game){
        System.out.println(BLUE + "Would you like to rate game? (yes/no)"+ NONE);
        String desicion = scanner.nextLine().toUpperCase().replaceAll("\\s","");
        if(!"YES".equals(desicion)){
            return;
        }
        int rating = 0;
        while (rating<1 || rating>5 ){
            System.out.println(BLUE + "Type from 1 to 5 to rate a game" + NONE);
            try {
                rating = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Only numbers..." + NONE);
            }
        }
        Rating totalRating = new Rating(game, name , rating,new Date());
        ratingService.setRating(totalRating);
        System.out.println( BLUE + "Your rating is ->" + ratingService.getRating(game,name) + NONE);
        System.out.println(YELLOW + "And the average rating is -> " + ratingService.getAverageRating(game) + NONE);
    }

    private void CommentOn(String name, String game){
        System.out.println(BLUE + "Would you like to add a comment? (yes/no)" + NONE);
        String decision = scanner.nextLine().toUpperCase().replaceAll("\\s","");
        if(!"YES".equals(decision))
            return;
        System.out.println(BLUE + "Leave a comment here :" + BLUE);
        String text = scanner.nextLine();
        Comment comment = new Comment(name, game,text, new java.util.Date());

        commentService.addComment(comment);
        System.out.println();
    }



}
