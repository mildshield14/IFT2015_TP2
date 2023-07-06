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

    public String methodPrescription(String line, LocalDate date){

        String[] parts = line.split("\t");

        String med = parts[0];  
        int num1 = Integer.parseInt(parts[1]);  
        int num2 = Integer.parseInt(parts[2]); 

        int total = num1*num2;
      
        Medicament foundMed = this.tree.findClosest(med,date);

        String outputstring="";

       if (foundMed.getStock()>=total){
            this.tree.removeMed(foundMed);
            foundMed.setStock(foundMed.getStock()-total);
            this.tree.addMed(foundMed);
            outputstring=med + num1 + num2 + "OK";
        }else if (!((foundMed.getStock()>=total))){
            outputstring=med + num1 + num2 + "COMMANDE";
        } else{
            System.out.println("Med does not exists??");
        }


        return outputstring;

               }

               public static void main(String[] args) {
                
// utiliser this.tree pour utiliser les methodes
// utiliset outputStock quand la ligne est Stock;


               }
  
}
