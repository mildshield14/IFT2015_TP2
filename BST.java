// used https://www.programiz.com/dsa/binary-search-tree

import java.time.LocalDate;
import java.util.UUID;

class BST { 
    //node class that defines BST node
    class Node { 
        
            Medicament key;
            Node left, right;
    
            public Node(Medicament key) {
                this.key = key;
                left = right = null;
            }
        }
     
    // BST root node 
    public static Node root; 
  
   // Constructor for BST =>initial empty tree
    public BST(){ 
        root = null; 
    } 

public void insert(Medicament key){
    root = insertKey(root,key);
}

public Node insertKey(Node root, Medicament key) {
    if (root == null) {
        root = new Node(key);
        return root;
    }

    if (key.getDateExpi().compareTo(root.key.getDateExpi()) < 0)
        root.left = insertKey(root.left, key);
    else if (key.getDateExpi().compareTo(root.key.getDateExpi()) >= 0)
        root.right = insertKey(root.right, key);

    return root;
}

void inorder() {
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key.getNom() + " " + root.key.getDateExpi() + " " + root.key.getStock());
            inorderRec(root.right);
        }
    }


void deleteKey(UUID uuid) {
    root = deleteRec(root, uuid);
}

    Node deleteRec(Node root, UUID uuid) {
        if (root == null)
            return root;

        if (uuid.compareTo(root.key.getUUID()) < 0)
            root.left = deleteRec(root.left, uuid);
        else if (uuid.compareTo(root.key.getUUID()) > 0)
            root.right = deleteRec(root.right, uuid);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);

            root.right = deleteRec(root.right, root.key.getUUID());
        }

        return root;
    }

    public Medicament minValue(Node root){

        Medicament minvalue= root.key;

        while(root.left!=null){
            minvalue=root.left.key;
            root=root.left;
        }
        return minvalue;

    }

    static Medicament findClosestExpiryDate(String name, LocalDate currentDate) {

        Node closestNode = findClosestExpiryDateRec(root, name, currentDate, null);
        if (closestNode != null) {
            return closestNode.key;
        } else {
            return null;
        }
    }

    static Node findClosestExpiryDateRec(Node root, String name, LocalDate targetDate, Node closestNode) {
        if (root == null) {
            return closestNode;
        }

        if (root.key.getNom().equals(name)) {
            if (closestNode == null || root.key.getDateExpi().compareTo(closestNode.key.getDateExpi()) < 0) {
                closestNode = root;
            }
        }

        if (root.key.getNom().compareTo(name) < 0) {
            return findClosestExpiryDateRec(root.right, name, targetDate, closestNode);
        } else {
            return findClosestExpiryDateRec(root.left, name, targetDate, closestNode);
        }

    }
     public static void deleteNodesBeforeDate(LocalDate targetDate) {
            root = deleteNodesBeforeDateRec(root, targetDate);
        }
    
        public static Node deleteNodesBeforeDateRec(Node root, LocalDate targetDate) {
            if (root == null) {
                return null;
            }
    
            if (root.key.getDateExpi().compareTo(targetDate) < 0) {
                root = deleteNormalNode(root, root.key);
            }
            if (root!= null){
            root.left = deleteNodesBeforeDateRec(root.left, targetDate);
            root.right = deleteNodesBeforeDateRec(root.right, targetDate);
            }
    
            return root;
        }

        static Node deleteNormalNode(Node root, Medicament medicament) {
        if (root == null) {
            return null;
        }

        if (medicament.getDateExpi().compareTo(root.key.getDateExpi()) < 0) {
            root.left = deleteNormalNode(root.left, medicament);
        } else if (medicament.getDateExpi().compareTo(root.key.getDateExpi()) > 0) {
            root.right = deleteNormalNode(root.right, medicament);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            Node minValueNode = findMinValueNode(root.right);
            root.key = minValueNode.key;
            root.right = deleteNormalNode(root.right, minValueNode.key);
        }
        return root;
    }

 static Node findMinValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public static void main(String[] args) {
        BST tree = new BST();

        Medicament m1 = new Medicament("Med1", UUID.randomUUID(), LocalDate.of(2023, 7, 1),100);
        Medicament m2 = new Medicament("Med2", UUID.randomUUID(), LocalDate.of(2023, 7, 2),100);
        Medicament m3 = new Medicament("Med3", UUID.randomUUID(), LocalDate.of(2023, 7, 3),100);
        Medicament m4 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 4),200);
        Medicament m5 = new Medicament("Med5", UUID.randomUUID(), LocalDate.of(2023, 7, 5),100);
        Medicament m6 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 5),350);
        Medicament m7 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 10),450);
     
        tree.insert(m5);
        tree.insert(m3);
        tree.insert(m4);
        tree.insert(m1);
        
        tree.insert(m2);
        
        tree.insert(m6);
        tree.insert(m7);

        System.out.println("Inorder traversal (sorted by expiration date):");
        tree.inorder();

        Medicament m = findClosestExpiryDate("Med4", LocalDate.of(2023, 7, 3));
        System.out.println(m.getNom() + " " + m.getStock() + " " + m.getDateExpi());

        Medicament newm = new Medicament (m.getNom(), m.getUUID(),m.getDateExpi(), m.getStock()-50);
        tree.deleteKey(m.getUUID());
        System.gc();
        tree.insert(newm);
        System.gc();
        Medicament m11 = new Medicament("",UUID.randomUUID(),LocalDate.of(2023, 7, 1),100);
        m11 = findClosestExpiryDate("Med4", LocalDate.of(2023, 7, 3));
        System.out.println(m11.getNom() + " " + m11.getStock() + " " + m11.getDateExpi());
        tree.inorder();
        deleteNodesBeforeDate(LocalDate.of(2023, 7, 5));

        
        System.out.println("Inorder traversal (sorted by expiration date):");
        tree.inorder();
        
    }


}
