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
import org.femass.dao.AnuncioDao;
import org.femass.dao.FornecedorDao;
import org.femass.dao.SubcategoriaDao;
import org.femass.dao.UsuarioDao;
import org.femass.model.Anuncio;
import org.femass.model.Fornecedor;
import org.femass.model.Subcategoria;
import org.femass.model.TipoProduto;
import org.femass.model.Usuario;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Régis
 */
@Named(value = "guiAnuncio")
@SessionScoped
public class GuiAnuncio implements Serializable {

    private Boolean alterando;
    private Anuncio anuncio;
    private TipoProduto[] tiposprodutos;
    private List<Anuncio> anuncios;
    private List<Anuncio> anunciosaprovados;
    private List<Anuncio> anunciosfornecedor;
    private Fornecedor fornecedor;
    private List<Subcategoria> subcategorias;
    private Long idSubcategoria;
    private Long idUsuario;
    private Usuario usuario;
    private UploadedFile file;
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
        anuncio.setFornecedor(fornecedor);
        return "CadAnuncio";
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
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(!(usuario==null))
            anunciosfornecedor = anuncioDao.getAnunciosFornecedor(usuario.getId());
        anuncios = anuncioDao.getAnuncios();
        anunciosaprovados = anuncioDao.getAnunciosAprovados();
        subcategorias = subcategoriaDao.getSubcategorias();
    }
    
    public String gravar(){
        byte[] content = file.getContent();
        String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
        anuncio.setFoto(resp);
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

    public List<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public List<Anuncio> getAnunciosfornecedor() {
        return anunciosfornecedor;
    }

    public void setAnunciosfornecedor(List<Anuncio> anunciosfornecedor) {
        this.anunciosfornecedor = anunciosfornecedor;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Anuncio> getAnunciosaprovados() {
        return anunciosaprovados;
    }

    public void setAnunciosaprovados(List<Anuncio> anunciosaprovados) {
        this.anunciosaprovados = anunciosaprovados;
    }

    public TipoProduto[] getTiposprodutos() {
        return TipoProduto.values();
    }

    public void setTiposprodutos(TipoProduto[] tiposprodutos) {
        this.tiposprodutos = tiposprodutos;
    }

    
     
    
}
