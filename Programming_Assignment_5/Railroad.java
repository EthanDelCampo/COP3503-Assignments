/* Ethan Del Campo
 * Dr. Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 5
 */

import java.util.*;
import java.io.*;

public class Railroad 
{

    //Attributes used for the Railroad class
    private int numEdges;
    private String edgesFile;
    private ArrayList<Edge> edgesList;

    
    //Constructor for Railroad class
    public Railroad(int numEdges, String edgesFile)
    {
        this.numEdges = numEdges;
        this.edgesFile = edgesFile;

        this.edgesList = new ArrayList<Edge>();

        //Tries to scan edgesFile and add edges to edgesList
        try
        {
            
            //Path to edges file
            File file = new File(edgesFile);

            //Scanner to read edges file
            Scanner scanner = new Scanner(file);

            //Reads text file
            while(scanner.hasNextLine())
            {

                //Separates each part by character
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+");

                if (parts.length == 3) {
                    String source = parts[0];
                    String destination = parts[1];
                    int weight = Integer.parseInt(parts[2]);

                    //Adds new edge to edgesList
                    edgesList.add(new Edge(source, destination, weight));
                }

            }

            //Closes scanner
            scanner.close();

        }
        //File not found, program ends unsuccessfully
        catch (FileNotFoundException e)
        {
            System.exit(1);
        }

    }



    //Utilizes Kruskal's algorithm to make a new set
    public String buildRailroad()
    {

        //Sets up string to return
        StringBuilder result = new StringBuilder();

        //Sorts edgesList by weight amount
        Collections.sort(edgesList, new Comparator<Edge>() 
            {
            @Override
            public int compare(Edge e1, Edge e2) {
                return Integer.compare(e1.getWeight(), e2.getWeight());
            }
        });

        DisjointSet disjointSet = new DisjointSet(); //Sets up disjoint set

        //Adds each vertex to disjoint set
        for (Edge edge : edgesList)
        {
            disjointSet.makeSet(edge.getSource());
            disjointSet.makeSet(edge.getDestination());
        }

        //Keeps track of number of edges used
        int edgesUsed = 0;

        //initializes total cost to 0
        int totalCost = 0;

        for (Edge edge : edgesList) 
        {

            //Gets source and gets destination
            String source = edge.getSource();
            String destination = edge.getDestination();
    
            //Ensures edge does not form cycle
            if(!disjointSet.find(source).equals(disjointSet.find(destination))) 
            {
                // Add the edge to the MST
                disjointSet.union(source, destination);
                result.append(source + "---" + destination + "\t$" + edge.getWeight() + "\n");
                totalCost += edge.getWeight();
                edgesUsed++;
    
                // Stop if the MST is complete
                if (edgesUsed == edgesList.size() - 1) {
                    break;
                }
            }
        }
        
        //Adds total cost to resulting string
        result.append("The cost of the railroad is $" + totalCost + ".");

        //Converts StringBuilder result to String; returns the string
        return result.toString();

    }

}



//Helper class that represents one specific edge
class Edge
{

    //Attributes representing source, destination, weight
    private String source;
    private String destination;
    private int weight;


    //Constructor for an Edge object
    public Edge(String source, String destination, int weight) {

        /* Ensures source and destination are 
        * always in alphabetical order as Kruskal's
        * algorithm does not account for order
        */
        if(source.compareTo(destination) <= 0) 
        {
            this.source = source;
            this.destination = destination;
        } 
        else 
        {
            this.source = destination;
            this.destination = source;
        }

        this.weight = weight;

    }
    

    //Getter method that returns source
    public String getSource()
    {
        return source;
    }


    //Getter method that returns destination
    public String getDestination()
    {
        return destination;
    }


    //Getter method that returns weight
    public int getWeight()
    {
        return weight;
    }

}



//Helper class used to detect cycles for the algorithm
class DisjointSet
{

    //Attribute that stores the parent of each vertex
    private Map<String, String> parent;


    //Constructor for a DisjointSet
    public DisjointSet()
    {
        this.parent = new HashMap<>();
    }


    //Creates a new set for a nonexistent vertex
    public void makeSet(String vertex)
    {
        if(!parent.containsKey(vertex))
        {
            parent.put(vertex, vertex);
        }
    }


    //Finds root of set containing particular vertex
    public String find(String vertex)
    {
        if(!parent.get(vertex).equals(vertex))
        {
            //Sets up tree structure for future find operations
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }


    //Union operation on two sets containing two vertices
    public void union(String vertex1, String vertex2) {

        String root1 = find(vertex1);
        String root2 = find(vertex2);

        //Ensures roots are not equal
        if (!root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }
    
}