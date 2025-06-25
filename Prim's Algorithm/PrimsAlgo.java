
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

record ResultGraphConnection(int node1, int node2, int edge) {
    public String toString() {
        return "[ node1: " + this.node1 + ", node2: " + this.node2 + ", edge: " + this.edge + " ]";
    }
}

class NodeWeightPair {

    private final int node;
    private final int weight;

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

    public List<ResultGraphConnection> processPrimMinimumSpanningTree() {

        boolean[] visited = new boolean[this.numberOfNodes]; // Mark only for parent nodes.
        int[] weight = new int[this.numberOfNodes];
        int[] parent = new int[this.numberOfNodes];

        java.util.Arrays.fill(weight, Integer.MAX_VALUE);
        java.util.Arrays.fill(parent, -1);

        PriorityQueue<NodeWeightPair> queue = new PriorityQueue<>((nwA, nwB) -> nwA.getWeight() - nwB.getWeight());

        int sourceNode = 0; // Assume the source node to be zero(0).
        weight[sourceNode] = 0;
        queue.add(new NodeWeightPair(sourceNode, 0));

        while (!queue.isEmpty()) {

            NodeWeightPair poppedNodeWeightPair = queue.remove();

            int currentNode = poppedNodeWeightPair.getNode(); // Parent Node.
            int currentNodeWeight = poppedNodeWeightPair.getWeight(); // No use or not required.
            visited[currentNode] = true;

            List<NodeWeightPair> neighbourNodeWeightPairList = this.adjacentList.get(currentNode);

            for (NodeWeightPair neighbourNodeWeightPair : neighbourNodeWeightPairList) {
                int neighbourNode = neighbourNodeWeightPair.getNode();
                int neighbourNodeWeight = neighbourNodeWeightPair.getWeight();
                if (!visited[neighbourNode] && weight[neighbourNode] > neighbourNodeWeight) {
                    weight[neighbourNode] = neighbourNodeWeight;
                    parent[neighbourNode] = currentNode;
                    queue.add(new NodeWeightPair(neighbourNode, weight[neighbourNode]));
                }
            }

        }

        // Processing result ...
        List<ResultGraphConnection> result = new LinkedList<>();
        for (int node = 0; node < sourceNode; node++) {
            result.add(new ResultGraphConnection(parent[node], node, weight[node]));
        }
        for (int node = sourceNode + 1; node < this.numberOfNodes; node++) {
            result.add(new ResultGraphConnection(parent[node], node, weight[node]));
        }

        return result;
    }

    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        this.adjacentList = new HashMap<>();
        for (int node = 0; node < numberOfNodes; node++) {
            adjacentList.put(node, new LinkedList<>());
        }
    }

}

public class PrimsAlgo {

    public static void main(String[] args) {

        System.out.println("\n --- Prim's Algorithm --- \n");
		
		/*Graph graph = new Graph(5);
			graph.addEdge(0, 1, 2);
			graph.addEdge(0, 2, 14);
			graph.addEdge(0, 4, 55);
			graph.addEdge(1, 2, 7);
			graph.addEdge(1, 3, 8);
			graph.addEdge(1, 4, 99);
			graph.addEdge(2, 3, 101);
			graph.addEdge(2, 4, 56);
        graph.addEdge(3, 4, 48);*/

        /* ----- User Input Code ----- */
        java.util.Scanner input = new java.util.Scanner(System.in);

        System.out.print("Enter the number of nodes in your graph (CAUTION - Integer numbers only.): ");
        int numberOfNodes = input.nextInt();
        if (numberOfNodes < 1) {
            System.out.println("Invalid Input !!! ---> Number of nodes cannot be less than 1.\n");
            System.exit(0);
        }
		
		/*System.out.print("\nEnter the starting node (CAUTION - Integer numbers only.): ");		
			int sourceNode = input.nextInt();  
			if (sourceNode < 0 || sourceNode >= numberOfNodes) {
			System.out.println("Invalid Input !!! ---> Starting node should be within the range (0 to " + (numberOfNodes - 1) + " inclusive). \n");
			System.exit(0);
        }*/

        System.out.print("\nEnter the number of connections in your graph (CAUTION - Integer numbers only.): ");
        int numberOfConnections = input.nextInt();
        if (numberOfConnections < 0) {
            System.out.println("Invalid Input !!! ---> Number of connections cannot be less than 0.\n");
            System.exit(0);
        }

        List<List<Integer>> connections = new LinkedList<>();
        System.out.println("\n\nEnter the all the connections one by one (CAUTION -Integer numbers only.)::");
        for (int count = 1; count <= numberOfConnections; ) {
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

        System.out.println("Adjacent List:");
        for (int node = 0; node < numberOfNodes; node++) {
            System.out.print("Node = " + node + " [ " + graph.getAdjacentList().get(node) + " ] \n");
        }

        List<ResultGraphConnection> resultGraph = graph.processPrimMinimumSpanningTree();
        String result = "";
        for (ResultGraphConnection connection : resultGraph) {
            result += (connection.toString() + "\n");
        }
        System.out.println("\n\nMinimum Spanning Tree:\n" + result);

    }

}