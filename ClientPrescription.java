import java.util.ArrayList;
import java.util.UUID;

class ClientPrescription{

    private int repetition;
    private ArrayList<Medicament> medicaments;
    private UUID idCLient;

    public Client(int repetition, ArrayList<Medicament> medicaments, UUID idClient) {
        this.repetition = repetition;
        this.medicaments = medicaments;
        this.idClient = idClient;
    }

    public int getRepetition() { return repetition; }
    public ArrayList<Medicament> getMedicaments() { return repetition; }
    public UUID getIdClient() { return idClient; }
}
