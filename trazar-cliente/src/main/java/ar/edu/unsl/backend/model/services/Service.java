package ar.edu.unsl.backend.model.services;

import ar.edu.unsl.backend.util.ExpressionChecker;
import ar.edu.unsl.frontend.service_subscribers.ServiceSubscriber;

public abstract class Service
{
    private ExpressionChecker expressionChecker;
    private ServiceSubscriber serviceSubscriber;

    // ================================= Getters && setters =================================
    public ExpressionChecker getExpressionChecker()
    {
        return this.expressionChecker;
    }

    public void setExpressionChecker(ExpressionChecker expressionChecker)
    {
        this.expressionChecker = ExpressionChecker.getExpressionChecker();
    }

    public ServiceSubscriber getServiceSubscriber()
    {
        return this.serviceSubscriber;
    }

    public void setServiceSubscriber(ServiceSubscriber serviceSubscriber)
    {
        this.serviceSubscriber = serviceSubscriber;
    }

    // =================================== private methods ==================================


    // ================================= protected methods ==================================
    
    
    // =================================== public methods ===================================

    /*
    public void executeTask(Task<Void> task)
    {

    }
    */
}