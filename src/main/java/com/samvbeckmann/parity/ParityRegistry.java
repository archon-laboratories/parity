package com.samvbeckmann.parity;

import com.samvbeckmann.parity.core.AbstractAgent;
import com.samvbeckmann.parity.core.ICompletionCondition;
import com.samvbeckmann.parity.core.IInteractionHandler;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * parity, class made on 2015-07-02
 *
 * @author Sam Beckmann
 */
public final class ParityRegistry
{
    private static List<ReflectionWrapper> agents = new ArrayList<>();
    private static List<ReflectionWrapper> interactionHandlers = new ArrayList<>();
    private static List<ReflectionWrapper> completionConditions = new ArrayList<>();

    static void initializeRegistry()
    {
        Reflections reflections = new Reflections("com.samvbeckmann.parity.demoProgram");

        Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(ParitySubscribe.class);

        for (Class handler : handlers)
        {
            Method[] methods = handler.getMethods();
            for (Method method : methods)
            {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations)
                {
                    if (annotation instanceof ParitySubscribe.RegisterClasses)
                    {
                        RegisterType type = ((ParitySubscribe.RegisterClasses) annotation).value();
                        switch (type)
                        {
                            case AGENT:
                                registerAgent(handler, method);
                                break;
                            case INTERACTION_HANDLER:
                                registerInteractionHandler(handler, method);
                                break;
                            case COMPLETION_CONDITION:
                                registerCompletionCondition(handler, method);
                                break;
                        }
                    }
                }
            }
        }
    }

    private static boolean registerAgent(Class handler, Method agentMethod)
    {
        try
        {
            Object instance = handler.newInstance();

            if (methodReturnsClassList(agentMethod))
            {
                @SuppressWarnings("unchecked")
                List<Class> agentClasses = (List<Class>) agentMethod.invoke(instance);
                for (Class agentClass : agentClasses)
                {
                    if (agentClass.getSuperclass().equals(AbstractAgent.class))
                    {
                        AbstractAgent agent = (AbstractAgent) agentClass.newInstance();
                        String name = agent.getName();
                        String classpath = agentClass.getName();
                        agents.add(new ReflectionWrapper(classpath, name));
                        return true;
                    } else
                    {
                        System.out.println("Class " + agentClass.getName() + " not added. Note, this class should extend AbstractAgent."); // TODO
                    }
                }
            } else
            {
                System.out.println("Method " + agentMethod + " in class" + handler + " not added. Note, this method should return a List<Class>"); // TODO

            }

        } catch (Exception e)
        {
            System.err.println("Uh Oh."); // TODO
            e.printStackTrace();
        }

        return false;
    }

    private static boolean registerInteractionHandler(Class handler, Method handlerMethod)
    {
        try
        {
            Object instance = handler.newInstance();

            if (methodReturnsClassList(handlerMethod))
            {
                @SuppressWarnings("unchecked")
                List<Class> handlerClasses = (List<Class>) handlerMethod.invoke(instance);
                for (Class handlerClass : handlerClasses)
                {
                    Class[] interfaces = handlerClass.getInterfaces();
                    boolean flag = false;
                    for (Class inter : interfaces)
                    {
                        if (inter.equals(IInteractionHandler.class))
                        {
                            flag = true;
                        }
                    }
                    if (flag)
                    {
                        IInteractionHandler interactionHandler = (IInteractionHandler) handlerClass.newInstance();
                        String name = interactionHandler.getName();
                        String classpath = handlerClass.getName();
                        interactionHandlers.add(new ReflectionWrapper(classpath, name));
                        return true;
                    } else
                    {
                        System.out.println("Class " + handlerClass.getName() + " not added. Note, this class should implement IInteractionHandler."); // TODO
                    }
                }
            } else
            {
                System.out.println("Method " + handlerMethod + " in class" + handler + " not added. Note, this method should return a List<Class>"); // TODO

            }

        } catch (Exception e)
        {
            System.err.println("Uh Oh."); // TODO
            e.printStackTrace();
        }

        return false;
    }

    private static boolean registerCompletionCondition(Class handler, Method conditionMethod)
    {
        try
        {
            Object instance = handler.newInstance();

            if (methodReturnsClassList(conditionMethod))
            {
                @SuppressWarnings("unchecked")
                List<Class> conditionClasses = (List<Class>) conditionMethod.invoke(instance);
                for (Class conditionClass : conditionClasses)
                {
                    Class[] interfaces = conditionClass.getInterfaces();
                    boolean flag = false;
                    for (Class inter : interfaces)
                    {
                        if (inter.equals(ICompletionCondition.class))
                        {
                            flag = true;
                        }
                        if (flag)
                        {
                            ICompletionCondition completionCondition = (ICompletionCondition) conditionClass.newInstance();
                            String name = completionCondition.getName();
                            String classpath = conditionClass.getName();
                            completionConditions.add(new ReflectionWrapper(classpath, name));
                            return true;
                        } else
                        {
                            System.out.println("Class " + conditionClass.getName() + " not added. Note, this class should implement IInteractionHandler."); // TODO
                        }
                    }
                }
            } else
            {
                System.out.println("Method " + conditionMethod + " in class" + handler + " not added. Note, this method should return a List<Class>"); // TODO

            }
        } catch (Exception e)
        {
            System.err.println("Uh Oh."); // TODO
            e.printStackTrace();
        }

        return false;
    }

    private static boolean methodReturnsClassList(Method method)
    {
        Type returnType = method.getGenericReturnType();

        if (!(returnType instanceof ParameterizedType))
            return false;

        ParameterizedType type = (ParameterizedType) returnType;
        Type[] typeArguments = type.getActualTypeArguments();

        if (typeArguments.length != 1)
            return false;

        Class typeArgClass = (Class) typeArguments[0];

        return typeArgClass.equals(Class.class);
    }

    public static List<ReflectionWrapper> getAgents()
    {
        return agents;
    }

    public static List<ReflectionWrapper> getInteractionHandlers()
    {
        return interactionHandlers;
    }

    public static List<ReflectionWrapper> getCompletionConditions()
    {
        return completionConditions;
    }
}
