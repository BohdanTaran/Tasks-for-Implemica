package com.company;

import java.util.Arrays;

public class City
{
    private String name = "";
    private int index = 0;
    private int cost;
    int[] neighbours;
    int where_went;
    boolean imVisited;

    public City(String name, int index, int cost, int[] neighbours, boolean imVisited)
    {
        this.name = name;
        this.index = index;
        this.cost = cost;
        this.neighbours = neighbours;
        this.where_went = where_went;
        this.imVisited = false;
    }

    //////////////////////////// Getters ////////////////////////////

    public int getIndex() { return this.index; }
    public boolean getImVisited () { return this.imVisited; }

    public int getCost() { return this.cost; }
    public int getCost(int index_of_neighbour)
    {
        if (index_of_neighbour == 0)
        {
            return neighbours[index_of_neighbour + 1];
        }
        else
        {
            return neighbours[index_of_neighbour * 2 + 1];
        }
    }

    public String getName() {
        return this.name;
    }

    public int getSizeOfNeighbours()
    {
        int size = 0;
        for (int i = 0; i < neighbours.length; i++)
        {
            if (i % 2 == 0) { size++; }
        }
        return size;
    }

    public int getEdge(int index_of_neighbour)
    {
        if (index_of_neighbour == 0)
        {
            return neighbours[index_of_neighbour + 1];
        }
        else
        {
            return neighbours[index_of_neighbour * 2 + 1];
        }
    }

    public int getNeighbour (int index)
    {
        if (index == 0)
        {
            return neighbours[index] - 1;
        }
        else
        {
            return neighbours[index * 2] - 1;
        }
    }

    //////////////////////////// Setters ////////////////////////////

    public void setCost(int cost) { this.cost = cost; }
    public void setWhere_went(int index) { this.where_went = index; }
    public void setImVisited() { this.imVisited = true; }
}
