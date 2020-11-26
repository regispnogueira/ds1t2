/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.femass.gui;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private Usuario _usuario;
    private List<Usuario> usuarios;
    @EJB
    private FornecedorDao fornecedorDao;
    @EJB
    private UsuarioDao usuarioDao;
    
    /**
     * Creates a new instance of GuiUsuario
     */
    public GuiUsuario() {
     
    }

    public String login(){
        usuario = new Usuario();
        usuarios = usuarioDao.getUsuarios();
        return "Login";
    }
    
    public String logar(){
        usuario = usuarioDao.getUsuarioLogin(usuario.getEmail(), usuario.getSenha());
        if (usuario == null) {
            usuario = new Usuario();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!","Erro no Login!"));
            return null;
        } else {
            fornecedor = fornecedorDao.getFornecedorUsuario(usuario.getId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fornecedor", fornecedor);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
            return "LstAnuncio";
        }
    }  
     
    
    public String cadastrar(){
        usuario = new Usuario();
        usuarios = usuarioDao.getUsuarios();
        alterando = false;
        return "CadUsuario";
    }
    
    public String alterar(Usuario _usuario){
        this.usuario = _usuario;
        alterando = true;
        return "CadUsuario";
    }
    
    public String deletar(Usuario _usuario) {
        usuarioDao.deletar(_usuario);
        return "CadUsuario";
    }
    
    public String gravar(){
        _usuario = usuarioDao.getUsuario(usuario.getEmail());
        if (_usuario!=null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário já cadastrado!","Erro no Cadastro!"));
            return "CadUsuario";
        } else {
            fornecedor = new Fornecedor();
            fornecedor.setUsuario(usuario);
            usuarioDao.gravar(usuario);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fornecedor", fornecedor);
            return "CadFornecedor";
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
}
