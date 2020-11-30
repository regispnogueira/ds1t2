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
import org.femass.model.Categoria;



/**
 *
 * @author acg
 */
@Stateless
public class CategoriaDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Categoria categoria) {
        em.persist(categoria);
    }
    
    public void alterar(Categoria categoria) {
        em.merge(categoria);
    }
    
    public void deletar(Categoria categoria) {
        em.remove(em.merge(categoria));
    }
    
    public List<Categoria> getCategorias() {
        Query q = em.createQuery("select c from Categoria c order by c.nome");
        return q.getResultList();
    }
           
}
