import java.util.ArrayList;
import java.util.UUID;

class ClientPrescription{

    private int repetition;
    private ArrayList<Medicament> medicaments;
    private UUID idCLient;

    public Client(int repetition, ArrayList<Medicament> medicaments, UUID idClient) {
        this.repetition = repetition;
    }

    
}
