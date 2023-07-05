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
    public Node root; 
  
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
    else if (key.getDateExpi().compareTo(root.key.getDateExpi()) > 0)
        root.right = insertKey(root.right, key);

    return root;
}

void inorder() {
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key.getNom());
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



}
