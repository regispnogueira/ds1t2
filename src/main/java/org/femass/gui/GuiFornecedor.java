/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.femass.gui;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.femass.dao.BairroDao;
import org.femass.dao.FornecedorDao;
import org.femass.dao.UsuarioDao;
import org.femass.model.Bairro;
import org.femass.model.Fornecedor;
import org.femass.model.Usuario;
import static org.primefaces.behavior.validate.ClientValidator.PropertyKeys.event;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

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
    private UploadedFile file;
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
    
    @PostConstruct
    public void abrirTela(){
        fornecedor = (Fornecedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fornecedor");
    }
    
    public String gravar(){
        byte[] content = file.getContent();
        String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
        fornecedor.setFoto(resp);
        fornecedorDao.gravar(fornecedor);
        
        return "LstAnuncio";
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    
}
