import java.util.Scanner;

class Rr {
    public int index;       // index of the process
    public int id;          // ID of the process
    public int f;           // flag to check if message was sent
    public String state;    // active or inactive
}

public class Ring {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int temp, i, j;

        Rr[] proc = new Rr[10];
        for (i = 0; i < proc.length; i++)
            proc[i] = new Rr();

        System.out.print("Enter the number of processes: ");
        int num = in.nextInt();

        for (i = 0; i < num; i++) {
            proc[i].index = i;
            System.out.print("Enter the ID of process " + (i + 1) + ": ");
            proc[i].id = in.nextInt();
            proc[i].state = "active";
            proc[i].f = 0;
        }

        // Sort the processes by ID
        for (i = 0; i < num - 1; i++) {
            for (j = 0; j < num - 1 - i; j++) {
                if (proc[j].id > proc[j + 1].id) {
                    temp = proc[j].id;
                    proc[j].id = proc[j + 1].id;
                    proc[j + 1].id = temp;
                }
            }
        }

        System.out.println("Sorted Processes by ID:");
        for (i = 0; i < num; i++) {
            System.out.print(" [" + i + "] " + proc[i].id);
        }
        System.out.println();

        // Initially, the last process becomes coordinator
        proc[num - 1].state = "inactive";
        System.out.println("Process " + proc[num - 1].id + " selected as coordinator");

        int ch;
        while (true) {
            System.out.println("\n1. Election\n2. Quit");
            System.out.print("Enter your choice: ");
            ch = in.nextInt();

            for (i = 0; i < num; i++) {
                proc[i].f = 0;  // reset flags
            }

            switch (ch) {
                case 1:
                    System.out.print("Enter the process number (1 to " + num + ") that initiates election: ");
                    int init = in.nextInt() - 1;
                    int temp1 = (init + 1) % num;
                    int temp2 = init;
                    int[] arr = new int[10];
                    i = 0;

                    while (temp2 != temp1) {
                        if ("active".equals(proc[temp1].state) && proc[temp1].f == 0) {
                            System.out.println("Process " + proc[init].id + " sends message to " + proc[temp1].id);
                            proc[temp1].f = 1;
                            init = temp1;
                            arr[i++] = proc[temp1].id;
                        }
                        temp1 = (temp1 + 1) % num;
                    }

                    System.out.println("Process " + proc[init].id + " sends message to " + proc[temp1].id);
                    arr[i++] = proc[temp1].id;

                    int max = -1;
                    for (j = 0; j < i; j++) {
                        if (arr[j] > max) {
                            max = arr[j];
                        }
                    }

                    System.out.println("Process " + max + " selected as coordinator");
                    for (i = 0; i < num; i++) {
                        if (proc[i].id == max) {
                            proc[i].state = "inactive";
                        }
                    }
                    break;

                case 2:
                    System.out.println("Program terminated.");
                    return;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
