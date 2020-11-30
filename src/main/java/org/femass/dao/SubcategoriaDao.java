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
import org.femass.model.Subcategoria;



/**
 *
 * @author acg
 */
@Stateless
public class SubcategoriaDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Subcategoria subcategoria) {
        em.persist(subcategoria);
    }
    
    public void alterar(Subcategoria subcategoria) {
        em.merge(subcategoria);
    }
    
    public void deletar(Subcategoria subcategoria) {
        em.remove(em.merge(subcategoria));
    }
    
    public List<Subcategoria> getSubcategorias() {
        Query q = em.createQuery("select s from Subcategoria s order by s.nome");
        return q.getResultList();
    }
           
}
