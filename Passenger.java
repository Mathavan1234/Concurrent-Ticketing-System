// To use random sleep intervals between printing requests
import java.util.Random;

public class Passenger implements Runnable {
    // Creating the instance of the Machine for passenger to print the ticket
    private final Machine ticketMachine;
    private final ThreadGroup passengerThreadGroup;
    private final String passengerName;

    // Constructor to initialize the passenger thread group, name and ticket machine
    public Passenger(ThreadGroup passengerThreadGroup,String passengerName, Machine ticketMachine) {
        this.passengerThreadGroup = passengerThreadGroup;
        this.passengerName = passengerName;
        this.ticketMachine = ticketMachine;
    }

    // Getters
    @SuppressWarnings("unused")
    public ThreadGroup getPassengerThreadGroup() {
        return passengerThreadGroup;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void run() {
        // Created a tickets array and initialized it with 5 tickets
        Ticket[] tickets = new Ticket[5];

        // Initializes each ticket with a passenger
        tickets[0] = new Ticket("1", " \"Ticket 1\" ");
        tickets[1] = new Ticket("2", " \"Ticket 2\" ");
        tickets[2] = new Ticket("3", " \"Ticket 3\" ");
        tickets[3] = new Ticket("4", " \"Ticket 4\" ");
        tickets[4] = new Ticket("5", " \"Ticket 5\" ");

        // Variable which calculates the total number of pages
        int paperCountPerPassenger = 0;

        for (Ticket ticket : tickets) {
            // Looping and printing tickets one after the other
            ticketMachine.printTicket(ticket);
            System.out.println("Ticket," + ticket.getTicketName() + "printed by " + Thread.currentThread().getName());
            paperCountPerPassenger++;
            // Random sleep intervals between printing requests
            Random randomTime = new Random();
            int random = randomTime.nextInt(10);
            try {
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Printing the number of times paper has printed
        System.out.println("Passenger: " + getPassengerName() + " is in " + Thread.currentThread().getThreadGroup().getName()  + " has Finished Printing: " + tickets.length + " Tickets, with " + paperCountPerPassenger + " papers");
    }

}



