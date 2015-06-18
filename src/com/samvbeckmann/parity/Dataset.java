package com.samvbeckmann.parity;

import com.google.gson.stream.JsonReader;

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
    // TODO Connections
    int numTrials;
    int maxTravel;

    String agentClassPath;

    private ArrayList<ArrayList<Integer>> priorities;

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
                case "numIterations":
                    numTrials = reader.nextInt();
                    break;

                case "population":
                    parsePopulation(reader);
                    prepareCommunities();
                    break;

                case "agentFile":
                    agentClassPath = reader.nextString();
                    break;

                case "communities":
                    parseCommunities(reader);
                    break;
            }
        }
        reader.endObject();
        reader.close();
    }

    private void parseCommunities(JsonReader reader) throws IOException
    {
        int count = 0;
        communities[count] = new Community();
        Community currentCommunity;
        reader.beginArray();

        while (reader.hasNext()) // List of communities
        {
            currentCommunity = communities[count];
            int x = 0;
            int y = 0;
            ArrayList<AbstractAgent> agents = new ArrayList<>();

            if (reader.peek() == null)
                reader.skipValue();
            else
            {
                reader.beginArray();
                while (reader.hasNext()) // List of agents in one x
                {
                    if (reader.peek() == null)
                        reader.skipValue();
                    else
                    {
                        reader.beginArray();
                        while (reader.hasNext())
                        { // Actual agent
                            if (reader.peek() == null)
                                reader.skipValue();
                            else
                            {
                                AbstractAgent curAgent = getAgent(agentClassPath);
                                assert curAgent != null;
                                agents.add(curAgent);
                                curAgent.setX(x);
                                curAgent.setY(y);
                                curAgent.setOpinion(reader.nextDouble());
                            }
                            y++;
                        }
                        reader.endArray();
                        x++;
                    }
                }
                reader.endArray();
            }
            AbstractAgent[] actualAgents = new AbstractAgent[agents.size()];
            actualAgents = agents.toArray(actualAgents);
            currentCommunity.setAgents(actualAgents);
        }
        reader.endArray();
    }

    private void prepareCommunities()
    {
        ArrayList<Community> tempCommunities = new ArrayList<>();
        // Initialize the array with the exact number of entries.
        for (int i = 0; i < priorities.size(); i++)
            for (int j = 0; j < priorities.get(i).size(); j++)
            {
                Community curCommunity = new Community();
                tempCommunities.add(curCommunity);
                curCommunity.setX(i);
                curCommunity.setY(j);
            }

        communities = tempCommunities.toArray(communities);
    }

    private void parsePopulation(JsonReader reader) throws IOException
    {
        reader.beginObject();
        while (reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case "topography":
                    parseTopography(reader);
                    break;

                case "maxTravelDistance":
                    maxTravel = reader.nextInt();
                    break;
            }
        }
        reader.endObject();
    }

    private void parseTopography(JsonReader reader) throws IOException
    {
        reader.beginArray();
        priorities = new ArrayList<>();
        while (reader.hasNext())
        {
            if (reader.peek() == null)
                reader.skipValue();
            else
            {
                priorities.add(new ArrayList<Integer>());
                reader.beginArray();
                while (reader.hasNext())
                    if (reader.peek() == null)
                        reader.skipValue();
                    else
                        priorities.get(priorities.size() - 1).add(reader.nextInt());
                reader.endArray();
            }
        }
        reader.endArray();
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
