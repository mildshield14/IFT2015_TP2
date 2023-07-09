import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BST {
    private static Node root;

    public static Node getRoot() {
        return root;
    }

    public static void add(Medicament med) {
        root = addNode(root, med);
    }

    private static Node addNode(Node node, Medicament med) {
        if (node == null) {
            return new Node(med);
        }

        int compareResult = med.compareTo(node.data);
        if (compareResult < 0) {
            node.left = addNode(node.left, med);
        } else if (compareResult > 0) {
            node.right = addNode(node.right, med);
        }

        return node;
    }

    public static void remove(Medicament med) {
        root = removeNode(root, med);
    }

    private static Node removeNode(Node node, Medicament med) {
        if (node == null) {
            return null;
        }

        int compareResult = med.compareTo(node.data);
        if (compareResult < 0) {
            node.left = removeNode(node.left, med);
        } else if (compareResult > 0) {
            node.right = removeNode(node.right, med);
        } else {
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

    private static Node findSuccessor(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static Medicament findClosest(String nom, LocalDate date) {
        return findClosestNode(root, nom, date);
    }

    private static Medicament findClosestNode(Node node, String nom, LocalDate date) {
        if (node == null) {
            return null;
        }

        Medicament currentMed = node.data;
        if (currentMed.getNom().equals(nom)) {
            return currentMed;
        }

        long diff = Math.abs(date.toEpochDay() - currentMed.getDateExpi().toEpochDay());
        long closestDiff = Math.abs(date.toEpochDay() - currentMed.getDateExpi().toEpochDay());

        Medicament closestMed = null;

        if (diff < 0) {
            Medicament leftClosest = findClosestNode(node.left, nom, date);
            if (leftClosest != null) {
                long leftDiff = Math.abs(date.toEpochDay() - leftClosest.getDateExpi().toEpochDay());
                if (leftDiff < closestDiff) {
                    closestDiff = leftDiff;
                    closestMed = leftClosest;
                }
            }
        } else if (diff > 0) {
            Medicament rightClosest = findClosestNode(node.right, nom, date);
            if (rightClosest != null) {
                long rightDiff = Math.abs(date.toEpochDay() - rightClosest.getDateExpi().toEpochDay());
                if (rightDiff > closestDiff) {
                    closestDiff = rightDiff;
                    closestMed = rightClosest;
                }
            }
        }

        return closestMed;
    }

    public static ArrayList<String> outputStock() {
        ArrayList<String> stock = new ArrayList<>();
        outputStockInOrder(root, stock);
        return stock;
    }

    private static void outputStockInOrder(Node node, ArrayList<String> stock) {
        if (node == null) {
            return;
        }

        outputStockInOrder(node.left, stock);
        stock.add(node.data.getNom() + "\t" + node.data.getStock() + "\t" + node.data.getDateExpi());
        outputStockInOrder(node.right, stock);
    }

    public static void removeAllExpired(LocalDate date) {
        root = removeAllExpiredNodes(root, date);
    }

    public static Medicament search(Medicament med) {
        return searchNode(root, med);
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

    private static Medicament searchNode(Node node, Medicament med) {
        if (node == null) {
            return null;
        }

        int compareResult = med.compareTo(node.data);
        if (compareResult == 0) {
            return node.data;
        } else if (compareResult < 0) {
            return searchNode(node.left, med);
        } else {
            return searchNode(node.right, med);
        }
    }

    private static Node removeAllExpiredNodes(Node node, LocalDate date) {
        if (node == null) {
            return null;
        }

        node.left = removeAllExpiredNodes(node.left, date);
        node.right = removeAllExpiredNodes(node.right, date);

        if (node.data.getDateExpi().isBefore(date)) {
            node = removeNode(node, node.data);
        }

        return node;
    }
    private static class Node {
        private Medicament data;
        private Node left;
        private Node right;

        private Node(Medicament data) {
            this.data = data;
        }
    }
}

