
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

class Graph {
	
    private final Map<Integer, List<Integer>> adjacentList;
    private final int numberOfNodes;
	
    public Map<Integer, List<Integer>> getAdjacanetList() {
	    return this.adjacentList;  	
	}
	
	public int getNumberOfNodes() {
		return this.numberOfNodes;
	} 
	
	public void addRelation(int parentNode, int neighbourNode) {
		this.adjacentList.get(parentNode).add(neighbourNode);
		this.adjacentList.get(neighbourNode).add(parentNode);
	}
	
	public boolean hasCycle(int node, int parentNode, boolean[] visited) {
		visited[node] = true;
		List<Integer> neighbourNodeList = this.adjacentList.get(node);
		for (int neighbourNode : neighbourNodeList) {
			if (visited[neighbourNode] && parentNode != neighbourNode) {
				return true;
			}
			if (!visited[neighbourNode] && hasCycle(neighbourNode, node, visited)) {
				return true;
			}
		}
		return false;
	}
	
	public List<?> detectCycleUG() {
		for (int node = 0; node < this.numberOfNodes; node++) {
		    boolean[] visited = new boolean[this.numberOfNodes];
            if (hasCycle(node, -1, visited))	
			return List.of(true, "Cycle detected. -----> Provided graph is a cyclic graph.");				
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

public class DetectCycleUG {
	public static void main(String[] args) {
		
		System.out.println("\n ---  Cycle Detection In Undirected Graph --- \n");	
		
		/*Graph graph = new Graph(5);
			graph.addRelation(0, 1);
			graph.addRelation(1, 2);
			graph.addRelation(1, 3);
			graph.addRelation(1, 4);
		graph.addRelation(2, 4);*/
		
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
		while(true) {
			System.out.print("\nEnter new realtion (CAUTION - Integer numbers only.) [parent_node, neighbour_node]: "); 
			int parentNode = input.nextInt();
			if (parentNode < 0) { break; }
			if (parentNode >= numberOfNodes) {
				System.out.println("Inavlid Input !!! -----> Parent node value should be within the range (0 to " + (numberOfNodes - 1) + ").");
				continue;			
			}
			int neighbourNode = input.nextInt();
			if (neighbourNode < 0) { break; }
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
		
		System.out.println("\nAdjacent List:\n" + graph.getAdjacanetList());
		System.out.println("\n" + graph.detectCycleUG() + "\n");
		
	}
}