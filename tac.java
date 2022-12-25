 
import java.util.Scanner;
import java.util.Random;

public class tac {
    static int get_int(int a, int b) {
        Scanner kbd = new Scanner(System.in);
        boolean outofrange;
        int input;
        do { 
          /*System.out.println("Enter a number between " + a + " and " + b + ":");*/
          input = kbd.nextInt();
          outofrange = (input < a || input > b); 
          if (outofrange) System.out.println("Out of range");
        } while (outofrange);
        return input;
    }

    static int get_move(int[] array) {
        boolean notempty;
        int input;
        do {
          System.out.println("Enter a move, 1-9");
          input = get_int(1, 9);
          notempty = (array[input - 1] != 0);
          if (notempty) System.out.println("Square occupied, try again");
        } while (notempty);
        return input;
    }

    static int random_move(int[] array, int mark, boolean move){
        Random rand = new Random();
        int r;
        do {
            r = rand.nextInt(9);   /* random in [0,8] */
        } while (array[r] != 0);
        if (move){
            array[r] = mark;  /* make the move on the board */
        }
        return r;  /* the legal move found, in [0,8] */
    }


    static int robot_move(int[] array, int mark, boolean move){
        int opmark = 3 - mark;

        if (check_win(array, mark)) {
            return 1;
        }
        if (check_win(array, opmark)) {
            return -1;
        }
        if (board_full(array)){  /* Neither side won and board is full */
            return 0;            /* So it is a draw */
        }
        /* Try all legal moves. Pick the first one with the highest score */
        int maxi = 0;
        int max  = -2;
        for (int i = 0; i < 9; i++){
            if (array[i] == 0){
                array[i] = mark;  /* make the move on the board */
                int m = -robot_move(array, opmark, false);
                if (m > max) {
                    max  = m;
                    maxi = i;
                }
                array[i] = 0;     /* take it back */
            }
        }
        if (move){
            array[maxi] = mark;  /* make the move on the board */
        }
        return max; /* the position score for "mark"  */
    }

    static boolean board_full(int[] array){
        boolean ret = 
          (array[0] != 0 && array[1] != 0 && array[2] != 0 && 
           array[3] != 0 && array[4] != 0 && array[5] != 0 && 
           array[6] != 0 && array[7] != 0 && array[8] != 0);
        /* if (ret) System.out.println("Board is full"); */
        return ret;
    }

    static boolean tie(int[] array){
        if (check_win(array, 1) || check_win(array, 2)) return false;
        if (board_full(array)) return true; /* neither side won, board is full */
        return false;  /* neither side won, board is not full */
    }

    static boolean check_win(int[] array, int mark) {
        return
           ((array[0] == mark && array[1] == mark && array[2] == mark) ||
            (array[3] == mark && array[4] == mark && array[5] == mark) ||
            (array[6] == mark && array[7] == mark && array[8] == mark) ||
            (array[0] == mark && array[3] == mark && array[6] == mark) ||
            (array[1] == mark && array[4] == mark && array[7] == mark) ||
            (array[2] == mark && array[5] == mark && array[8] == mark) ||
            (array[0] == mark && array[4] == mark && array[8] == mark) ||
            (array[2] == mark && array[4] == mark && array[6] == mark));
    }

    static void print_boardx(int[] array) {
        System.out.println(Integer.toString(array[0]) + array[1] + array[2]);
        System.out.println(Integer.toString(array[3]) + array[4] + array[5]);
        System.out.println(Integer.toString(array[6]) + array[7] + array[8]);
    }

    static void print_board(int[] array) {
        String[] c = {"   ", " X ", " O "};

        System.out.println("+---+---+---+");
        System.out.println("|"  + c[array[0]] + "|" 
                                + c[array[1]] + "|" 
                                + c[array[2]] + "|");

        System.out.println("+---+---+---+");
        System.out.println("|"  + c[array[3]] + "|" 
                                + c[array[4]] + "|" 
                                + c[array[5]] + "|");

        System.out.println("+---+---+---+");
        System.out.println("|"  + c[array[6]] + "|" 
                                + c[array[7]] + "|" 
                                + c[array[8]] + "|");

        System.out.println("+---+---+---+");
    }

    public static void main(String[] args) {
        int[] board = new int[9];
        int i = 0;
        Scanner kbd2 = new Scanner(System.in);
        System.out.println("Enter 1=Robot goes first, 2=Robot goes second:");
        int yorn = get_int(1, 2);
        int j = 0;
        if (yorn == 2) j = 1;

            for (int k = 0; k < 9; k++){
                if (j % 2 == 0) {
                    int score = robot_move(board, 1, true);
                    print_board(board);
                    System.out.println("Robot X moved, score = " + score);
                    if (check_win(board, 1)){
                        System.out.println("Game over. Robot X wins");
                        break;
                    }
                    if (tie(board)) System.out.println("Game over. Draw");
                    j++;
                }
                else {
                    int move = get_move(board);
                    board[move - 1] = 2;
                    print_board(board);
                    System.out.println("Player O moved");
                    if (check_win(board, 2)){
                        System.out.println("Game over. Player O wins");
                        break;
                    }
                    if (tie(board)) System.out.println("Game over. Draw");
                    j++;
                }
            }
        }
    }

