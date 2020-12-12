/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.interfaces;

import ar.edu.unsl.backend.model.entities.Local;
import java.util.List;

/**
 *
 * @author demig
 */
public interface ILocalOperator {
    Local find(Integer id) throws Exception;

    Local delete(Integer id) throws Exception;

    public List<Local> findAll();
}
