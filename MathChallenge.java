import java.util.Random;
import java.util.Scanner;

public class MathChallenge {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int score = 0;

        System.out.println("========================");
        System.out.println("     MATH CHALLENGE");
        System.out.println("========================");

        for (int question = 1; question <= 10; question++) {

            int a = random.nextInt(20) + 1;
            int b = random.nextInt(20) + 1;

            int operation =
                    random.nextInt(3);

            int answer;

            System.out.print(
                    "\nQuestion " + question + ": "
            );

            if (operation == 0) {

                System.out.print(
                        a + " + " + b + " = "
                );

                answer = a + b;
            }

            else if (operation == 1) {

                System.out.print(
                        a + " - " + b + " = "
                );

                answer = a - b;
            }

            else {

                System.out.print(
                        a + " x " + b + " = "
                );

                answer = a * b;
            }

            int userAnswer = sc.nextInt();

            if (userAnswer == answer) {

                System.out.println("Correct!");
                score++;
            }
            else {

                System.out.println(
                        "Wrong! Correct answer = " +
                        answer
                );
            }
        }

        System.out.println(
                "\nFinal Score: " +
                score + "/10"
        );

        if (score == 10) {
            System.out.println("Excellent!");
        }
        else if (score >= 7) {
            System.out.println("Very Good!");
        }
        else if (score >= 5) {
            System.out.println("Good!");
        }
        else {
            System.out.println("Keep Practicing!");
        }

        sc.close();
    }
}