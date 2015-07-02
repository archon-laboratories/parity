package com.samvbeckmann.parity.core;

import com.google.gson.stream.JsonReader;
import com.samvbeckmann.parity.demoProgram.BasicCompletionCondition;
import com.samvbeckmann.parity.demoProgram.BasicInteractionHandler;
import com.samvbeckmann.parity.utilities.InteractionHelper;
import com.samvbeckmann.parity.utilities.Shuffler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Nate Beckemeyer and Sam Beckmann.
 */
public class Dataset
{
    private final HashMap<AbstractAgent, ArrayList<Double>> initialAgentOpinions;
    private int numTrials;
    private int opinionCount;
    private Community[] communities;
    private JsonReader reader;
    private IInteractionHandler interactionHandler;
    private ICompletionCondition completionCondition;
    private Connection[] connections;


    /**
     * Converts the JSON dataset file to java variables, enabling the program to run.
     *
     * @param filename The name of the JSON file where the information is located.
     */
    public Dataset(String filename)
    {
        interactionHandler = new BasicInteractionHandler();
        completionCondition = new BasicCompletionCondition();
        initialAgentOpinions = new HashMap<>();

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

        removeOneWayNeighbours();
    }

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
                        initialAgentOpinions.put(agents[count], agentOpinions);
                        agents[count].setOpinions(new ArrayList<>(agentOpinions));
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
     * @return The {@link Connection}s of the community
     * @throws IOException
     */
    private Connection[] parseNeighbors(int numNeighbors, int thisId_) throws IOException
    {
        int neighborCount = 0;
        Connection[] neighbours = new Connection[numNeighbors];

        reader.beginArray();
        while (reader.hasNext())
        {
            if (reader.peek() == null)
            {
                reader.skipValue();
                continue;
            }
            reader.beginArray();
            boolean counted = false;
            int possibleInteractions = -1;
            Community other = null;

            while (reader.hasNext())
            {
                if (reader.peek() == null)
                {
                    reader.skipValue();
                    continue;
                }
                if (!counted)
                {
                    possibleInteractions = reader.nextInt();
                    counted = true;
                } else
                {
                    other = communities[reader.nextInt()];
                }
            }
            reader.endArray();
            neighbours[neighborCount] = new Connection(communities[thisId_], other, possibleInteractions);
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
                    communities[id_].setNeighbours(parseNeighbors(numNeighbors, id_));
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

    private void removeOneWayNeighbours()
    {
        for (Community comm : communities)
        {
            ArrayList<Connection> newNeighbours = new ArrayList<>();
            for (Connection neighbour : comm.getNeighbours())
                if (neighbour.getNeighbourCommunity().getConnectionByCommunity(comm) != null)
                {
                    Connection connection = new Connection(comm, neighbour.getNeighbourCommunity(),
                            neighbour.getPossibleInteractions());
                    newNeighbours.add(connection);
                }
            comm.setNeighbours(newNeighbours.toArray(new Connection[newNeighbours.size()]));
        }
        connections = InteractionHelper.getConnectionsFromPopulation(new Population(communities));
    }

    /**
     * @return A population from the generated data
     */
    public Population getDatasetPopulation()
    {
        for (Community current : communities)
            for (AbstractAgent agent : current.getAgents())
                agent.setOpinions(new ArrayList<>(initialAgentOpinions.get(agent)));

        Population created = new Population(communities);
        created.setConnections(connections);

        return created;

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

    /**
     * Shuffles the array of communities within the population. Within each community, agent locations are also shuffled.
     * It also shuffles the order of the connections retrieved by the population.
     * This does nothing more than change the order in which communities are examined or read; if sloppy code will
     * prefer the first community it finds (such as lower index), this method will change which community that is.
     */
    public Population shuffleData()
    {
        new Shuffler<Community>().shuffleArray(communities);

        // Swap the agents within each community
        for (Community current : communities)
            current.setAgents(new Shuffler<AbstractAgent>().copyRandom(current.getAgents()));

        // Shuffle the connections in the population
        new Shuffler<Connection>().shuffleArray(connections);

        return getDatasetPopulation();
    }
}
