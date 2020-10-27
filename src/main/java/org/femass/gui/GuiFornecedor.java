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
import org.femass.dao.BairroDao;
import org.femass.dao.FornecedorDao;
import org.femass.dao.UsuarioDao;
import org.femass.model.Bairro;
import org.femass.model.Fornecedor;
import org.femass.model.Usuario;

/**
 *
 * @author Régis
 */
@Named(value = "guiFornecedor")
@SessionScoped
public class GuiFornecedor implements Serializable {

    private Boolean alterando;
    private Fornecedor fornecedor;
    private Long idUsuario;
    private static List<Bairro> bairros;
    private Long idBairro;
    private Usuario usuario;
    @EJB
    private FornecedorDao fornecedorDao;
    @EJB
    private UsuarioDao usuarioDao;
    @EJB
    private BairroDao bairroDao;
    
    /**
     * Creates a new instance of GuiUsuario
     */
    public GuiFornecedor() {
       
    }

    public String cadastrar(){
        fornecedor = new Fornecedor();
        alterando = false;
        bairros = bairroDao.getBairros();
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
        fornecedor.setUsuario(usuario);
        fornecedorDao.gravar(fornecedor);
        
        return "cadUsuario";
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

    public List<Bairro> getBairros() {
        bairros = bairroDao.getBairros();
        return bairros;
    }

    public void setBairros(List<Bairro> bairros) {
        this.bairros = bairros;
    }

    public Long getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Long idBairro) {
        this.idBairro = idBairro;
    }
    
}
