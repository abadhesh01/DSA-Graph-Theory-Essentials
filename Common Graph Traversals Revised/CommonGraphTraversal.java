
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

record Graph (Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public Graph (int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
      this.adjacentList.put(node, new ArrayList<>());
  }
  
  public void addEdge (int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    this.adjacentList.get(node2).add(node1);
  }
  
  public List<Integer> traverseBFS (int sourceNode) {   
    List<Integer> result = new ArrayList<>(); // Craeting a result list ...
  
    Queue<Integer> nodeQueue = new LinkedList<>(); // Creating a simple queue for pushing nodes into it ... 
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of node(s) pushed into the queue of nodes.
    
    nodeQueue.add(sourceNode); // Adding unvisited source node to the queue ...
    visited[sourceNode] = true; // Marking source node as visited. 
    
    while (!nodeQueue.isEmpty()) { // Looping untill the queue is empty ...
      int node = nodeQueue.remove(); // Popping a node from the queue ...
      
      result.add(node); // Adding node to the result list ...
      List<Integer> neighbourNodeList = this.adjacentList.get(node); // Getting the neighbour node(s) of the popped node ...
      
      for (int neighbourNode : neighbourNodeList) { // Traversing through the neighbour node(s) ...
        if (visited[neighbourNode]) continue; // If the neighbour node is visited, then moving to the next neighbour node ...
        nodeQueue.add(neighbourNode); // For univisited neighbour node, adding the node to the queue ...
        visited[neighbourNode] = true; // Marking the neighbour node as visited ..
      }
    }
    
    return result; // Returning the result list ...
  }
  
  public void traverseDFS (int node, boolean[] visited, List<Integer> result) /* DFS helper function. */ {
    // NOTE - Only pass unvisited node to this function.
    visited[node] = true; // Marking node as visited ...
    result.add(node); // Adding node to the result list ...
    List<Integer> neighbourNodeList = this.adjacentList.get(node); // Getting the neighbour node(s) of the given node ...
    for (int neighbourNode : neighbourNodeList) { // Traversing through the neighbour node(s) ...
      if (visited[neighbourNode]) continue; // If the neighbour node is visited, then moving to the next neighbour node ...
      this.traverseDFS(neighbourNode, visited, result); // Calling this function recursively and passing the unvisited neighbour node ...
    }
  }
  
  public List<Integer> traverseDFS (int sourceNode) /* DFS calling function. */ { 
    List<Integer> result = new ArrayList<>(); // Craeting a result list ...
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of nodes passed to the DFS helper function.
    this.traverseDFS(sourceNode, visited, result); // Calling the DFS helper function ...
    return result; // Returning the result list ...
  }
  
}


public class CommonGraphTraversal {

  public static void main (String[] args) {
  
    System.out.println("\n--- Graph Traversal Algorithms ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");
    
    Scanner keyboardInput = new Scanner(System.in);
    
    System.out.print("\nEnter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;
    
    System.out.println("\nAdd edge(s) to the graph in the order \"node1 node2\" ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {  
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }
    
    System.out.print("\nEnter the source node of your graph: ");
    int sourceNode = keyboardInput.nextInt();
    if (sourceNode < 0) return;
    
    keyboardInput.close();
    
    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...
    
    for (List<Integer> edge : edgeList) // Adding edge(s) to the graph ...
      graph.addEdge(edge.get(0), edge.get(1));
    
    // Computing and printing BFS (Bredth First Search) traversal of graph ...
    System.out.println("\nBFS (Bredth First Search) traversal of the graph: " + graph.traverseBFS(sourceNode)); 
    
    // Computing and printing DFS (Depth First Search) traversal of graph ...
    System.out.println("\nDFS (Depth First Search) traversal of the graph: " + graph.traverseDFS(sourceNode)); 
    
    System.out.println("\n");
    
  }
  
}
 