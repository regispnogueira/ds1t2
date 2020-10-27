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
import org.femass.model.Usuario;

/**
 *
 * @author Régis
 */
@Named(value = "guiUsuario")
@SessionScoped
public class GuiUsuario implements Serializable {

    private Boolean alterando;
    private Fornecedor fornecedor;
    private Usuario usuario;
    @EJB
    private FornecedorDao fornecedorDao;
    @EJB
    private UsuarioDao usuarioDao;
    
    /**
     * Creates a new instance of GuiUsuario
     */
    public GuiUsuario() {
    }

    public String cadastrar(){
        usuario = new Usuario();
        alterando = false;
        return "cadUsuario";
    }
    
    public String alterar(Usuario _usuario){
        this.usuario = _usuario;
        alterando = true;
        return "cadUsuario";
    }
    
    public String deletar(Usuario _usuario) {
        usuarioDao.deletar(_usuario);
        return "cadUsuario";
    }
    
    public String gravar(){
        if(!alterando){
            usuarioDao.gravar(usuario);
        } else {
            usuarioDao.alterar(usuario);
        }
        return "cadFornecedor";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
