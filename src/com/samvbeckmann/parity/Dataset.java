package com.samvbeckmann.parity;

import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Nate Beckemeyer and Sam Beckmann.
 */
public class Dataset
{
    int numTrials;
    int opinionCount;

    Community[] communities;

    /**
     * Gets an object of an agent from its classpath
     *
     * @param classPath The classpath of the agent
     * @return The corresponding agent
     */
    private AbstractAgent getAgent(String classPath)
    {
        try
        {
            return (AbstractAgent) Class.forName(classPath).newInstance();
        } catch (Exception e)
        {
            System.err.println("Warning! Could not locate agent class " + classPath);
            return null;
        }
    }

    private void parseInput(JsonReader reader) throws IOException
    {
        reader.beginObject();
        while (reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case "population":
                    parsePopulation(reader);
                    break;

                default:
                    System.out.println("Tag " + name + " not found!");
            }
        }
        reader.endObject();
        reader.close();
    }

    private AbstractAgent[] parseAgents(JsonReader reader, int agentCount) throws IOException
    {
        AbstractAgent[] agents = new AbstractAgent[agentCount];
        int count = 0;

        reader.beginArray();
        while (reader.hasNext()) // Agents array
        {
            if (reader.peek() == null)
            {
                reader.skipValue();
                continue;
            }

            reader.beginObject();
            if (!(reader.nextName().equals("sourceFile")))
                System.exit(3);

            agents[count] = getAgent(reader.nextString());
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "xPos":
                        agents[count].setX(reader.nextInt());
                        break;

                    case "yPos":
                        agents[count].setY(reader.nextInt());
                        break;

                    case "opinions":
                        reader.beginArray();
                        agents[count].setOpinion(reader.nextDouble());
                        reader.endArray();
                }
            }
            count++;
            reader.endObject();
        }

        reader.endArray();

        return agents;
    }

    private void parseCommunities(JsonReader reader) throws IOException
    {
        int count = 0;
        communities[count] = new Community();
        reader.beginArray();

        while (reader.hasNext()) // List of communities
        {
            if (reader.peek() == null)
            {
                reader.skipValue();
                continue;
            }

            parseCommunity(reader);
        }
        reader.endArray();
    }

    private void parseCommunity(JsonReader reader) throws IOException
    {
        reader.beginObject();

        int id_ = -1;
        int numNeighbors = 0;
        int agentCount = 0;
        while (reader.hasNext())
        {
            String name = reader.nextName();

            switch (name)
            {
                case "id":
                    id_ = reader.nextInt();
                    break;

                case "numNeighbors":
                    numNeighbors = reader.nextInt();
                    break;

                case "neighbors":
                    communities[id_].setNeighbours(parseNeighbors(reader, numNeighbors));
                    break;

                case "agentCount":
                    agentCount = reader.nextInt();
                    break;

                case "agents":
                    communities[id_].setAgents(parseAgents(reader, agentCount));
                    break;

                default:
                    System.err.println("Community property " + name + " not found!");

            }
        }
        reader.endObject();
    }

    private OneWayConnection[] parseNeighbors(JsonReader reader, int numNeighbors) throws IOException
    {
        int neighborCount = 0;
        OneWayConnection[] neighbors = new OneWayConnection[numNeighbors];

        reader.beginArray();
        while (reader.hasNext())
        {
            if (reader.peek() == null)
            {
                reader.skipValue();
                continue;
            }
            OneWayConnection connect = new OneWayConnection();
            connect.setCommunity(communities[reader.nextInt()]);
            neighbors[neighborCount] = connect;
            neighborCount++;
        }
        reader.endArray();

        return neighbors;
    }

    private void parsePopulation(JsonReader reader) throws IOException
    {
        reader.beginObject();
        while (reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case "numIterations":
                    numTrials = reader.nextInt();
                    break;

                case "numberCommunities":
                    int numCommunities = reader.nextInt();
                    communities = new Community[numCommunities];
                    for (int i = 0; i < numCommunities; i++)
                    {
                        communities[i] = new Community();
                    }
                    break;

                case "opinionCount":
                    opinionCount = reader.nextInt();
                    break;

                case "communities":
                    parseCommunities(reader);
                    break;

                default:
                    System.out.println("JSON tag " + name + " in \"population\" not found!");
                    break;
            }
        }
        reader.endObject();
    }

    public Dataset(String filename)
    {
        boolean fileFound = false;
        Scanner console = new Scanner(System.in);

        do
        {
            try
            {
                JsonReader reader = new JsonReader(new FileReader(filename));
                fileFound = true;
                parseInput(reader);
            } catch (FileNotFoundException e)
            {
                System.err.print("File " + filename + " not found. Please try another file: ");
                filename = console.next();
            } catch (IOException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
        } while (!fileFound);
        console.close();
    }

    public Population getDatasetPopulation()
    {
        return new Population(communities);
    }

    public int getNumTrials()
    {
        return numTrials;
    }
}
