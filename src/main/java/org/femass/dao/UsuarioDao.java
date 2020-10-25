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
import org.femass.model.Usuario;



/**
 *
 * @author acg
 */
@Stateless
public class UsuarioDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Usuario usuario) {
        em.persist(usuario);
    }
    
    public void alterar(Usuario usuario) {
        em.merge(usuario);
    }
    
    public void deletar(Usuario usuario) {
        em.remove(usuario);
    }
    
    public List<Usuario> getUsuarios() {
        Query q = em.createQuery("select u from Usuario u order by u.email");
        return q.getResultList();
    }
           
}
