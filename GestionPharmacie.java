import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class GestionPharmacie {

    protected static LocalDate currentDate;

  public static void setCurrentDate(LocalDate currentDate1){
    currentDate=currentDate1;
}

public static LocalDate getCurrentDate() {
    return currentDate;
}
  public static void stringToMed(String string){
        String[] theLine = string.split(" ");
        int num = Integer.parseInt(theLine[1]);
        String date1 = theLine[2];
        String[] date2 = date1.split("-");
        int year = Integer.parseInt(date2[0]);
        int month = Integer.parseInt(date2[1]);
        int day = Integer.parseInt(date2[2]);
        String med = theLine[0];
        Medicament medicament = new Medicament(med,UUID.randomUUID(),LocalDate.of(year,month,day),num);
        BST.addMed(medicament);
    }
    public static void readTheThing() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/exemple1.txt"));
            String line = reader.readLine();
            String instruction = "";
            int i = 1;
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/erm.txt"));
            
            while (line != null) {

                if (line.equals(";")) {
                    line = reader.readLine();
                    continue;
                }
                else if (line.contains("APPROV")) {
                    instruction = "APPROV";
                    line = reader.readLine();
                }
                else if (line.contains("PRESCRIPTION")) {
                    instruction = "PRESCRIPTION";
                    writer.write("\nPRESCRIPTION " + i + "\n");
                    i = i+1;
                    line = reader.readLine();
                }
                else if (line.contains("DATE")) {
                    String dateLine = line.replace("DATE ","");

                    dateLine = dateLine.replace(";", "");

                    dateLine = dateLine.replace(" ","");
                    writer.write(dateLine + " OK \n");
                    dateLine = dateLine.replace("-",",");

                    String[] dateeLine = dateLine.split(",");
                    int year = Integer.parseInt(dateeLine[0]);
                    int month = Integer.parseInt(dateeLine[1]);
                    int day = Integer.parseInt(dateeLine[2]);

                    setCurrentDate (LocalDate.of(year,month,day));
                    BST.removeAllExpired(LocalDate.of(year,month,day));
                    line = reader.readLine();
                    instruction = "DATE";
                }
                else if (line.contains("STOCK")) {
                    instruction = "STOCK";
                }
                if (instruction == "APPROV") {

                    stringToMed(line);
                    // check if always OK and if is unnecessary
                    line = reader.readLine();
                    if (line.equals("APPROV") || line.equals("PRESCRIPTION") || line.equals("DATE") || line.equals("STOCK")) {
                        writer.write("APPROV OK\n");
                    }

                }
                else if (instruction == "PRESCRIPTION") {
                    String output = methodPrescription(line,getCurrentDate());
                    writer.write(output + "\n");
                    line = reader.readLine();
                }
                else if (instruction == "STOCK") {
                    writer.write("\nSTOCK " + getCurrentDate() + "\n");
                    ArrayList<String> stock = BST.outputStock();
                    for (int j=0; j<stock.size(); j++){
                        writer.write(stock.get(j) + "\n");
                    }
                    line = reader.readLine();
                }
                else if (instruction == "DATE"){
                    continue;
                }
            }
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public  static String methodPrescription(String line, LocalDate date){
// TODO; change to \t
        String[] parts = line.split("\t");

        
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
readTheThing();
    // Lire le fichier (donnees en fichier en désordre; chercher les keywords)
    // Entrer les donnees dans les structures
    //          creer des objets Medicament, puis les rajouter à Stock. quand ya APPROV.
    //          sauvegarder dateObtenue dans une variable DATE.
    //          si STOCK, output Stock
    //          PRESCRIPTION, extract le nom du medicament - extract quantity du fuchier.
    //          Chercher dans stock en utilisant findClosestExpiryDate. Check si currentDate < Expiry Date
    //          va retourner un objet Medicament; check quantity du Medicament et quantite du Client et 
    //            si same OK, quaMed > quaPres ok, else COMMANDE
    //            if OK, remove quantity/ node in binary tree using deleteKey(UUID)    
    //            do necessary outputs of OK and COMMANDE. 
    //              IF COMMANDE, add to RECORD (if RECORD vide => Date courante et OK)
    //       
    //           au prochain DATE, juste avant, output RECORD. Juste apres, deleteAll.  
    
    //           until EOF
    }
}
