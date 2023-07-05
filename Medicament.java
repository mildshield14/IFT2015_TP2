import java.util.UUID;
import java.time.LocalDate;

class Medicament{
    private String nom;
    private UUID uuid;
    private LocalDate dateExpi;
    private int stock;

   public Medicament(String nom, UUID uuid, LocalDate dateExpi, int stock){
        this.nom=nom;
        this.uuid=uuid;
        this.dateExpi=dateExpi;
        this.stock=stock;
   }

   public int getStock() {
       return stock;
   }

   public LocalDate getDateExpi() {
       return dateExpi;
   }

   public String getNom(){
    return nom;
   }


   public UUID getUUID(){
    return uuid;
   }

 

}
