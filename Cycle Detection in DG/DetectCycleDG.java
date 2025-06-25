
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
        //this.adjacentList.get(childNode).add(parentNode); // Error Prone -----> The graph is a directed graph.
    }
    
    public boolean hasCycle(int node, boolean[] visited, boolean[] recurrence) {
        visited[node] = true;
        recurrence[node] = true;
        List<Integer> neighbourNodeList = this.adjacentList.get(node);
        for (int neighbourNode : neighbourNodeList) {
            if (!visited[neighbourNode] && hasCycle(neighbourNode, visited, recurrence)) {
                return true;
            } 
            if (recurrence[neighbourNode]) {
                return true;
            }
        }
        recurrence[node] = false; // Error Prone -----> Do not forget to remove recurrence mark.
        return false;
    }
    
    public List<?> detectCycleDG() {
        boolean[] visited = new boolean[this.numberOfNodes];
        boolean[] recurrence = new boolean[this.numberOfNodes];
        for (int node = 0; node < this.numberOfNodes; node++) {
            if (!visited[node] && hasCycle(node, visited, recurrence)) {
                return List.of(true, "Cycle detected. -----> Provided graph is a cyclic graph.");
            }
        }
        return List.of(false, "Cycle not found. -----> Provided graph is an acyclic graph.");
    }
    
    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        this.adjacentList = new HashMap<>();
        for (int node = 0; node < numberOfNodes; node++) {
            this.adjacentList.put(node, new LinkedList<>()); 
        }
    }
    
}

public class DetectCycleDG {
    public static void main(String[] args) {
        System.out.println("\n ---  Cycle Detection In Directed Graph --- \n");
        
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
        
        System.out.println("\nAdjacent List:\n" + graph.getAdjacentList());
        System.out.println("\n" + graph.detectCycleDG() + "\n");
    }
}