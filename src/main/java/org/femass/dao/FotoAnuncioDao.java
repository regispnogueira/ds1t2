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
import org.femass.model.FotoAnuncio;



/**
 *
 * @author acg
 */
@Stateless
public class FotoAnuncioDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(FotoAnuncio fotoanuncio) {
        em.persist(fotoanuncio);
    }
    
    public void alterar(FotoAnuncio fotoanuncio) {
        em.merge(fotoanuncio);
    }
    
    public void deletar(FotoAnuncio fotoanuncio) {
        em.remove(em.merge(fotoanuncio));
    }
    
    public List<FotoAnuncio> getFotoAnuncios() {
        Query q = em.createQuery("select f from FotoAnuncio f order by f.id");
        return q.getResultList();
    }
           
}
