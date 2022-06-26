package com.company;

import java.io.*;
import java.util.*;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        File file = new File("dataConsole.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<City> massive_of_cities = new LinkedList<City>();

        Scanner sc = new Scanner(System.in);

        /*
        * line - for reading information from file
        * neighbours_cost - initialize cost for each city
        * */
        String line = bufferedReader.readLine();
        int number_of_test = Integer.parseInt(line);
        int quantity_cities = 0;

        for (int test = 0; test < number_of_test; test++)
        {
            line = bufferedReader.readLine();
            quantity_cities = Integer.parseInt(line);
            int index_of_city = 0; // index each city (the index of the first city is 1)

            /*
            * name_of_city - name from file for send to constructor
            * quantity_neighbours - read info about quantity neighbours for each city
            * */
            for (int cityIndex = 0; cityIndex < quantity_cities; cityIndex++)
            {
                line = bufferedReader.readLine();
                String name_of_city = line;
                line = bufferedReader.readLine();
                int quantity_neighbours = Integer.parseInt(line);

                /*
                * for_neighbours - variable for build string in order to send string to constructor like massive of chars
                * */
                String for_neighbours = "";
                int[] neighbours_cost; // for quantity of neighbours and them cost
                for (int neighbour = 0; neighbour < quantity_neighbours; neighbour++)
                {
                    line = bufferedReader.readLine();
                    for_neighbours += line + " ";
                }
                // Building object (City)
                neighbours_cost =  Arrays.stream(for_neighbours.split(" ")).mapToInt(Integer::parseInt).toArray();
                final int INFINITY = 1000000; // instead a symbol '∞'
                City city = new City(name_of_city, index_of_city, INFINITY, neighbours_cost, false);
                massive_of_cities.add(city);
                index_of_city++;
            }
        }

//        for (City c : massive_of_cities) {
//            c.show();
//        }

        System.out.print("From city: ");
        String start_city = sc.nextLine();
        System.out.print("To city: ");
        String end_city = sc.nextLine();

        try
        {
            algorithm(massive_of_cities, start_city, end_city, quantity_cities);
        }
        catch (NoSuchElementException e)
        {
            System.out.print("");
        }

    }

    public static void algorithm (List<City> massive_of_cities, String start_city, String end_city, int quantity_cities)
    {
        // index of started city
        int index_of_city = 0;
        boolean stop = false; // For start and end loop
        final int INFINITY = 1000000; // instead a symbol '∞'

        // Set start city
        for (int i = 0; i < massive_of_cities.size(); i++)
        {
            String name = massive_of_cities.get(i).getName();
            if (name.equals(start_city))
            {
                index_of_city = massive_of_cities.get(i).getIndex();
                break;
            }
        }

        massive_of_cities.get(index_of_city).setCost(0); // reset cost to zero because first item has 0
        while (!stop)
        {
            // If current city never was visit - we enter next
            if (massive_of_cities.get(index_of_city).getImVisited() == false)
            {
                int size_neighbours = massive_of_cities.get(index_of_city).getSizeOfNeighbours();
                List<Integer> mas_of_min_costs = new ArrayList<>(); // For costs of neighbours
                for (int neighbour = 0; neighbour < size_neighbours; neighbour++)
                {
                    // Getting value edge of neighbour
                    int edge = massive_of_cities.get(index_of_city).getEdge(neighbour);
                    // Getting index from neighbour for take cost of neighbour from list
                    int index_of_neighbour  = massive_of_cities.get(index_of_city).getNeighbour(neighbour);
                    // Getting cost of neighbour
                    int cost_of_neighbour = massive_of_cities.get(index_of_neighbour).getCost();
                    // Variable in order to add cost of edge to cost of neighbour
                    int cost_of_city = massive_of_cities.get(index_of_city).getCost();
                    // Set cost for neighbour

                    if ((cost_of_city + edge) < cost_of_neighbour)
                    {
                        massive_of_cities.get(index_of_neighbour).setCost(edge + cost_of_city);
                        massive_of_cities.get(index_of_neighbour).setWhere_went(index_of_city);
                    }
                    mas_of_min_costs.add(edge); // Pushing costs of neighbours and in end compare
                }
                // It city we visited
                massive_of_cities.get(index_of_city).setImVisited();

                int min = Collections.min(mas_of_min_costs); // For search min cost of edges

                // For save costs neighbours_of_neighbour
                List<Integer> mas_min_costs_neighbours_of_neighbour = new ArrayList<>();

                for (int neighbour = 0; neighbour < size_neighbours; neighbour++)
                {
                    int edge_of_neighbour = 0;
                    // Getting index from neighbour for check visited of neighbour from list
                    int index_of_neighbour  = 0;
                    boolean this_neighbour_was_visited = false;
                    try
                    {
                        edge_of_neighbour = massive_of_cities.get(index_of_city).getEdge(neighbour);
                        index_of_neighbour  = massive_of_cities.get(index_of_city).getNeighbour(neighbour);
                        this_neighbour_was_visited = massive_of_cities.get(index_of_neighbour).getImVisited();
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("");
                    }

                    // Pushing in massive for check where we need to go
                    if (edge_of_neighbour == min && this_neighbour_was_visited != true) // Equals min cost
                    {
                        int save_current_city = index_of_city; // For save current city
                        try
                        {
                            // step in neighbour for that check all paths from him
                            index_of_city = massive_of_cities.get(index_of_city).getNeighbour(neighbour);
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            System.out.println("");
                        }

                        if (massive_of_cities.get(index_of_city).getName().equals(end_city))
                        {
                            System.out.println("------------------\nMIN COST = " +
                                    massive_of_cities.get(index_of_city).getCost() +
                                    "\n------------------");
                            stop = true;
                        }
                        else
                        {
                            int min_cost_of_neighbour = neighbourPathsCheck(massive_of_cities, index_of_city);
                            mas_min_costs_neighbours_of_neighbour.add(min_cost_of_neighbour);
                            // Return to current city
                            index_of_city = save_current_city;
                        }
                    }
                }

                // Choosing path
                int min_cost = Collections.min(mas_min_costs_neighbours_of_neighbour);
                for (int neighbour = 0; neighbour < size_neighbours; neighbour++)
                {
                    int current_city = index_of_city; // For save current city
                    // step in neighbour for that check all paths from him
                    int step_in_neighbour = massive_of_cities.get(index_of_city).getNeighbour(neighbour);
                    // Getting value edge of neighbour
                    int edge = massive_of_cities.get(index_of_city).getEdge(neighbour);
                    // cost of neighbour
                    int cost_of_neighbour = massive_of_cities.get(step_in_neighbour).getCost();
                    // Getting cost all path of neighbour
                    int cost_path_of_neighbour = neighbourPathsCheck(massive_of_cities, step_in_neighbour);

                    // If city never was initialized
                    if (cost_of_neighbour == INFINITY)
                    {
                        massive_of_cities.get(step_in_neighbour).setCost(edge);
                        cost_of_neighbour = massive_of_cities.get(step_in_neighbour).getCost();
                    }

                    // If path == min cost - we enter to him
                    if (cost_path_of_neighbour == min_cost)
                    {
                        index_of_city = step_in_neighbour;
                        // Set visit previous city
                        massive_of_cities.get(current_city).setImVisited();
                        break;
                    }
                }
            }
        }
    }

    // For check neighbours of neighbour for that check which path we need to choose
    public static int neighbourPathsCheck (List<City> massive_of_cities ,int index_of_city)
    {
        if (massive_of_cities.get(index_of_city).getImVisited() == false)
        {
            int size_neighbours = massive_of_cities.get(index_of_city).getSizeOfNeighbours();
            List<Integer> mas_of_min_costs = new ArrayList<>(); // For costs of neighbours
            for (int neighbour = 0; neighbour < size_neighbours; neighbour++)
            {
                // Getting value edge of neighbour
                int edge = massive_of_cities.get(index_of_city).getEdge(neighbour);
                // Getting index from neighbour for take cost of neighbour from list
                int index_of_neighbour  = massive_of_cities.get(index_of_city).getNeighbour(neighbour);
                boolean this_neighbour_was_visited = massive_of_cities.get(index_of_neighbour).getImVisited();
                // Getting cost of neighbour
                int cost_of_neighbour = massive_of_cities.get(index_of_neighbour).getCost();
                // Variable in order to add cost of edge to cost of neighbour
                int cost_of_city = massive_of_cities.get(index_of_city).getCost();
                // Set cost for neighbour

                if (this_neighbour_was_visited != true)
                {
                    mas_of_min_costs.add(edge + cost_of_city); // Pushing costs of neighbours and in end compare
                }
            }
            int min = Collections.min(mas_of_min_costs); // For search min cost
            return min;
        }
        return index_of_city;
    }
}