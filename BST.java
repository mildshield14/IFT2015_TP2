import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
  public static void readTheThing() {
    BufferedReader reader;
    boolean first = true;

    try {
      reader = new BufferedReader(new FileReader("src/exemple4.txt"));
      String line = reader.readLine();
      String instruction = "";
      ArrayList<String> commande = new ArrayList<String>();
      int i = 1;
      BufferedWriter writer = new BufferedWriter(new FileWriter("src/exemple1+.txt"));

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

          if (first == true){
            writer.write(dateLine + "\tOK \n");
            first = false;
          }
          dateLine = dateLine.replace("-",",");

          String[] dateeLine = dateLine.split(",");
          int year = Integer.parseInt(dateeLine[0]);
          int month = Integer.parseInt(dateeLine[1]);
          int day = Integer.parseInt(dateeLine[2]);

          setCurrentDate (LocalDate.of(year,month,day));
          BST.removeAllExpired(LocalDate.of(year,month,day));
          if (commande.size() > 0) {
            writer.write( "\n" + currentDate + "\tCOMMANDES :\n");
            String element = BST.outputCommande(commande);
            writer.write(element);
            commande.clear();
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
          if (output.contains("COMMANDE")){
            commande.add(output);
          }
          writer.write(output + "\n");
          line = reader.readLine();
        }
        else if (instruction == "STOCK") {
          writer.write("STOCK " + getCurrentDate() + "\n");
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

 /* public  static String methodCommande(String line,ArrayList<String> commande) {

    String[] parts = line.split("\\s+");

    for (int i = 0; i < parts.length; i++) {
      parts[i] = parts[i].trim();
    }
    String num1 =parts[1];
    String num2 = parts[2];
    String med = parts[0];

    String[] newLine = line.split(num2);
    String newString = newLine[0];
    return newString ;
  }*/

  public  static String methodPrescription(String line, LocalDate date){
// TODO; change to \t
    String[] parts = line.split("\\s+");


    for (int i = 0; i < parts.length; i++) {
      parts[i] = parts[i].trim();
    }

    int num1 = Integer.parseInt(parts[1]);
    int num2 = Integer.parseInt(parts[2]);
    String med = parts[0];

    int total = num1*num2;
    BST.outputStock();
    Medicament foundMed = BST.findClosest(med,date);

    String outputstring="";

    if (foundMed != null && ((foundMed.getStock()<total))){
      outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
    } else if (foundMed !=null && foundMed.getStock()>=total){

      BST.removeMed(foundMed);

      foundMed.setStock(foundMed.getStock()-total);

      BST.addMed(foundMed);
      outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "OK");
    }else if (foundMed ==null){
      System.out.println(med + "\t" + total + " stock needed" +"\t" + " does not exist??");
      outputstring=(med + "\t" + num1 + "\t"+ num2 +"\t"+ "COMMANDE");
    }


    return outputstring;

  }

  public static void main(String[] args) {
    BST.firsttime();
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
