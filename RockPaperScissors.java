import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;

        while (true) {

            System.out.println("\n===== ROCK PAPER SCISSORS =====");
            System.out.println("1. Rock");
            System.out.println("2. Paper");
            System.out.println("3. Scissors");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int player = sc.nextInt();

            if (player == 0) {
                break;
            }

            if (player < 1 || player > 3) {
                System.out.println("Invalid choice!");
                continue;
            }

            int computer = random.nextInt(3) + 1;

            System.out.println("You chose: " + name(player));
            System.out.println("Computer chose: " + name(computer));

            if (player == computer) {
                System.out.println("DRAW!");
            }
            else if (
                    (player == 1 && computer == 3) ||
                    (player == 2 && computer == 1) ||
                    (player == 3 && computer == 2)
            ) {
                System.out.println("YOU WIN!");
                playerScore++;
            }
            else {
                System.out.println("COMPUTER WINS!");
                computerScore++;
            }

            System.out.println(
                    "Score: You " + playerScore +
                    " - Computer " + computerScore
            );
        }

        System.out.println("Game ended.");
        sc.close();
    }

    static String name(int choice) {

        if (choice == 1) {
            return "Rock";
        }
        else if (choice == 2) {
            return "Paper";
        }
        else {
            return "Scissors";
        }
    }
}