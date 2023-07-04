// used https://www.programiz.com/dsa/binary-search-tree

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
    root = insertKey(root,key)
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

void deleteKey(UUID uuid) {
    root = deleteRec(root, uuid);
}

public Node deleteRec(Node root, UUID uuid) {
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

    public Medicament minValue(Node root){

        Medicament minvalue= root.key;

        while(root.left!=null){
            minv=root.left.key;
            root=root.left;
        }
        return minvalue;

    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        Medicament m1 = new Medicament("Med1", UUID.randomUUID(), LocalDate.of(2023, 7, 1));
        Medicament m2 = new Medicament("Med2", UUID.randomUUID(), LocalDate.of(2023, 7, 2));
        Medicament m3 = new Medicament("Med3", UUID.randomUUID(), LocalDate.of(2023, 7, 3));
        Medicament m4 = new Medicament("Med4", UUID.randomUUID(), LocalDate.of(2023, 7, 4));
        Medicament m5 = new Medicament("Med5", UUID.randomUUID(), LocalDate.of(2023, 7, 5));

        tree.insert(m3);
        tree.insert(m1);
        tree.insert(m4);
        tree.insert(m2);
        tree.insert(m5);

        System.out.println("Inorder traversal (sorted by expiration date):");
        tree.inorder();
    }


}
