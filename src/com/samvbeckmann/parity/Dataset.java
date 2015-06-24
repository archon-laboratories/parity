package com.samvbeckmann.parity;

import com.google.gson.stream.JsonReader;
import com.samvbeckmann.parity.basicProgram.BasicCompletionCondition;
import com.samvbeckmann.parity.basicProgram.BasicInteractionHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Nate Beckemeyer and Sam Beckmann.
 */
public class Dataset
{
    private int numTrials;
    private int opinionCount;

    private Community[] communities;

    private JsonReader reader;

    private IInteractionHandler interactionHandler;
    private ICompletionCondition completionCondition;


    /**
     * Gets a completion condition from its classpath
     *
     * @param classPath The classpath of the completion condition class
     * @return An instantiation of the corresponding completion condition
     */
    private ICompletionCondition getCondition(String classPath)
    {
        try
        {
            return (ICompletionCondition) Class.forName(classPath).newInstance();
        } catch (Exception e)
        {
            System.err.println("Warning! Could not locate completion condition class " + classPath + "!" +
                    "Using " + completionCondition.getClass() + " instead!");
            return null;
        }
    }

    /**
     * Gets an interaction handler from its classpath
     *
     * @param classPath The classpath of the interaction handler class
     * @return An instantiation of the corresponding handler
     */
    private IInteractionHandler getHandler(String classPath)
    {
        try
        {
            return (IInteractionHandler) Class.forName(classPath).newInstance();
        } catch (Exception e)
        {
            System.err.println("Warning! Could not locate interaction handler class " + classPath + "!" +
                    "Using " + interactionHandler.getClass() + " instead!");
            return null;
        }
    }

    /**
     * Gets an instance of an agent from its classpath
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

    /**
     * Parses the agents of a community in the JSON file
     *
     * @param agentCount The number of agents in the community
     * @return The agents of the community
     * @throws IOException
     */
    private AbstractAgent[] parseAgents(int agentCount) throws IOException
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
            while (reader.hasNext())
            {
                String name = reader.nextName();
                switch (name)
                {
                    case "xPos":
                        agents[count].setX(reader.nextInt());
                        break;

                    case "yPos":
                        agents[count].setY(reader.nextInt());
                        break;

                    case "opinions":
                        ArrayList<Double> agentOpinions = new ArrayList<>(opinionCount);
                        reader.beginArray();
                        while (reader.hasNext())
                        {
                            if (reader.peek() == null)
                            {
                                reader.skipValue();
                                continue;
                            }
                            agentOpinions.add(reader.nextDouble());
                        }
                        reader.endArray();
                        agents[count].setOpinions(agentOpinions);
                        break;

                    default:
                        System.out.println("Warning! Tag " + name + " not found in Agent!");
                        break;
                }
            }
            count++;
            reader.endObject();
        }

        reader.endArray();

        return agents;
    }

    /**
     * Parses the neighbors of a community
     *
     * @param numNeighbors The number of neighbors
     * @return The {@link OneWayConnection}s of the community
     * @throws IOException
     */
    private OneWayConnection[] parseNeighbors(int numNeighbors) throws IOException
    {
        int neighborCount = 0;
        OneWayConnection[] neighbours = new OneWayConnection[numNeighbors];

        reader.beginArray();
        while (reader.hasNext())
        {
            if (reader.peek() == null)
            {
                reader.skipValue();
                continue;
            }
            OneWayConnection connect = new OneWayConnection();

            reader.beginArray();
            boolean counted = false;
            while (reader.hasNext()) {
                if (reader.peek() == null) {
                    reader.skipValue();
                    continue;
                }
                if (!counted) {
                    connect.setPossibleInteractions(reader.nextInt());
                    counted = true;
                }
                else {
                    connect.setCommunity(communities[reader.nextInt()]);
                }
            }
            reader.endArray();
            neighbours[neighborCount] = connect;
            neighborCount++;
        }
        reader.endArray();

        return neighbours;
    }

    /**
     * Parses a single community in the JSON file
     *
     * @throws IOException
     */
    private void parseCommunity() throws IOException
    {
        reader.beginObject();

        int id_ = -1;
        int numNeighbors = 0;
        int agentCount = 0;
        int interactionsAvailable = -1;

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

                case "neighbours":
                    assert interactionsAvailable >= 0;
                    communities[id_].setNeighbours(parseNeighbors(numNeighbors));
                    break;

                case "agentCount":
                    agentCount = reader.nextInt();
                    break;

                case "agents":
                    communities[id_].setAgents(parseAgents(agentCount));
                    break;

                default:
                    System.err.println("Community property " + name + " not found!");
                    reader.skipValue();
                    break;
            }
        }
        communities[id_].resetAvailability();
        reader.endObject();
    }

    /**
     * Parses the communities of the JSON file
     *
     * @throws IOException
     */
    private void parseCommunities() throws IOException
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

            parseCommunity();
        }
        reader.endArray();
    }

    /**
     * Parses the population tag of the JSON file
     *
     * @throws IOException
     */
    private void parsePopulation() throws IOException
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
                        communities[i] = new Community();
                    break;

                case "completionCondition":
                    completionCondition = getCondition(reader.nextString());
                    break;

                case "interactionHandler":
                    interactionHandler = getHandler(reader.nextString());
                    break;

                case "opinionCount":
                    opinionCount = reader.nextInt();
                    break;

                case "communities":
                    parseCommunities();
                    break;

                default:
                    System.out.println("JSON tag " + name + " in \"population\" not found!");
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    /**
     * Parses the outermost JSON file
     *
     * @throws IOException error parsing the file
     */
    private void parseInput() throws IOException
    {
        reader.beginObject();
        while (reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case "population":
                    parsePopulation();
                    break;

                default:
                    System.out.println("Outermost tag " + name + " not found!");
            }
        }
        reader.endObject();
        reader.close();
    }

    /**
     * Converts the JSON dataset file to java variables, enabling the program to run.
     *
     * @param filename The name of the JSON file where the information is located.
     */
    public Dataset(String filename)
    {
        interactionHandler = new BasicInteractionHandler();
        completionCondition = new BasicCompletionCondition();

        boolean fileFound = false;
        Scanner console = new Scanner(System.in);

        do
        {
            try
            {
                reader = new JsonReader(new FileReader(filename));
                fileFound = true;
                parseInput();
                reader.close();
                console.close();
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
    }

    /**
     * @return A population from the generated data
     */
    public Population getDatasetPopulation()
    {
        return new Population(communities);
    }

    /**
     * @return The number of trials being run this dataset
     */
    public int getNumTrials()
    {
        return numTrials;
    }

    /**
     * @return The completion condition being used by the program
     */
    public ICompletionCondition getCompletionCondition()
    {
        return completionCondition;
    }

    /**
     * @return The interaction handler for the population
     */
    public IInteractionHandler getInteractionHandler()
    {
        return interactionHandler;
    }
}
