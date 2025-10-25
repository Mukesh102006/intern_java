import java.util.*;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class TASK4 {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> users = new HashMap<>();
    static int score = 0;

    public static void main(String[] args) {
        users.put("admin", new User("admin", "1234")); // default user

        System.out.println("===== Welcome to Online Examination System =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    if (login()) {
                        System.out.println("\nLogin Successful!");
                        menu();
                    } else {
                        System.out.println("Too many failed attempts. Exiting...");
                    }
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // ----------- Register New User -----------
    static void register() {
        System.out.print("\nEnter new username: ");
        String uname = sc.nextLine();

        if (users.containsKey(uname)) {
            System.out.println("Username already exists! Try a different one.");
            return;
        }

        System.out.print("Enter new password: ");
        String pass = sc.nextLine();

        users.put(uname, new User(uname, pass));
        System.out.println("User registered successfully!");
    }

    // ----------- Login -----------
    static boolean login() {
        for (int i = 0; i < 3; i++) {
            System.out.print("\nEnter Username: ");
            String uname = sc.nextLine();
            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            User user = users.get(uname);
            if (user != null && user.password.equals(pass)) {
                return true;
            } else {
                System.out.println("Invalid username or password! Attempts left: " + (2 - i));
            }
        }
        return false;
    }

    // ----------- Main Menu -----------
    static void menu() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Start Exam");
            System.out.println("2. Update Profile and Password");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    startExam();
                    break;
                case "2":
                    updateProfile();
                    break;
                case "3":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ----------- Start Exam -----------
    static void startExam() {
        System.out.println("\nExam Started! Answer the following questions:");

        Question[] questions = {
            new Question("Which keyword is used to inherit a class in Java?",
                    new String[]{"super", "this", "extends", "implements"}, 3),
            new Question("Which of these is not an OOP concept?",
                    new String[]{"Encapsulation", "Polymorphism", "Compilation", "Inheritance"}, 3),
            new Question("What is the default value of a boolean variable in Java?",
                    new String[]{"true", "false", "null", "0"}, 2),
            new Question("Which package contains the Scanner class?",
                    new String[]{"java.io", "java.util", "java.net", "java.lang"}, 2),
            new Question("Which method is used to start a thread?",
                    new String[]{"start()", "run()", "init()", "execute()"}, 1)
        };

        score = 0;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < questions.length; i++) {
            Question q = questions[i];
            System.out.println("\nQ" + (i + 1) + ": " + q.question);
            for (int j = 0; j < q.options.length; j++) {
                System.out.println((j + 1) + ". " + q.options[j]);
            }

            int answer = getValidAnswer();
            if (answer == q.correctAnswer) {
                score++;
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        System.out.println("\n===== Exam Completed =====");
        System.out.println("Your Score: " + score + "/" + questions.length);
        double percentage = (score * 100.0) / questions.length;
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Time Taken: " + duration + " seconds");

        if (score >= 3) {
            System.out.println("Congratulations! You passed the exam.");
        } else {
            System.out.println("Better luck next time!");
        }
    }

    // ----------- Get Valid Answer -----------
    static int getValidAnswer() {
        while (true) {
            System.out.print("Your answer (1-4): ");
            String input = sc.nextLine();
            try {
                int ans = Integer.parseInt(input);
                if (ans >= 1 && ans <= 4) {
                    return ans;
                } else {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
            }
        }
    }

    // ----------- Update Profile -----------
    static void updateProfile() {
        System.out.print("\nEnter your username: ");
        String uname = sc.nextLine();
        User user = users.get(uname);

        if (user != null) {
            System.out.print("Enter your current password: ");
            String oldPass = sc.nextLine();

            if (user.password.equals(oldPass)) {
                System.out.print("Enter new password: ");
                String newPass = sc.nextLine();
                user.password = newPass;
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Incorrect old password!");
            }
        } else {
            System.out.println("User not found!");
        }
    }
}
