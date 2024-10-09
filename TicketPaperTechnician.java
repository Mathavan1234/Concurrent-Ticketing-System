// To use random sleep intervals between printing requests
import java.util.Random;

public class TicketPaperTechnician implements Runnable {
    // Creating the instance of the ServiceTicketMachine for the ticket technician to refill the paper
    private final ServiceTicketMachine serviceTicketMachine;
    private final  ThreadGroup technicianThreadGroup;
    private final String ticketTechnicianName;

    // Constructor to initialize the ticket technician thread group, name and service ticket machine
    public TicketPaperTechnician(ThreadGroup technicianThreadGroup,String ticketTechnicianName, ServiceTicketMachine serviceTicketMachine) {
        this.technicianThreadGroup = technicianThreadGroup;
        this.ticketTechnicianName = ticketTechnicianName;
        this.serviceTicketMachine = serviceTicketMachine;
    }

    // Getters
    @SuppressWarnings("unused")
    public ThreadGroup getTechnicianThreadGroup() {
        return technicianThreadGroup;
    }

    public String getTicketTechnicianName() {
        return ticketTechnicianName;
    }


    // Ticket technician refills the paper tray when it becomes less than 200 pages
    @Override
    public void run() {
        // Ticket technician is allowed to refill only 3 times
        for (int i = 1; i <= 3; i++) {
            // Looping and refills the paper 3 times
            serviceTicketMachine.refillPaper();

            // Random sleep intervals between printing requests
            Random randomTime = new Random();
            int random = randomTime.nextInt(10);
            try {
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Printing the number of times paper has refilled
        System.out.println("[Ticket Technician] "+ getTicketTechnicianName() + " is in " + Thread.currentThread().getThreadGroup().getName() + " Finished refilling, packs of paper used : " + ((TicketMachine) serviceTicketMachine).getNoOfPaperPacks() +" ]");

    }

}


