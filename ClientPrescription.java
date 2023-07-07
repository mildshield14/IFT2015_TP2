import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

class ClientPrescription{

    private int repetition;
    private ArrayList<Medicament> medicaments;
    private UUID idClient;
    private LocalDate dateObtenue;
    private LocalDate currentDate;

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
        String[] parts = line.split(" ");

        
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
        }else if (!((foundMed.getStock()>=total))){
            outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
        } else{
            System.out.println("Med does not exists??");
        }


        return outputstring;

               }

               public static void main(String[] args) {
             //   BST t = new BST();
    
// utiliser this.tree pour utiliser les methodes
// utiliset outputStock quand la ligne est Stock
Medicament m1 = new Medicament("Med1", UUID.randomUUID(), LocalDate.of(2023, 7, 1),100);
Medicament m2 = new Medicament("Med2", UUID.randomUUID(), LocalDate.of(2023, 7, 2),100);
Medicament m3 = new Medicament("Med3", UUID.randomUUID(), LocalDate.of(2023, 7, 3),100);
Medicament m4 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 4),200);
Medicament m5 = new Medicament("Med5", UUID.randomUUID(), LocalDate.of(2023, 7, 5),100);
Medicament m6 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 5),350);
Medicament m7 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 10),450);

BST.addMed(m3);

BST.addMed(m2);

BST.addMed(m1);

BST.addMed(m4);

BST.addMed(m7);

BST.addMed(m6);

BST.addMed(m5);

System.out.println(methodPrescription("Med4 50 20" , LocalDate.of(2024,12,12)));

BST.outputStock(LocalDate.of(2022,12,12));

System.out.println("\n");

BST.removeMed(m1);


BST.outputStock(LocalDate.of(2022,12,12));

System.out.println("\n");

BST.removeAllExpired( LocalDate.of(2023,7,3));

BST.outputStock(LocalDate.of(2022,12,12));



               }
  
}
