import java.util.*;

public class Assignment04 {
    // problem 1
    // using BFS
    public static boolean hasPath(int n, int[][] edges, int source, int destination) {
        if (source == destination) return true;

        // Build the graph using adjacency list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);  // because the graph is bidirectional
        }

        // BFS setup
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        
        // Start BFS from the source vertex
        queue.add(source);
        visited.add(source);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // Process each neighbor
            for (int neighbor : graph.get(current)) {
                if (neighbor == destination) {
                    return true;  // path found
                }
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;  // path not found
    }
    // problem 2
    private static List<List<Integer>> graph;
    private static boolean[] visited;
    private static boolean[] recStack;  // recursion stack for cycle detection

    public static boolean allLeadsToDestination(int n, int[][] edges, int source, int destination) {
        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
        }

        // Destination should not have outgoing edges
        if (!graph.get(destination).isEmpty()) {
            return false;
        }

        visited = new boolean[n];
        recStack = new boolean[n];

        return dfs(source, destination);
    }

    private static boolean dfs(int node, int destination) {
        if (recStack[node]) return false;  // Cycle detected
        if (visited[node]) return true;  // Already processed

        if (node != destination && graph.get(node).isEmpty()) return false;  // Stuck at a non-destination node

        recStack[node] = true;
        visited[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!dfs(neighbor, destination)) {
                return false;
            }
        }

        recStack[node] = false;
        return true;
    }

    public static void main(String[] args) {
        //test for problem 1
        //test 1 for problem 1
        int n_1 = 3; 
        int[][] edges_1 = { {0, 1}, {1, 2}, {2, 0} };
        int source_1 = 0;
        int destination_1 = 2;
        //test 2 for problem 1
        int n_2 = 6; 
        int[][] edges_2 = { {0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3} };
        int source_2 = 0;
        int destination_2 = 5;

        System.out.println(hasPath(n_1, edges_1, source_1, destination_1));  // Output: True
        System.out.println(hasPath(n_2, edges_2, source_2, destination_2));  // Output: False


        //test for problem 2
        //test 1 for problem 2
        int n_3 = 3;
        int[][] edges_3 = {{0, 1}, {0, 2}};
        int source_3 = 0; 
        int destination_3 = 2;

        //test 2 for problem 2
        int n_4 = 4;
        int[][] edges_4 = {{0, 1}, {0, 3},{1, 2},{2, 1}};
        int source_4 = 0; 
        int destination_4 = 3;

        //test 3 for problem 2
        int n_5 = 4;
        int[][] edges_5 = {{0, 1}, {0, 2},{1, 3},{2, 3}};
        int source_5 = 0; 
        int destination_5 = 3;


        System.out.println(allLeadsToDestination(n_3, edges_3, source_3, destination_3));  // Output: False
        System.out.println(allLeadsToDestination(n_4, edges_4, source_4, destination_4));  // Output: False
        System.out.println(allLeadsToDestination(n_5, edges_5, source_5, destination_5));  // Output: True
    }
}

