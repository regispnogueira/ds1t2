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
import org.femass.model.Fornecedor;



/**
 *
 * @author acg
 */
@Stateless
public class FornecedorDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void gravar(Fornecedor fornecedor) {
        em.persist(fornecedor);
    }
    
    public void alterar(Fornecedor fornecedor) {
        em.merge(fornecedor);
    }
    
    public void deletar(Fornecedor fornecedor) {
        em.remove(fornecedor);
    }
    
    public List<Fornecedor> getFornecedores() {
        Query q = em.createQuery("select f from Fornecedor f order by f.nome");
        return q.getResultList();
    }
           
}
