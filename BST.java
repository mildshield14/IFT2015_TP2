import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class BST {
    public static BST tree = new BST();
    private static Node root;

    public static Node getRoot() {
        return root;
    }

    public static void firsttime (){
        BST tree= new BST();
    }

    public static void setTree(BST tree) {
        BST.tree = tree;
    }

    class Node {
        public Comparable data;
        public Node left;
        public Node right;


        /**
         Inserts a new node as a descendant of this node.
         @param newNode the node to insert
         */
        public void addNode(Node newNode)
        {
            int comp = newNode.data.compareTo(data);
            if (comp < 0)
            {if (left == null) left = newNode;
            else left.addNode(newNode);
            }
            else if (comp > 0)
            {
                if (right == null) right = newNode;
                else right.addNode(newNode);
            }
        }

        /**
         Prints this node and all of its descendants
         in sorted order.
         */
       // public ArrayList<String> medsStock =new ArrayList<>();
      /*  public void outputStock1()
        {
            if (left != null)
                left.outputStock();
            Medicament m= (Medicament) data;
            medsStock.add(m.getNom() + "\t" + m.getStock() + "\t" + m.getDateExpi());
            if (right != null)
                right.outputStock();
        }

        public ArrayList<String> outputStock() {
            outputStock1();
         return  medsStock;
        }*/

        public void traverseInOrder(ArrayList<Medicament> meds) {
            if (left != null)
                left.traverseInOrder(meds);
            meds.add((Medicament) data);
            if (right != null)
                right.traverseInOrder(meds);
        }
    }
    public void add(Comparable obj)
    {
        Node newNode = new Node();
        newNode.data = obj;
        newNode.left = null;
        newNode.right = null;
        if (root == null) root = newNode;
        else root.addNode(newNode);
    }
    /**
     Tries to find an object in the tree.
     @param obj the object to find
     @return true if the object is contained in the tree
     */
    public boolean find(Comparable obj)
    {
        Node current = root;
        while (current != null)
        {
            int d = current.data.compareTo(obj);
            if (d == 0) return true;
            else if (d > 0) current = current.left;
            else current = current.right;
        }
        return false;
    }

    /**
     Tries to remove an object from the tree. Does nothing
     if the object is not contained in the tree.
     @param obj the object to remove
     */
    private static Node removeNode(Node node, Comparable obj) {
        if (node == null) {
            return null;
        }

        int comp = obj.compareTo(node.data);
        if (comp < 0) {
            node.left = removeNode(node.left, obj);
        } else if (comp > 0) {
            node.right = removeNode(node.right, obj);
        } else {
            // Node to be removed found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node successor = findSuccessor(node.right);
                node.data = successor.data;
                node.right = removeNode(node.right, successor.data);
            }
        }

        return node;
    }


    /**
     A node of a tree stores a data item and references
     of the child nodes to the left and to the right.
     */

    //protected static  TreeSet<Medicament> tree;


    /**
     Constructs an empty tree.
     */
    public BST()   {
        root = null;
    };
    /*public static TreeSet<Medicament> getTree() {
        return tree;
    }

    public static void setTree(TreeSet<Medicament> tree) {
        BST.tree = tree;
    }*/
