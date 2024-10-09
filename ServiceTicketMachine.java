// This interface extends the Machine interface
public interface ServiceTicketMachine extends Machine {

    // Declared constant value for the maximum paper tray capacity
    int Full_Paper_Tray = 250;

    // Declared constant value for the number of paper sheets per pack
    int SheetsPerPack = 50;

    // Declared constant value for the full toner level
    int Full_Toner_Level = 500;

    // Declared constant value for the minimum toner level
    int Minimum_Toner_Level = 10;

    // Abstract method to refill toner cartridge by toner technician
    void refillTonerCartridge();

    // Abstract method to refill paper by ticket technician
    void refillPaper();
}
