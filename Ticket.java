public class Ticket {
    // Declaring private variables to store passengerID and ticketName
    private final String passengerID;
    private final String ticketName;

    // Constructor to initialize Ticket object with passengerID and ticketName
    public Ticket(String passengerID, String ticketName ) {
        this.passengerID = passengerID ;
        this.ticketName = ticketName ;
    }

    // Getter method to retrieve the ticket name
    public String getTicketName( )  {
        return ticketName;
    }

    // Overriding the toString method to provide a string representation of the Ticket object
    public String toString( ) {
        return "Ticket[ " +
                "PassengerID: " + passengerID + ", " +
                "Ticket Name: " + ticketName + ", "  +
                "]";
    }
}