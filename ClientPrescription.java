import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

class ClientPrescription{

    private int repetition;
    private ArrayList<Medicament> medicaments;
    private UUID idClient;
    private LocalDate dateObtenue;
    private static BST tree = new BST();
    private static LocalDate currentDate;


    public ClientPrescription(int repetition, ArrayList<Medicament> medicaments, UUID idClient) {
        this.repetition = repetition;
        this.medicaments = medicaments;
        this.idClient = idClient;
    }

    public int getRepetition() { return repetition; }
    public ArrayList<Medicament> getMedicaments() { return medicaments; }
    public UUID getIdClient() { return idClient; }


    public void setDate(LocalDate dateCurrent){
        this.currentDate=dateCurrent;
    }

    public  static String methodPrescription(String line, LocalDate date){
// TODO; change to \t
        String[] parts = line.split("\\s+");


        int num1 = Integer.parseInt(parts[1]);
        int num2 = Integer.parseInt(parts[2]);
        String med = parts[0];

        int total = num1*num2;

        Medicament foundMed = BST.findClosest(med,date);

        String outputstring="";

        if (foundMed !=null && foundMed.getStock()>=total){

            BST.removeMed(foundMed);
            foundMed.setStock(foundMed.getStock()-total);
            BST.addMed(foundMed);
            outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "OK");
        }else if (foundMed != null && !(foundMed.getStock()>=total)){
            outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
        } else if (foundMed == null){
            outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
        }


        return outputstring;

    }

    public static void main(String[] args) {

// utiliser this.tree pour utiliser les methodes
// utiliset outputStock quand la ligne est Stock;


    }

}
