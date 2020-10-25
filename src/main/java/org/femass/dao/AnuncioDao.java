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
import org.femass.model.Anuncio;



/**
 *
 * @author acg
 */
@Stateless
public class AnuncioDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Anuncio anuncio) {
        em.persist(anuncio);
    }
    
    public void alterar(Anuncio anuncio) {
        em.merge(anuncio);
    }
    
    public void deletar(Anuncio anuncio) {
        em.remove(anuncio);
    }
    
    public List<Anuncio> getAnuncios() {
        Query q = em.createQuery("select a from Anuncio a order by a.nome");
        return q.getResultList();
    }
           
}
