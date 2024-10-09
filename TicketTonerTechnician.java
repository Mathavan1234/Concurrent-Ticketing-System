// To use random sleep intervals between printing requests

import java.util.Random;

public class TicketTonerTechnician implements Runnable {
    // Creating the instance of the ServiceTicketMachine for the toner technician to refill the toner
    private final ServiceTicketMachine serviceTicketMachine;
    private final ThreadGroup technicianThreadGroup;
    private final String tonerTechnicianName;

    // Constructor to initialize the toner technician thread group, name and service ticket machine
    public TicketTonerTechnician(ThreadGroup technicianThreadGroup,String tonerTechnicianName,ServiceTicketMachine servicePrinter) {
        this.technicianThreadGroup = technicianThreadGroup;
        this.tonerTechnicianName = tonerTechnicianName;
        this.serviceTicketMachine = servicePrinter;
    }

    // Getters
    @SuppressWarnings("unused")
    public ThreadGroup getTechnicianThreadGroup() {
        return technicianThreadGroup;
    }

    public String getTonerTechnicianName() {
        return tonerTechnicianName;
    }

    // Ticket technician refills the toner when it becomes less than 10
    @Override
    public void run() {
        // Toner technician is allowed to refill only 3 times
        for (int i = 1; i <= 3; i++) {
            // Looping and refills the paper 3 times
            serviceTicketMachine.refillTonerCartridge();

            // Random sleep intervals between printing requests
            Random randomTime = new Random();
            int random = randomTime.nextInt(10);
            try {
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Printing the number of times toner has refilled
        System.out.println("[ "+ getTonerTechnicianName() + " is in " + Thread.currentThread().getThreadGroup().getName() + "Finished refilling the toner, Units of cartridges replaced: " + ((TicketMachine) serviceTicketMachine).getNoOfTonerCartridgeUsed() +" ]");
    }

}

