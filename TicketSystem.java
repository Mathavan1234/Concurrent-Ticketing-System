public class TicketSystem {

    public static void main(String[] args)  {

        //Created two thread groups for passengers and technicians
        ThreadGroup passengerThreadGroup = new ThreadGroup("Passenger Thread group");
        ThreadGroup technicianThreadGroup = new ThreadGroup("Technician Thread group");

        // Created an instance of shared monitor
        ServiceTicketMachine serviceTicketMachine = new TicketMachine("Ink Jet", 2, 5, 0, 0,passengerThreadGroup);

        // Created runnable instances of the passengers
        Runnable r1 = new Passenger(passengerThreadGroup,"Jay Cutler",serviceTicketMachine);
        Runnable r2 = new Passenger(passengerThreadGroup,"Kevin Levrone",serviceTicketMachine);
        Runnable r3 = new Passenger(passengerThreadGroup,"Sam Sulek",serviceTicketMachine);
        Runnable r4 = new Passenger(passengerThreadGroup,"Ronnie Coleman",serviceTicketMachine);

        // Created runnable instances of the technicians
        Runnable r5 = new TicketPaperTechnician(technicianThreadGroup,"George",serviceTicketMachine);
        Runnable r6 = new TicketTonerTechnician(technicianThreadGroup,"Sheldon",serviceTicketMachine);

        // Created passenger threads and passed the runnable instances as the target
        Thread passengerThreadOne = new Thread(passengerThreadGroup,r1,"Passenger One");
        Thread passengerThreadTwo = new Thread(passengerThreadGroup,r2,"Passenger Two");
        Thread passengerThreadThree = new Thread(passengerThreadGroup,r3,"Passenger Three");
        Thread passengerThreadFour = new Thread(passengerThreadGroup,r4,"Passenger Four");

        // Created technician threads and passed the runnable instances as the target
        Thread paperTechnicianThread = new Thread(technicianThreadGroup,r5,"Ticket Technician");
        Thread tonerTechnicianThread = new Thread(technicianThreadGroup,r6,"Toner Technician");

        // Starting all the threads NEW -> RUNNABLE STATE
        passengerThreadOne.start();
        passengerThreadTwo.start();
        passengerThreadThree.start();
        passengerThreadFour.start();

        paperTechnicianThread.start();
        tonerTechnicianThread.start();

        // Joining all the threads to execute concurrently
        try {
            passengerThreadOne.join();
            passengerThreadTwo.join();
            passengerThreadThree.join();
            passengerThreadFour.join();

            paperTechnicianThread.join();
            tonerTechnicianThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(serviceTicketMachine);
        System.out.println("Program has been executed successfully 🙂 !!!");
    }
}