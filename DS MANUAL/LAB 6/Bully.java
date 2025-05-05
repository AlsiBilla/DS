import java.util.Scanner;

public class Bully {
    static boolean[] state = new boolean[5]; // state[i] is true if process i+1 is up
    static int coordinator = 5; // Assuming process 5 is initially the coordinator

    public static void up(int up) {
        if (state[up - 1]) {
            System.out.println("Process " + up + " is already up");
        } else {
            state[up - 1] = true;
            System.out.println("Process " + up + " held an election");
            for (int i = up; i < 5; i++) {
                System.out.println("Election message sent from process " + up + " to process " + (i + 1));
            }

            for (int i = 5; i > up; i--) {
                if (state[i - 1]) {
                    coordinator = i;
                    System.out.println("Coordinator message sent from process " + i + " to all");
                    break;
                }
            }
        }
    }

    public static void down(int down) {
        if (!state[down - 1]) {
            System.out.println("Process " + down + " is already down.");
        } else {
            state[down - 1] = false;
            System.out.println("Process " + down + " is brought down.");
            if (down == coordinator) {
                coordinator = -1; // No current coordinator
            }
        }
    }

    public static void mess(int mess) {
        if (!state[mess - 1]) {
            System.out.println("Process " + mess + " is down");
            return;
        }
    
        if (coordinator != -1 && state[coordinator - 1]) {
            System.out.println("OK (Coordinator is process " + coordinator + ")");
        } else {
            System.out.println("Process " + mess + " initiates an election");
            for (int i = mess; i < 5; i++) {
                System.out.println("Election message sent from process " + mess + " to process " + (i + 1));
            }
    
            for (int i = 5; i >= mess; i--) {
                if (state[i - 1]) {
                    coordinator = i;
                    System.out.println("Coordinator message sent from process " + i + " to all");
                    break;
                }
            } 
        }
    }
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initially, all processes are up
        for (int i = 0; i < 5; i++) {
            state[i] = true;
        }

        System.out.println("5 active processes: p1 p2 p3 p4 p5");
        System.out.println("Process 5 is the initial coordinator");

        int choice;
        do {
            System.out.println("\n------ Menu ------");
            System.out.println("1. Bring up a process");
            System.out.println("2. Bring down a process");
            System.out.println("3. Send a message");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter process number to bring up (1-5): ");
                    int up = sc.nextInt();
                    if (up < 1 || up > 5) {
                        System.out.println("Invalid process number.");
                    } else {
                        up(up);
                    }
                    break;

                case 2:
                    System.out.print("Enter process number to bring down (1-5): ");
                    int down = sc.nextInt();
                    if (down < 1 || down > 5) {
                        System.out.println("Invalid process number.");
                    } else {
                        down(down);
                    }
                    break;

                case 3:
                    System.out.print("Enter process number to send message (1-5): ");
                    int mess = sc.nextInt();
                    if (mess < 1 || mess > 5) {
                        System.out.println("Invalid process number.");
                    } else {
                        mess(mess);
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }
}
