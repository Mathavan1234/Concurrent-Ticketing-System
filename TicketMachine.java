// Monitor Class - Shared Resource
public class TicketMachine implements ServiceTicketMachine {

    // Declared private attributes
    private final String id; // To store the machine's Name
    private int currentPaperLevelOfTicketMachine; // To count the current papers in the tray of the machine
    private int currentTonerLevelOfTicketMachine; // To count the toner level in the machine
    private int noOfPagesPrinted; // To count the total number of pages printed
    private int noOfTicketsPrinted; // To count the total number of tickets printed

    // Declared a threadgroup to manage passenger threads
    private final ThreadGroup passengerThreadGroup;

    private int noOfTonerCartridgeUsed;  // To get a count of how many times the toner is refilled
    private int noOfPaperPacksUsed; // To get a count of how many times the paper tray is refilled

    // Parameterized Constructor of the class
    public TicketMachine(String id, int currentPaperLevelOfTicketMachine, int currentTonerLevelOfTonerMachine, int noOfPagesPrinted, int noOfTicketsPrinted,ThreadGroup passengerThreadGroup) {
        this.id = id;
        this.currentPaperLevelOfTicketMachine = currentPaperLevelOfTicketMachine;
        this.currentTonerLevelOfTicketMachine = currentTonerLevelOfTonerMachine;
        this.noOfPagesPrinted = noOfPagesPrinted;
        this.noOfTicketsPrinted = noOfTicketsPrinted;
        this.passengerThreadGroup = passengerThreadGroup;
    }

    // Method to check whether all passengers have finished the ticket printing - it returns true or false
    private boolean passengerHasFinishedPrinting() {
        // if there is no active threads it returns true
        return passengerThreadGroup.activeCount() < 1;
    }

    // Method which returns the count of toner refilled
    public int getNoOfTonerCartridgeUsed() {
        return noOfTonerCartridgeUsed;
    }

    // Method which returns the count of paper refilled
    public int getNoOfPaperPacks() {
        return noOfPaperPacksUsed;
    }


    // Overriding the abstract printTicket method of Machine interface to give functionality for the passenger to print the ticket
    // It's declared as synchronized monitor method to ensure mutual exclusion
    @Override
    public synchronized void printTicket(Ticket ticket) {

        int noOfPageInTicket = 1; // Variable to store the number of pages of a ticket. (It is 1 for all the tickets)

        // Boolean variables are declared to check whether the number of pages and toner level is less than the required amount
        boolean lessNoOfPages = currentPaperLevelOfTicketMachine <= noOfPageInTicket;
        boolean lessTonerLevel = currentTonerLevelOfTicketMachine <= noOfPageInTicket;

        // while there is no page to print or no ink to print, passenger has to wait
        while (lessNoOfPages || lessTonerLevel) {
            try {
                if (currentTonerLevelOfTicketMachine <= noOfPageInTicket) {
                    // Printing a message to mention there is no sufficient amount of toner to print
                    System.out.println("(Can't refill the ticket machine with the toner cartridge, it will be a waste of toner.)");
                } else {
                    // Printing a message to mention there is no sufficient amount of paper to print
                    System.out.println("(Can't refill the printer with papers,because it will exceed the maximum amount of papers.)");
                }
                // Releasing  the monitor lock by wait() method
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // If there is sufficient amount of paper and toner then passengers can print the tickets (comes out of while loop)

        // Decrement the current paper level by the number of pages printed in the ticket
        currentPaperLevelOfTicketMachine -= noOfPageInTicket;
        // Decrement the current toner level by the number of pages printed in the ticket :- 1 unit of toner = 1 sheet of paper
        currentTonerLevelOfTicketMachine -= noOfPageInTicket;

        // To get the total page count of the machine which are printed
        noOfPagesPrinted += noOfPageInTicket;
        // Incrementing the printed ticket count
        noOfTicketsPrinted++;
        System.out.println("Number of pages for this ticket: " + noOfPageInTicket + ", Total number of Pages printed: " + noOfPagesPrinted + " , Total Ticket count: " + noOfTicketsPrinted);
        // Notifying all the threads which are in the runnable state to acquire the monitor lock
        notifyAll();
    }


    // Overriding the abstract refillPaper method of ServiceTicketMachine interface to give functionality for the ticket technician to refill the paper
    // It's declared as synchronized monitor method to ensure mutual exclusion
    @Override
    public synchronized void refillPaper() {
        // Declared a boolean value to check whether the number of papers are more than 200 (250 - 50)
        boolean hasEnoughPapers = currentPaperLevelOfTicketMachine >= (Full_Paper_Tray - SheetsPerPack);

        // If the paper tray has more than 200 pages the technician should wait
        while (hasEnoughPapers) {
            try {
                // The thread waits for 5 seconds - TIMED_WAITING STATE
                wait(5000);
                // it returns when passenger has printed finishing, if so no paper refill is needed
                if (passengerHasFinishedPrinting()) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // If the paper tray has less than 200 pages then ticket technician should refill
        System.out.println("[Ticket Technician] Refilled the paper tray with a paper bundle..");
        // He increments the paper count by 50
        currentPaperLevelOfTicketMachine += SheetsPerPack;

        // Increment the times that the paper tray is refilled
        this.noOfPaperPacksUsed++;

        // Notifying all the threads which are in the runnable state to acquire the monitor lock
        notifyAll();
    }

    // Overriding the abstract refillTonerCartridge method of ServiceTicketMachine interface to give functionality for the toner technician to refill the toner
    // It's declared as synchronized monitor method to ensure mutual exclusion
    @Override
    public synchronized void refillTonerCartridge() {
        // Declared a boolean value to check whether the number of toners are more than 10
        boolean hasEnoughToner = (currentTonerLevelOfTicketMachine >= Minimum_Toner_Level);
        // has enough toner
        while (hasEnoughToner) {
            try {
                //wait 5 seconds when toner is more than 10
                wait(5000);
                // it returns when passenger has printed finishing, if so no toner refill is needed
                if (passengerHasFinishedPrinting()) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // If the paper tray has less than 10 toners then toner technician should refill
        System.out.println("[Toner Technician] Refilled a new toner cartridge..");

        // Toner level is updated with 500
        currentTonerLevelOfTicketMachine = Full_Toner_Level;

        // Increment the times that the toner is refilled
        this.noOfTonerCartridgeUsed++;

        // Notifying all the threads which are in the runnable state to acquire the monitor lock
        notifyAll();
    }

    // Overriding the toString method to provide a string representation of the Ticket Machine object
    @Override
    public String toString() {
        return "[TicketMachineID: " + id +
                ", Paper Level: " + currentPaperLevelOfTicketMachine +
                ", Toner Level: " + currentTonerLevelOfTicketMachine +
                ", Tickets Printed: " + noOfTicketsPrinted + "]";
    }
}

