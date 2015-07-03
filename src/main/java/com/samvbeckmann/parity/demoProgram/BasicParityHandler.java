package com.samvbeckmann.parity.demoProgram;

import com.samvbeckmann.parity.ParitySubscribe;
import com.samvbeckmann.parity.RegisterType;

import java.util.ArrayList;
import java.util.List;

/**
 * parity, class made on 2015-07-02
 *
 * @author Sam Beckmann
 */
@ParitySubscribe
public class BasicParityHandler
{
    @ParitySubscribe.RegisterClasses(RegisterType.INTERACTION_HANDLER)
    public List<Class> registerInteractionHandlers()
    {
        List<Class> interactionHandlers = new ArrayList<>();
        interactionHandlers.add(BasicInteractionHandler.class);
        return interactionHandlers;
    }

    @ParitySubscribe.RegisterClasses(RegisterType.COMPLETION_CONDITION)
    public List<Class> registerCompletionConditions()
    {
        List<Class> completionConditions = new ArrayList<>();
        completionConditions.add(BasicCompletionCondition.class);
        return completionConditions;
    }

    @ParitySubscribe.RegisterClasses(RegisterType.AGENT)
    public List<Class> registerAgents()
    {
        List<Class> agents = new ArrayList<>();
        agents.add(BasicAgent.class);
        return agents;
    }
}
