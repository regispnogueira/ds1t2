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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.femass.dao.AnuncioDao;
import org.femass.dao.FornecedorDao;
import org.femass.dao.SubcategoriaDao;
import org.femass.dao.UsuarioDao;
import org.femass.model.Anuncio;
import org.femass.model.Fornecedor;
import org.femass.model.Subcategoria;
import org.femass.model.Usuario;

/**
 *
 * @author Régis
 */
@Named(value = "guiAnuncio")
@SessionScoped
public class GuiAnuncio implements Serializable {

    private Boolean alterando;
    private Anuncio anuncio;
    private List<Anuncio> anuncios;
    private Fornecedor fornecedor;
    private List<Subcategoria> subcategorias;
    private Long idSubcategoria;
    private Long idUsuario;
    private Usuario usuario;
    @EJB
    private SubcategoriaDao subcategoriaDao;
    @EJB
    private AnuncioDao anuncioDao;
    @EJB
    private FornecedorDao fornecedorDao;
    @EJB
    private UsuarioDao usuarioDao;
        
    /**
     * Creates a new instance of GuiUsuario
     */
    public GuiAnuncio() {
       
    }

    public String cadastrar(){
        anuncio = new Anuncio();
        alterando = false;
        return "cadAnuncio";
    }
    
    public String alterar(Anuncio _anuncio){
        this.anuncio = _anuncio;
        alterando = true;
        return "cadAnuncio";
    }
    
    public String deletar(Anuncio _anuncio) {
        anuncioDao.deletar(_anuncio);
        return "LstAnuncio";
    }
    
    @PostConstruct
    public void abrirTela(){
        fornecedor = (Fornecedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fornecedor");
        anuncios = anuncioDao.getAnuncios();
        subcategorias = subcategoriaDao.getSubcategorias();
    }
    
    public String gravar(){
        anuncioDao.gravar(anuncio);
        
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

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Long idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }
    
    public List<Subcategoria> getSubcategorias(){
        subcategorias = subcategoriaDao.getSubcategorias();
        return subcategorias;
    }
     
}
