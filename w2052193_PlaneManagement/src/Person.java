public class Person {
    private String firstName;
    private String surname;
    private String email;

    /**
     * intialises the constructor
     * @param firstName first name of the buyer
     * @param surname  surname(last name) of the buyer
     * @param email email of the buyer
     */
    public Person(String firstName, String surname, String email){
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
    }

    /**
     * @return first name of the buyer
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * setter for the first name of the buyer
     * @param firstName
     */
    public void setFirstName(String firstName){this.firstName = firstName;}

    /**
     * @return surname(last name) of the buyer
     */
    public String getSurname(){
        return this.surname;
    }

    /**
     * setter for the surname of the buyer
     * @param surname
     */
    public void setSurname(String surname) {this.surname = surname;}

    /**
     * @return email of the buyer
     */
    public String getEmail(){
        return  this.email;
    }

    /**
     * setter for the email of the buyer
     * @param email
     */
    public void setEmail(String email) {this.email = email;}

    /**
     * converts the values to string
     * @return information of the buyer
     */
    @Override
    public String toString() {
        return this.firstName + surname + email;
    }

    /**
     * @return information of the buyer
     */
    public String getInfo(){
        return "First Name    : "+getFirstName()+"\n"+
                "Surname       : "+getSurname()+"\n"+
                "Email         : "+getEmail();
    }
}