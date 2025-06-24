
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

class NodeWeightPair {
	
	private final int node;
	private final int weight; // Represents distance between two nodes.
	
	public int getNode() {
		return this.node;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String toString() {
		return "{ node = " + this.node + ", weight = " + this.weight + " }";
	}
	
	public NodeWeightPair(int node, int weight) {
		this.node = node;
		this.weight = weight;
	} 
	
}

class Graph {
	
    private final Map<Integer, List<NodeWeightPair>> adjacentList;
	private final int numberOfNodes;
	
	public Map<Integer, List<NodeWeightPair>> getAdjacentList() {
	    return this.adjacentList;
	}	
	
	public int getNumberOfNodes() {
		return this.numberOfNodes;
	}
	
	public void addEdge(int node1, int node2, int weight) {
        this.adjacentList.get(node1).add(new NodeWeightPair(node2, weight));
		this.adjacentList.get(node2).add(new NodeWeightPair(node1, weight));
	}
	
	public Map<Integer, Integer> processDijKstraShortestPath(int sourceNode) {
		
		int[] distance = new int[this.numberOfNodes];
		PriorityQueue<NodeWeightPair> queue = new PriorityQueue<>((nwpA, nwpB) -> nwpA.getWeight() - nwpB.getWeight());
		
		for (int index = 0; index < numberOfNodes; index++) {
		    distance[index] = Integer.MAX_VALUE;
		}	
		distance[sourceNode] = 0;
		queue.add(new NodeWeightPair(sourceNode, 0));
		
		while (!queue.isEmpty()) {
	        
			NodeWeightPair poppedPair = queue.remove();
			
			int currentNode = poppedPair.getNode();
            int distanceFromSourceCalculatedSoFar = poppedPair.getWeight();
			
			if (distanceFromSourceCalculatedSoFar > distance[currentNode]) {
				continue;
			}
			
			List<NodeWeightPair> neighbourNodeWeightPairList = this.adjacentList.get(currentNode); 
            
			for (NodeWeightPair neighbourNodeWeightPair : neighbourNodeWeightPairList) {
				
				int neighbourNode = neighbourNodeWeightPair.getNode();
				int edgeWeight = neighbourNodeWeightPair.getWeight();
				
				if (distance[neighbourNode] > (distance[currentNode] + edgeWeight)) {
				    distance[neighbourNode] = distance[currentNode] + edgeWeight;
					queue.add(new NodeWeightPair(neighbourNode, distance[neighbourNode]));
				}
			}
			
		}
		
		// Processing result ...
		Map<Integer, Integer> shortestDistanceFromSourceNode = new LinkedHashMap<>();
		for (int index = 0; index < this.numberOfNodes; index++) {
			shortestDistanceFromSourceNode.put(index, distance[index]);
		}
		
		return shortestDistanceFromSourceNode;
	}
	
    public Graph(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
		this.adjacentList = new HashMap<>();	
		for (int node = 0; node < numberOfNodes; node++)
		adjacentList.put(node, new ArrayList<>());		  
	}    	
	
}

public class DijKstraAlgo {
	
	public static void main(String[] args) {
		System.out.println("\n --- DijKstra's Algorithm --- \n");
		
		/*Graph graph = new Graph(8);
			graph.addEdge(0, 1, 8);
			graph.addEdge(0, 5, 99);
			graph.addEdge(1, 2, 32);
			graph.addEdge(1, 6, 27);
			graph.addEdge(1, 7, 11);
			graph.addEdge(2, 3, 37);
			graph.addEdge(2, 4, 45);
		int sourceNode = 0;*/
		
		/* ----- User Input Code ----- */
		java.util.Scanner input = new java.util.Scanner(System.in);
		
		System.out.print("Enter the number of nodes in your graph (CAUTION - Integer numbers only.): ");		
		int numberOfNodes = input.nextInt();
		if (numberOfNodes < 1) {
			System.out.println("Invalid Input !!! ---> Number of nodes cannot be less than 1.\n");
			System.exit(0);
		}
		
		System.out.print("\nEnter the starting node (CAUTION - Integer numbers only.): ");		
		int sourceNode = input.nextInt();  
		if (sourceNode < 0 || sourceNode >= numberOfNodes) {
			System.out.println("Invalid Input !!! ---> Starting node should be within the range (0 to " + (numberOfNodes - 1) + " inclusive). \n");
			System.exit(0);
		}
		
		System.out.print("\nEnter the number of connections in your graph (CAUTION - Integer numbers only.): ");		
		int numberOfConnections = input.nextInt();
		if (numberOfConnections < 0) {
			System.out.println("Invalid Input !!! ---> Number of connections cannot be less than 0.\n");
			System.exit(0);
		}
		
		List<List<Integer>> connections = new ArrayList<>(); 
		System.out.println("\n\nEnter the all the connections one by one (CAUTION -Integer numbers only.)::");
		for (int count = 1; count <= numberOfConnections;) {
			System.out.print("Enter a new connection(" + count + ") [node1, node2, weight(/edge)]: ");
			int node1 = input.nextInt(); 
			if (node1 < 0 || node1 >= numberOfNodes) {
				System.out.println("Invalid Input !!! ---> Node-1 value should be within the range(0 to " + (numberOfNodes - 1) + ").\n");
				continue;
			}
			int node2 = input.nextInt(); 
			if (node2 < 0 || node2 >= numberOfNodes) {
				System.out.println("Invalid Input !!! ---> Node-2 value should be within the range(0 to " + (numberOfNodes - 1) + ").\n");
				continue;
			}
			if (node1 == node2) {
				System.out.println("Invalid Input !!! ---> Node-1 and Node-2 cannot be the same.\n");
				continue;
			}
			int edge = input.nextInt(); 
			if (edge <= 0) {
				System.out.println("Invalid Input !!! ---> Edge value cannot be zero(0) or negative.\n");
				continue;
			}
			connections.add(List.of(node1, node2, edge));
			count++;
		}
		
		input.close();
		/* ----- User Input Code ----- */
		
		Graph graph = new Graph(numberOfNodes);
		for (List<Integer> connection : connections) {
			graph.addEdge(connection.get(0), connection.get(1), connection.get(2));
		}
		
		System.out.println("\nAdjacent List:");
		for (int node = 0; node < numberOfNodes; node++) {
			System.out.print("Node = " + node + " [ " +graph.getAdjacentList().get(node) + " ] \n");
		}	
		
		System.out.println("\nShortest distance of each node from source node '" + sourceNode + "': " + graph.processDijKstraShortestPath(sourceNode) + "\n");
		
	} 
	
}