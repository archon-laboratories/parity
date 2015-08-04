package com.samvbeckmann.parity;

import com.samvbeckmann.parity.core.AbstractAgent;
import com.samvbeckmann.parity.core.ICompletionCondition;
import com.samvbeckmann.parity.core.IInteractionHandler;
import com.samvbeckmann.parity.reference.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Creates a global registry of available
 * agents, completion conditions, and interaction handlers.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
public final class ParityRegistry
{
    private static ObservableList<ReflectionWrapper> agents = FXCollections.observableArrayList();
    private static ObservableList<ReflectionWrapper> interactionHandlers = FXCollections.observableArrayList();
    private static ObservableList<ReflectionWrapper> completionConditions = FXCollections.observableArrayList();

    private static Set<Class<?>> readDirectory(String pathToDirectory) throws IOException, ClassNotFoundException
    {
        Set<Class<?>> classes = new HashSet<>();
        File directory = new File(pathToDirectory);


        if (directory.isDirectory())
        {
            File[] files = directory.listFiles();
            assert files != null;

            for (File current : files)
                classes.addAll(readPath(current.getAbsolutePath()).stream().collect(Collectors.toList()));
        }

        return classes;
    }

    private static Set<Class<?>> readJar(String pathToJar) throws IOException, ClassNotFoundException
    {
        Set<Class<?>> classes = new HashSet<>();

        JarFile jarFile = new JarFile(pathToJar);
        Enumeration e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements())
        {
            JarEntry je = (JarEntry) e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class"))
                continue;

            // -6 because of the .class suffix
            String className = je.getName().substring(0, je.getName().length() - 6);

            className = className.replace('/', '.');
            try
            {
                Class c = customLoadClass(cl, className);

                assert c != null;
                classes.add(c);
            } catch (Throwable error)
            {
                System.err.println("Could not load class " + className + ". Please ensure that it is not required for" +
                        " the program.");
            }

        }
        return classes;
    }

    private static Class customLoadClass(URLClassLoader cl, String className) throws ClassNotFoundException
    {
        return cl.loadClass(className);
    }

    private static Set<Class<?>> readPath(String path) throws IOException, ClassNotFoundException
    {
        if (path.endsWith(".jar"))
            return readJar(path);
        else
            return readDirectory(path);
    }

    static void populateRegistry()
    {
        Set<Class<?>> handlers;
        try
        {
            handlers = readPath("extend");
        } catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Internal error populating registry.");
            e.printStackTrace();
            handlers = new HashSet<>();
        }


        for (Class handler : handlers)
        {
            try
            {
                if (handler.getAnnotation(ParitySubscribe.class) == null)
                    continue;

                boolean matched = false; // Ensure that all classes with @ParitySubscribe are used by the project

                if (handler.newInstance() instanceof AbstractAgent)
                {
                    agents.add(new ReflectionWrapper(handler.getName(),
                            ((AbstractAgent) handler.newInstance()).getName()));
                    matched = true;
                }

                if (handler.newInstance() instanceof ICompletionCondition)
                {
                    completionConditions.add(new ReflectionWrapper(handler.getName(),
                            ((ICompletionCondition) handler.newInstance()).getName()));
                    matched = true;
                }

                if (handler.newInstance() instanceof IInteractionHandler)
                {
                    interactionHandlers.add(new ReflectionWrapper(handler.getName(),
                            ((IInteractionHandler) handler.newInstance()).getName()));
                    matched = true;
                }

                if (!matched)
                    System.err.printf(Messages.Errors.REGISTER_CLASS_DOES_NOT_EXTEND, handler);

            } catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }
    }

    public static ObservableList<ReflectionWrapper> getAgents()
    {
        return agents;
    }

    public static ObservableList<ReflectionWrapper> getInteractionHandlers()
    {
        return interactionHandlers;
    }

    public static ObservableList<ReflectionWrapper> getCompletionConditions()
    {
        return completionConditions;
    }
}