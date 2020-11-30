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
import org.femass.model.Bairro;



/**
 *
 * @author acg
 */
@Stateless
public class BairroDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Bairro bairro) {
        em.persist(bairro);
    }
    
    public void alterar(Bairro bairro) {
        em.merge(bairro);
    }
    
    public void deletar(Bairro bairro) {
        em.remove(em.merge(bairro));
    }
    
    public List<Bairro> getBairros() {
        Query q = em.createQuery("select b from Bairro b order by b.nome");
        return q.getResultList();
    }
           
}