public static Medicament findClosest(String nom, LocalDate date){
    return findClosestNode(root, nom, date);
}

    public static Medicament findClosestNode(Node node, String nom, LocalDate date) {

        if (node == null) {
            return null;
        }
        Medicament currentMedi = (Medicament) node.data;

        long diff = ChronoUnit.DAYS.between(currentMedi.getDateExpi(), date);
        long closestDiff = Math.abs(diff);

        Medicament closestMedi = null;

        if (nom.equals(currentMedi.getNom())) {
            closestMedi = currentMedi;
        }
        if (diff < 0) {
            Medicament left = findClosestNode(node.left, nom, date);
            if (left != null) {

                long leftDiff = Math.abs(ChronoUnit.DAYS.between(left.getDateExpi(), date));
                if (leftDiff < closestDiff) {
                    closestDiff = leftDiff;
                    closestMedi = left;
                }
            }
        } else if (diff > 0) {
            Medicament right = findClosestNode(node.right, nom, date);
            if (right != null) {
                //TODO
                long rightDiff = Math.abs(ChronoUnit.DAYS.between(right.getDateExpi(), date));
                if (rightDiff < closestDiff) {
                    closestDiff = rightDiff;
                    closestMedi = right;
                }
            }

        }
        return closestMedi;
    }

    public static ArrayList<Medicament> traverseInOrder() {
        ArrayList<Medicament> meds = new ArrayList<>();
        if (root != null) {
            root.traverseInOrder(meds);
            return meds;
        }
        return null;
    }

    public static ArrayList<String> outputStock(){

        ArrayList<String> medsStock =new ArrayList<>();
        //ArrayList <Medicament> meds = traverseInOrder();
Stack<Node> stack = new Stack<>();
Node current= root;

while (current!=null || !stack.isEmpty()){
    while (current !=null){
        stack.push(current);
        current=current.left;

    }
    current=stack.pop();
    Medicament med = (Medicament) current.data;
    medsStock.add(med.getNom() + "\t" + med.getStock() + "\t" + med.getDateExpi());
    current =current.right;
}
return medsStock;
//        for (Medicament med:meds) {
//            medsStock.add(med.getNom() + "\t" + med.getStock() + "\t" + med.getDateExpi());
//        }

    }


    public static String outputCommande(ArrayList<String> commande){

        ArrayList<String> medsStock =new ArrayList<String>();
        Map<String, Integer> stock = new HashMap<>();

        for (int n=0; n<commande.size(); n++) {
            String line = commande.get(n);
            String[] parts = line.split("\\s+");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }

            int num1 = Integer.parseInt(parts[1]);
            int num2 = Integer.parseInt(parts[2]);
            String med = parts[0];

            int total = num1 * num2;
            String output = med + "\t" + total;
            //medsStock.add(output);

            if (stock.containsKey(med)) {
                int stock2 = stock.get(med);
                stock.put(med, stock2+total);
            }
            else {
                stock.put(med, total);
            }
        }
        String output = "";
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            output = output + entry.getKey() + " " + entry.getValue() + "\n";
        }
        output = output + "\n";
        return output;
    }

    public static void removeMed(Medicament med){

        BST tree = getTree();
        tree.remove(med);
        setTree(tree);
    }
    public void remove(Comparable obj) {
        root = removeNode(root, obj);
    }
    public static void addMed(Medicament med){

        BST tree = getTree();
        tree.add(med);
        setTree(tree);
        String s ="";
    }

    public static BST getTree() {
        return tree;
    }

    public static Node removeExpiredNodes(Node node, LocalDate date) {
        if (node == null) {
            return null;
        }

        node.left = removeExpiredNodes (node.left, date);
        node.right= removeExpiredNodes(node.left,date);

        if (((Medicament)node.data ).getDateExpi().isBefore(date)) {
            return removeNode(node);
        }
        return node;

    }

    private static Node removeNode(Node node) {
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
Node succ = findSuccessor(node.right);
node.data=succ.data;
node.right=removeNode(succ);
return node;
        }
    }

    private static Node findSuccessor(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

//    public static void removeAllExpired(LocalDate date){
//        BST tree = getTree();
//        tree.removeIf(mmm -> mmm.getDateExpi().isBefore(date));
//        BST.setTree(tree);
//    }

    public static Medicament searchTraverse(Node node, Medicament med) {
        if (node != null) {
            Medicament m = (Medicament) node.data;
            int comp = m.getNom().compareTo(med.getNom());
            if (comp == 0 && (m.getDateExpi().equals(med.getDateExpi()))) {
                return med;
            } else if (comp > 0) {
                return searchTraverse(node.left, med);
            } else {
                return searchTraverse(node.right, med);
            }
        }
        return null;

    }

//    public static Medicament searchMed(Medicament med) {
//        BST tree = getTree();
//        for (int i=0;i<;i++) {
//            if (med.getNom().equals(medicament.getNom()) && med.getDateExpi().equals(medicament.getDateExpi())){
//                return medicament;
//            }
//        }
//        return null;
//    }

    public static void main(String[] args) {

      firsttime();

       Medicament m1 = new Medicament("Med1", UUID.randomUUID(), LocalDate.of(2023, 7, 1),100);
      //  tree.contains(m1.getNom());
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

       // System.out.println("Inorder traversal (sorted by expiration date):");
        //tree.inorder();
        ArrayList<String> medd = outputStock();

        for (String mm: medd){
            System.out.println(mm);
        }


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


        /*System.out.println("Inorder traversal (sorted by expiration date):");
        //tree.inorder();
        /*Medicament newm1 = new Medicament (m1.getNom(), m1.getUUID(),LocalDate.of(2023, 7, 15), m1.getStock()+1000);
        tree.add(newm1);*/
        //removeMed(m1);
        //removeBeforeDate(tree, LocalDate.of(2023, 7, 4 ));
        /*removeAllExpired(LocalDate.of(2023, 7, 4 ));
        for (Medicament med:tree) {
            System.out.println(med.getNom() + " " + med.getStock() + " " + med.getDateExpi());
        }

    }
}


import org.w3c.dom.Node;

/**
 This class implements a binary search tree whose
 nodes hold objects that implement the Comparable
  interface.
  */



         /**
  Inserts a new node into the tree.
  @param obj the object to insert
  */


}}
