// Absalon Marion (20211423) et Sooben Vennila (20235256)
// IFT 2015 - TP2
// Gestion Pharmacie

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Tp2 {

  protected static LocalDate currentDate;
  protected  static  List<Record> recordCommande = new ArrayList<>();

    // ceci permet de sauvegarder la commande comme demandé dans l'énoncé
    record Record(String name, int number) {}


  // cette méthode va effectuer la lecture du fichier ainsi que l'ecriture
  public static void readTheThing(String read, String write) {
    BufferedReader reader;
    boolean first = true;

    try {
      reader = new BufferedReader(new FileReader(read));
      String line = reader.readLine();
      String instruction = "";

      int i = 1;
      BufferedWriter writer = new BufferedWriter(new FileWriter(write));

      while (line != null) {

        if (line.equals(";")&& instruction.equals("PRESCRIPTION")){
          writer.write("\n");
        }

        if (line.contains(";") || line.equals("")){
          line = reader.readLine();
          continue;
        }
        else if (line.contains("APPROV")) {
          instruction = "APPROV";
          line = reader.readLine();
        }
        else if (line.contains("PRESCRIPTION")) {
          instruction = "PRESCRIPTION";
          writer.write("PRESCRIPTION " + i + "\n");
          i = i+1;
          line = reader.readLine();
        }
        else if (line.contains("DATE")) {
            //ceci permet d'avoir la date une seule fois i/e la premiere fois

            String dateLine = methodeDate(line);

            if (first == true){
                writer.write(dateLine + "\tOK \n\n");
                first = false;
            }

          if (recordCommande.size() > 0) {
            writer.write(currentDate + "\tCOMMANDES :\n");
            String element = outputCommande();
            writer.write(element);
            recordCommande.clear();
          }
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
          if (line.equals(";")) {
            writer.write("APPROV OK\n");
          }

        }
        else if (instruction == "PRESCRIPTION") {
          String output = methodPrescription(line,getCurrentDate());

          writer.write(output + "\n");
          line = reader.readLine();
        }
        else if (instruction == "STOCK") {

          writer.write("STOCK " + getCurrentDate() + "\n");
          ArrayList<String> stock = BST.outputStock();
          for (int j=0; j<stock.size(); j++){
            writer.write(stock.get(j) + "\n");
          }
          writer.write("\n");
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

    // cette méthode est utilisé quand la commande dans le fichier est DATE.
  public static String methodeDate(String line){

      String dateLine = line.replace("DATE ","");

      dateLine = dateLine.replace(";", "");

      dateLine = dateLine.replace(" ","");

      setCurrentDate (LocalDate.parse(dateLine));

      // nouvelle date courante = supprimer les medicaments expires
      BST.removeAllExpired(getCurrentDate());

      return dateLine;
  }

// cette méthode est utilisé quand la commande dans le fichier est PRESCRIPTION.
  public  static String methodPrescription(String line, LocalDate date){
    String[] parts = line.split("\\s+");


    for (int i = 0; i < parts.length; i++) {
      parts[i] = parts[i].trim();
    }

    int num1 = Integer.parseInt(parts[1]);
    int num2 = Integer.parseInt(parts[2]);
    String med = parts[0];

    int total = num1*num2;
    BST.outputStock();
    Medicament foundMed = BST.findClosest(med,date, total);


    String outputstring="";

    if (foundMed != null && ((foundMed.getStock()<total))){
      outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
      recordCommande.add((new Record(med, num1*num2)));
    } else if (foundMed !=null && foundMed.getStock()>=total){

      BST.removeMed(foundMed);

      foundMed.setStock(foundMed.getStock()-total);


      BST.addMed(foundMed);
      outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "OK");
    }else if (foundMed ==null){
        recordCommande.add((new Record(med, num1*num2)));
      outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
    }
    return outputstring;
  }

    // va permettre de mettre en sortie toutes les commandes à passer
    public static String outputCommande(){
        String output ="";

        Map<String, Integer> num = new HashMap<>();
        for (Record record : recordCommande) {
            String name = record.name;
            int number = record.number;
            num.put(name, num.getOrDefault(name, 0) + number);
        }

        // Create new record
        List<Record> uniqueRecords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : num.entrySet()) {
            String name = entry.getKey();
            int numb = entry.getValue();
            uniqueRecords.add(new Record(name, numb));
        }

        recordCommande=uniqueRecords;

        Collections.sort(recordCommande, Comparator.comparing(r -> r.name));
        for (Record record : recordCommande) {
            output = output + (record.name() + "\t" + record.number() + "\n");

        }


        output = output + "\n";
        return output;
    }

    // cette méthode est utilisé quand la commande dans le fichier est DATE.
    // on actualise la date courante.
    public static void setCurrentDate(LocalDate currentDate1){
        currentDate=currentDate1;
    }

    public static LocalDate getCurrentDate() {
        return currentDate;
    }

    // cette méthode est utilisé quand la commande dans le fichier est APPROV.
    // un parse est effectué pour avoir un LocalDate
    public static void stringToMed(String string){
        String[] parts = string.split("\\s+");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        int num = Integer.parseInt(parts[1]);

        LocalDate date = LocalDate.parse(parts[2]);

        String med = parts[0];

        Medicament medicament = new Medicament(med,UUID.randomUUID(),date,num);
        Medicament medoc = BST.searchMed(medicament);

        if (medoc != null) {
            medoc.setStock(medoc.getStock()+ num);
            BST.addMed(medoc);
        }
        else{
            BST.addMed(medicament);
        }

    }


    public static void main(String[] args) {
    // Lire le fichier (donnees en fichier en désordre; chercher les keywords)
    // Entrer les donnees dans les structures
    //          creer des objets Medicament, puis les rajouter à Stock. quand ya APPROV.
    //          DATE, sauvegarder dateObtenue dans une variable datecourante, output COMMANDE.
    //          si STOCK, output Stock
    //          PRESCRIPTION, extract le nom du medicament - extract quantity du fichier.
    //          Chercher dans stock en utilisant findClosestExpiryDate. Check si currentDate < Expiry Date
    //          va retourner un objet Medicament; check quantity du Medicament et quantite du Client et
    //            si same OK, quaMed > quaPres ok, else COMMANDE
    //            if OK, remove quantity/ node in binary tree using delete(UUID)
    //            do necessary outputs of OK and COMMANDE.
    //              IF COMMANDE, add to RECORD (if RECORD vide => Date courante et OK)
    //           au prochain DATE, juste avant, output RECORD. Juste apres, deleteAll.
    //           until EOF
      BST.firsttime();
      readTheThing(args[0], args[1]);
  }
}

