/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.femass.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.femass.model.Administrador;



/**
 *
 * @author acg
 */
@Stateless
public class AdministradorDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Administrador administrador) {
        em.persist(administrador);
    }
    
    public void alterar(Administrador administrador) {
        em.merge(administrador);
    }
    
    public void deletar(Administrador administrador) {
        em.remove(administrador);
    }
    
    public List<Administrador> getAdministradores() {
        Query q = em.createQuery("select a from Administrador a order by a.nome");
        return q.getResultList();
    }
           
}
