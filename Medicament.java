import java.util.UUID;
import java.time.LocalDate;

class Medicament{
    private String nom;
    private UUID uuid;
    private LocalDate dateExpi;

   public Medicament(String nom, UUID uuid, LocalDate dateExpi){
        this.nom=nom;
        this.uuid=uuid;
        this.dateExpi=dateExpi;
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
