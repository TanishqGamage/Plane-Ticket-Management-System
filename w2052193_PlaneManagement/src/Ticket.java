import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private int row;
    private int seat;
    private int price;
    private Person person;

    /**
     * intialises the constructor
     * @param row  row of the seat
     * @param seat number of the seat
     * @param price price of the seat
     * @param person information of the buyer
     */
    public Ticket(int row, int seat, int price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    /**
     * @return the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * setter for the row number
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the seat number
     */
    public int getSeat() {
        return seat;
    }

    /**
     * setter for the seat number
     * @param seat
     */
    public void setSeat(int seat) {
        this.seat = seat;
    }

    /**
     * @return the price of the seat
     */
    public int getPrice() {
        return price;
    }

    /**
     * setter for the price of seat
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the information of the buyer
     */
    public Person getPerson() {
        return person;
    }

    /**
     * setter for the buyer's info
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * gets the row & seat number, price, and buyer's information
     * @return the row & seat number, price, and buyer's information
     */
    public String getTicketInfo(){
        return "Row           : "+(char)getRow()+"\n"+
                "Seat Number   : "+getSeat()+"\n"+
                "Price         : "+getPrice()+"\n"+
                "Customer Info "+"-".repeat(10)+"\n"+
                getPerson().getInfo()+"\n"+"-".repeat(50);
    }

    /**
     * saves the ticket information in a text file
     * opens a directory named "Tickets"
     * creates a file with the ticket name
     * writes the information in the text file
     */
    public void save(){
        try{
            File myDirectory = new File("Tickets");
            if (!myDirectory.exists()){
                myDirectory.mkdirs();
            }
            FileWriter fileWrite = new FileWriter("Tickets\\"+(char)getRow()+getSeat()+".txt");
            fileWrite.write(getTicketInfo());
            fileWrite.close();
        }
        catch (IOException e){
            System.out.println("There was an error while creating the file\n");
        }
    }

    /**
     * deletes the file with the ticket information when the cancelSeat() method is called
     * checks if the file with the ticket name exists and deletes it
     */
    public void delete(){
        File file = new File("Tickets\\"+(char)getRow()+getSeat()+".txt");
        if (file.exists()) file.delete();
        else{
            System.out.println("File not found\n");
        }
    }
}