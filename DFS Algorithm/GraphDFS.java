
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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


  public void dfsTraversalHelper (int node, boolean[] visited, List<Integer> result) {
    visited[node] = true;
    result.add(node);
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if (visited[neighbourNode]) continue;
      this.dfsTraversalHelper(neighbourNode, visited, result);
    }    
  }

  public List<Integer> traverseDFS (int sourceNode) {
    boolean[] visited = new boolean[this.numberOfNodes]; // Keeps track of nodes visited as current / parent node.  
    List<Integer> result = new ArrayList<>();
    this.dfsTraversalHelper(sourceNode, visited, result);
    return result;
  }

}

public class GraphDFS {

  public static void main (String[] args) {

    System.out.println("\n--- Depth First Search For Graph ---\n");
    System.out.println("[ NOTE - Entering any negative number at any point of time will close the input.]\n");

    Scanner keyboardInput = new Scanner(System.in);

    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;
  
    System.out.println("Add new edges to the graph::");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      System.out.print("Enter a new edge [ node_1 node_2 ]: ");
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }
    
    keyboardInput.close();

    // Creating a new graph ...
    Graph graph = new Graph(numberOfNodes);

    // Adding edges to the graph ...
    for (List<Integer> edge : edgeList) 
      graph.addEdge(edge.get(0), edge.get(1));

    // Printing the Adjacent List ...
    System.out.println("\nAdjacent List ->");
    System.out.println("---- \t \t ---------------------"); 
    System.out.println("Node \t \t Adjacent List Node(s)");
    System.out.println("---- \t \t ---------------------"); 
    for (int node = 0; node < numberOfNodes; node++)
      System.out.println(" " + node + " \t \t " + " " + graph.adjacentList().get(node));

    // Computing and Printing DFS traversal of graph ...
    System.out.println("\nDFS Traversal of the provided graph -> ");
    System.out.println("----------- \t \t -------------");
    System.out.println("Source Node \t \t DFS Traversal");  
    System.out.println("----------- \t \t -------------"); 
    for (int node = 0; node < numberOfNodes; node++)
      System.out.println("  " + node + " \t \t\t " + graph.traverseDFS(node));
    System.out.println("\n");

  }

}
