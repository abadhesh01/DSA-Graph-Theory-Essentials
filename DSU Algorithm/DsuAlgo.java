
import java.util.Map;
import java.util.HashMap;
import java.util.List; 
import java.util.LinkedList;

class Graph {
    
    private final Map<Integer, List<Integer>> adjacentList;
    private final int numberOfNodes;
    
    public Map<Integer, List<Integer>> getAdjacentList() {
        return this.adjacentList;
    }
    
    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }
    
    public void addRelation(int parentNode, int childNode) {
        this.adjacentList.get(parentNode).add(childNode);
        this.adjacentList.get(childNode).add(parentNode); 
    } 
    
    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        this.adjacentList = new HashMap<>();
        for (int node = 0; node < numberOfNodes; node++) {
            this.adjacentList.put(node, new LinkedList<>());
        }
    }   
    
}

class DSU {
    
    private final int[] parent; // Keeps track of parent nodes of given node.
    private final int[] rank; // Keeps track of rank of the given node.
    
    public int[] getParent() {
        return this.parent;   
    }
    
    public int[] getRank() {
        return this.rank;
    }
    
    public int findUltimateParent(int node) {
        if (this.parent[node] == node) {
            return node;
        }
        return findUltimateParent(this.parent[node]);    
    }
    
    public void makeUnion(int nodeA, int nodeB) {
        
        int rootX = findUltimateParent(nodeA);
        int rootY = findUltimateParent(nodeB);
        
        if (rootX != rootY) {
            // Make parents .....
            // Note - Parent nodes have higher rank than child nodes.
            if (rank[rootX] > rank[rootY]) {
                this.parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                this.parent[rootX] = rootY;
                } else {
                // If both nodes have same rank, make one node the parent of another node
                // and also increase it's rank by 1.
                this.parent[rootY] = rootX;
                this.rank[rootX]++;
            }
        }
        
    }
    
    public DSU (int numberOfNodes) {
        this.parent = new int[numberOfNodes];
        this.rank = new int[numberOfNodes];
        // Note - By default every node is the parent of itself.
        for (int node = 0; node < numberOfNodes; node++) {
            this.parent[node] = node;  
        }
    }
    
}

public class DsuAlgo {
    public static void main(String[] args) {
        System.out.println("\n--- Disjoint Set Union Algorithm ---\n");
        
        /* --- User Input --- */
        java.util.Scanner input = new java.util.Scanner(System.in);
        
        System.out.print("\nEnter the number of nodes (CAUTION - Integer numbers only.): ");
        int numberOfNodes = input.nextInt();
        if (numberOfNodes < 1) {
            System.out.println("Inavlid Input !!! -----> Number of nodes cannot be less than 1.");
            System.exit(0);
        }
        
        System.out.println("\nEnter all the realtions (NOTE - Enter any negative number at any point of time to close input.) ::");
        List<List<Integer>> relations = new LinkedList<>();
        while (true) {
            System.out.print("\nEnter new realtion (CAUTION - Integer numbers only.) [parent_node, neighbour_node]: ");
            int parentNode = input.nextInt();
            if (parentNode < 0) {
                break;
            }
            if (parentNode >= numberOfNodes) {
                System.out.println("Inavlid Input !!! -----> Parent node value should be within the range (0 to " + (numberOfNodes - 1) + ").");
                continue;
            }
            int neighbourNode = input.nextInt();
            if (neighbourNode < 0) {
                break;
            }
            if (neighbourNode >= numberOfNodes) {
                System.out.println("Inavlid Input !!! -----> Neighbour node value should be within the range (0 to " + (numberOfNodes - 1) + ").");
                continue;
            }
            relations.add(List.of(parentNode, neighbourNode));
        }
        
        if (relations.isEmpty()) {
            System.out.println("\nUnable to detect cycle !!! -----> No relations exist!\n");
            System.exit(0);
        }
        
        input.close();
        /* --- User Input --- */
        
        Graph graph = new Graph(numberOfNodes);
        for (List<Integer> relation : relations) {
            graph.addRelation(relation.get(0), relation.get(1));
        }
        
        DSU dsu = new DSU(numberOfNodes);
        for (int parentNode = 0; parentNode < numberOfNodes; parentNode++) {
            List<Integer> childNodes  = graph.getAdjacentList().get(parentNode);
            for (int childNode : childNodes) {
                dsu.makeUnion(parentNode, childNode);
            }
        }
        
        System.out.println("\nOutput::");
        int[] parent = dsu.getParent();
        for (int node = 0; node < numberOfNodes; node++) {
            System.out.println("Ultimate parent of Node-" + node + " -----> Node-" + parent[node]);
        }
        System.out.println("\n");
        
    }
}