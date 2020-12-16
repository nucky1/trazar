/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.services;


import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.interfaces.ILocalOperator;
import ar.edu.unsl.backend.model.persistence.LocalOperatorRetrofit;
import ar.edu.unsl.backend.util.Statics;

/**
 *
 * @author demig
 */
public class LocalService extends Service{
    private ILocalOperator operator;
    
    public LocalService(){
        this.operator = new LocalOperatorRetrofit(this);
    }
    
    public void findById() throws Exception {
        this.operator.find(Statics.getUser().getId_local());
    }
    public void update(Local l) throws Exception {
        this.operator.update(l);
    }

    public void insertLocal(Local miLocal) {
        this.operator.insert(miLocal);
    }
}
