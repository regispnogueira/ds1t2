/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.femass.gui;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import org.femass.dao.FornecedorDao;
import org.femass.dao.UsuarioDao;
import org.femass.model.Fornecedor;

/**
 *
 * @author Régis
 */
@Named(value = "guiUsuario")
@SessionScoped
public class GuiFornecedor implements Serializable {

    private Boolean alterando;
    private Fornecedor fornecedor;
    @EJB
    private FornecedorDao fornecedorDao;
    @EJB
    private UsuarioDao usuarioDao;
    private Long idUsuario;
    
    
    /**
     * Creates a new instance of GuiUsuario
     */
    public GuiFornecedor() {
    }

    public String cadastrar(){
        fornecedor = new Fornecedor();
        alterando = false;
        return "cadFornecedor";
    }
    
    public String alterar(Fornecedor _fornecedor){
        this.fornecedor = _fornecedor;
        this.idUsuario = fornecedor.getUsuario().getId();
        alterando = true;
        return "cadFornecedor";
    }
    
    public String deletar(Fornecedor _fornecedor) {
        usuarioDao.deletar(_fornecedor.getUsuario());
        fornecedorDao.deletar(_fornecedor);
        return "cadFornecedor";
    }
    
    public String gravar(){
        if(!alterando){
            usuarioDao.gravar(fornecedor.getUsuario());
            fornecedorDao.gravar(fornecedor);
        } else {
            usuarioDao.alterar(fornecedor.getUsuario());
            fornecedorDao.alterar(fornecedor);
        }
        return "cadFornecedor";
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}
