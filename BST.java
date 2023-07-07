// used https://www.programiz.com/dsa/binary-search-tree

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class BST {

   public static  TreeSet<Medicament> tree = new TreeSet<Medicament>();

   public static TreeSet<Medicament> getTree() {
       return tree;
   }

    public static Medicament findClosest(String nom, LocalDate date) {
        Medicament m = null;
        long closestDiff = Long.MAX_VALUE;
        for (Medicament M : tree) {
            long diff = ChronoUnit.DAYS.between(M.getDateExpi(), date);
            if (Math.abs(diff)<closestDiff && M.getNom().equals(nom)) {
                closestDiff = Math.abs(diff);
                m = M;

            }
        } return m;
    }


    public static void outputStock(LocalDate date){
        for (Medicament med:tree) {
            if(med.getStock()>0 && med.getDateExpi().isAfter(date)){
            System.out.println(med.getNom() + " " + med.getStock() + " " + med.getDateExpi());
        }}
    }

    public static void removeMed(Medicament med){
    tree.remove(med);
    }

    public static void addMed(Medicament med){
        tree.add(med);
        }

    public static void removeAllExpired(LocalDate date){
        tree.removeIf(mmm -> mmm.getDateExpi().isBefore(date));
    }


    public static void main(String[] args) {
        //BST tree = new BST();
       
        Medicament m1 = new Medicament("Med1", UUID.randomUUID(), LocalDate.of(2023, 7, 1),100);
        Medicament m2 = new Medicament("Med2", UUID.randomUUID(), LocalDate.of(2023, 7, 2),100);
        Medicament m3 = new Medicament("Med3", UUID.randomUUID(), LocalDate.of(2023, 7, 3),100);
        Medicament m4 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 4),200);
        Medicament m5 = new Medicament("Med5", UUID.randomUUID(), LocalDate.of(2023, 7, 5),100);
        Medicament m6 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 5),350);
        Medicament m7 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 10),450);
        //ArrayList<Medicament> ee = new ArrayList<>();
        //ee = new Collection<Medicament>();
        addMed(m5);
        addMed(m3);
        addMed(m4);
        addMed(m1);
        addMed(m2);
        addMed(m6);
        addMed(m7);
        //tree.addAll(ee);

        System.out.println("Inorder traversal (sorted by expiration date):");
        //tree.inorder();
       outputStock(LocalDate.of(2023,12,12));

        Medicament m = findClosest("Med4", LocalDate.of(2023, 7, 3));
        System.out.println("Output: " + m.getNom() + " " + m.getStock() + " " + m.getDateExpi());

       Medicament newm = new Medicament (m.getNom(), m.getUUID(),m.getDateExpi(), m.getStock()-50);
       //Medicament newm1 = new Medicament (m1.getNom(), m1.getUUID(),m1.getDateExpi(), m1.getStock()-50);
       removeMed(m);
       //tree.remove(m1);
        //System.gc();
        addMed(newm);
        //tree.add(newm1);
        //System.gc();
        Medicament m11 = new Medicament("",UUID.randomUUID(),LocalDate.of(2023, 7, 1),100);
        m11 = findClosest("Med4",LocalDate.of(2023, 7, 3));
        System.out.println("Output: " + m11.getNom() + " " + m11.getStock() + " " + m11.getDateExpi());
        //int size = tree.size();
        //tree.inorder();
        /*for (int i=0; i<size*size; i++) {
            deleteNodesBeforeDate(LocalDate.of(2023, 7, 3));
        }*/
        /*deleteNodesBeforeDate(LocalDate.of(2023, 7, 5));
        deleteNodesBeforeDate(LocalDate.of(2023, 7, 5));*/


        System.out.println("Inorder traversal (sorted by expiration date):");
        //tree.inorder();
        /*Medicament newm1 = new Medicament (m1.getNom(), m1.getUUID(),LocalDate.of(2023, 7, 15), m1.getStock()+1000);
        tree.add(newm1);*/
        removeMed(m1);
        //removeBeforeDate(tree, LocalDate.of(2023, 7, 4 ));
       removeAllExpired(LocalDate.of(2023, 7, 4 ));
        for (Medicament med:tree) {
            System.out.println(med.getNom() + " " + med.getStock() + " " + med.getDateExpi());
        }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    }
}

