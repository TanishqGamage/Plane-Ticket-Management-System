import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class w2052193_PlaneManagement {
    public static Scanner sc = new Scanner(System.in);
    public static final int[][] seats = new int[4][];   // intialises a ragged array for seats
    static {
        seats[0] = new int[14];
        seats[1] = new int[12];
        seats[2] = new int[12];
        seats[3] = new int[14];
    }
    public static final Ticket[][] tickets = new Ticket[4][];    // intialises a ragged array for tickets
    static {
        tickets[0] = new Ticket[14];
        tickets[1] = new Ticket[12];
        tickets[2] = new Ticket[12];
        tickets[3] = new Ticket[14];
    }
    public static int totalPrice = 0;    // counter for total ticket sales
    public static int Row;        // seat row
    public static int Seat;       // seat number

    public static void main(String[] args) {

        // when re-running the program deletes all previous data stored (tickets,customerInfo etc.)
        File folder = new File("Tickets");
        if (!folder.exists()) return;
        try {
            String[] subFiles = folder.list();
            for (String fileName : subFiles) {
                File subFile = new File(folder.getPath(),fileName);
                subFile.delete();
            }
        }
        catch (NullPointerException e){
            System.out.println();
        }
        // prints the menu
        System.out.println("Welcome to the Plane Management application");
        while (true){

            System.out.println("\n"+"*".repeat(50));
            System.out.println("*"+" ".repeat(18)+"MENU OPTIONS"+" ".repeat(18)+"*");
            System.out.println("*".repeat(50));
            System.out.println("\t1) Buy a seat");
            System.out.println("\t2) Cancel a seat");
            System.out.println("\t3) Find first available seat");
            System.out.println("\t4) Show seating plan");
            System.out.println("\t5) Print tickets information and total sales");
            System.out.println("\t6) Search ticket");
            System.out.println("\t0) Quit");
            System.out.println("*".repeat(50));

            try {
                System.out.print("\tPlease select an option: ");
                int Option = sc.nextInt();

                if (Option == 0){
                    System.out.println("Quit");       // quits programme
                    break;
                }
                if(Option <=6){
                    switch (Option){
                        case 1:
                            System.out.println("\n--- Seat Purchase "+"-".repeat(15));
                            buy_seat();
                            break;
                        case 2:
                            System.out.println("\n--- Seat Cancelation "+"-".repeat(15));
                            cancel_seat();
                            break;
                        case 3:
                            System.out.println("\n--- First Available Seat "+"-".repeat(10));
                            find_first_available();
                            break;
                        case 4:
                            System.out.println("\n--- Seating Plan "+"-".repeat(15));
                            show_seating_plan();
                            break;
                        case 5:
                            System.out.println("\n--- Ticket information and Total sales "+"-".repeat(10));
                            print_tickets_info();
                            break;
                        case 6:
                            System.out.println("\n--- Search ticket "+"-".repeat(15));
                            search_ticket();
                            break;
                        default:
                            System.out.println("Please enter a valid option !");
                    }
                }
                else
                    System.out.println("Enter a valid input !");
            }
            catch(InputMismatchException ex){
                System.out.println("Please enter valid Option !");
                sc.next();
            }
        }
    }

    // gets user inputs for Seat Row and Seat number
    public static void getUserInput(){
        while (true) {
            System.out.print("Enter Row Letter (A, B, C, D): ");
            Row = sc.next().toUpperCase().charAt(0);
            if(Row >= 65 && Row <=68){
                break;
            }
            System.out.println("Enter a valid Row !\n");
        }
        while (true) {
            try {
                System.out.print("Enter Seat number \nA,D-[1-14] \nB,C-[1-12]: ");
                Seat = sc.nextInt();
                if (Row == 65 || Row == 68) {           // checks if seat row is A or D
                    if (Seat >= 1 && Seat <= 14) {
                        break;
                    } else {
                        System.out.println("Enter a valid seat number !");
                        continue;
                    }
                }
                if (Row == 66 || Row == 67) {            // checks if seat row is B or D
                    if (Seat >= 1 && Seat <= 12) {
                        break;
                    } else {
                        System.out.println("Enter a valid seat number !");
                        continue;
                    }
                }
            }
            catch (InputMismatchException e){
                System.out.println("Enter a valid seat number !");
                sc.next();
            }
            System.out.println();
        }
    }

    /**
     * sets the ticket price based on users input
     * @param seat the seat number selected by user
     * @return the ticket price
     */
    public static int getTicketPrice(int seat){
        int price = 0;
        if(seat >= 0 && seat <= 5){
            price = 200;
        } else if (seat >= 6 && seat <= 9) {
            price = 150;
        } else if (seat >= 10 && seat <= 14){
            price = 180;
        }
        return price;
    }

    // reserves the seat by taking user's input
    public static void buy_seat(){
        while(true) {
            try {
                getUserInput();

                int rowIndex = Row - 65;          //calculates the array index using char value
                int seatIndex = Seat - 1;

                if (seats[rowIndex][seatIndex] == 1) {          // checks if seat is available
                    System.out.println("seat already sold\n");
                    continue;
                }
                Person customer = getPersonInfo();
                if (seats[rowIndex][seatIndex] == 0) {          // if seat is available; reserves the seat
                    seats[rowIndex][seatIndex] = 1;
                    System.out.println("Seat sold\n");
                    reserve_ticket(Row, Seat, customer);
                }
                totalPrice += getTicketPrice(Seat);          // adds the current ticket price to the total
                break;
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Enter a valid seat number !");
                sc.next();
            }
        }

    }

    //gets the user's information after purchasing the seat
    public static Person getPersonInfo(){
        System.out.println("Enter your first name : ");
        String fName = sc.next();
        System.out.println("Enter your last name : ");
        String lName = sc.next();
        String email;
        while (true) {
            System.out.println("Enter your email : ");
            email = sc.next();
            if (!email.contains("@") || (!email.contains("."))) {      // checks if the email is valid
                System.out.println("Enter a valid email");
                continue;
            }
            break;
        }
        System.out.println();
        return new Person(fName,lName,email);
    }

    /**
     * adds the selected seat number and row to a ticket array
     * @param row  the seat row
     * @param seat the seat number
     * @param person the information of the buyer
     */
    public static void reserve_ticket(int row, int seat, Person person){
        Ticket ticket = new Ticket(row,seat,getTicketPrice(seat),person);
        tickets[row-65][seat-1] = ticket;
        ticket.save();                      // saves the ticket ina .txt file
    }

    /**
     * iterates through the ticket array
     * if the index is not null it prints the ticket information,
     * using the getTicketInfo() method
     * and prints the total ticket prices
     */
    public static void print_tickets_info(){
        for (int i = 0; i < tickets.length; i++){
            for (int j = 0; j < tickets[i].length; j++){
                Ticket ticket = tickets[i][j];
                if (ticket != null){
                    String ticketInfo =  ticket.getTicketInfo();
                    System.out.println(ticketInfo);
                }
            }
        }
        System.out.println("\nTotal Ticket Price "+totalPrice);
    }

    /**
     * this cancels the seat selected by user
     * checks if the seat is already booked and deletes the file with the name of the seat,
     * turns the index of that ticket to null in the ticket array
     */
    public static void cancel_seat(){
        while (true){
            getUserInput();

            int rowIndex = Row - 65;
            int seatIndex = Seat - 1;

            if (seats[rowIndex][seatIndex] == 1){
                seats[rowIndex][seatIndex] = 0;

                tickets[rowIndex][seatIndex].delete();
                tickets[rowIndex][seatIndex] = null;

                totalPrice -= getTicketPrice(Seat);
                System.out.println("Seat Canceled\n");
                break;
            }
            else if (seats[rowIndex][seatIndex] == 0) {
                System.out.println("This seat isn't booked to be cancelled\n");
                break;
            }
        }
    }

    /**
     * finds the first available seat int the seating plan
     * iterates through the seat array , if index is null prints the seat row and number
     */
    public static void find_first_available(){
        boolean seatFound = false;
        String[] rowletter = new String[]{"A","B","C","D"};
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                   if (seats[i][j] == 0){
                       System.out.println("Seat found "+rowletter[i]+""+(j+1)+"\n");
                       seatFound = true;
                   }
                if (seatFound) break;
            }
            if (seatFound) break;
        }
        if (!seatFound) System.out.println("No available seats\n");
    }

    /**
     * prints the seating plan
     * iterates through the seats array;
     * if index is null it prints as "0" , if not null prints as "X"
     */
    public static void show_seating_plan(){
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] == 0 ? " O" : " X");
            }
            System.out.println();
        }
    }

    /**
     * prints the information of a seat selected by user
     * iterates through the ticket array if it matches the input given by user,
     * and it's not null; prints the information
     */
    public static   void search_ticket(){
        getUserInput();
        int rowIndex = Row - 65;
        int seatIndex = Seat - 1;
        for (int i = 0; i < tickets.length; i++){
            for (int j = 0; j < tickets[i].length; j++){
                if (rowIndex == i && seatIndex == j){
                    if(tickets[i][j] != null){
                        Ticket ticket = tickets[i][j];
                        String ticketInfo =  ticket.getTicketInfo();
                        System.out.println(ticketInfo);
                    }
                    else{
                        System.out.println("This seat is available\n");
                    }
                }
            }
        }
    }
}