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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.femass.dao.AnuncioDao;
import org.femass.dao.BairroDao;
import org.femass.dao.FornecedorDao;
import org.femass.dao.FotoAnuncioDao;
import org.femass.dao.SubcategoriaDao;
import org.femass.dao.UsuarioDao;
import org.femass.model.Anuncio;
import org.femass.model.Bairro;
import org.femass.model.Fornecedor;
import org.femass.model.FotoAnuncio;
import org.femass.model.Subcategoria;
import org.femass.model.TipoProduto;
import org.femass.model.Usuario;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Régis
 */
@Named(value = "guiAdm")
@SessionScoped
public class GuiAdm implements Serializable {

    private Boolean alterando;
    private String search;
        
    private Fornecedor fornecedor;
    private Usuario usuario;
    private Usuario _usuario;
    private Anuncio anuncio;
    private FotoAnuncio fotoanuncio;
    
    private Long idSubcategoria;
    private Long idUsuario;
    private Long idBairro;
    
    private UploadedFile filefornecedor;
    private UploadedFile fileanuncio;
    
    private TipoProduto[] tiposprodutos;
    private List<Anuncio> anuncios;
    private List<Anuncio> anunciosaprovados;
    private List<Anuncio> anunciosfornecedor;
    private List<Anuncio> anunciosbusca;
    private List<Subcategoria> subcategorias;
    private List<Usuario> usuarios;
    private static List<Bairro> bairros;
    
    @EJB
    private FornecedorDao fornecedorDao;
    @EJB
    private UsuarioDao usuarioDao;
    @EJB
    private BairroDao bairroDao;
    @EJB
    private SubcategoriaDao subcategoriaDao;
    @EJB
    private AnuncioDao anuncioDao;
    @EJB
    private FotoAnuncioDao fotoanuncioDao;
        
    /**
     * Creates a new instance of GuiUsuario
     */
    public GuiAdm() {
     
    }

    @PostConstruct
    public void abrirTela(){
        
        anuncios = anuncioDao.getAnuncios();
        anunciosaprovados = anuncioDao.getAnunciosAprovados();
        subcategorias = subcategoriaDao.getSubcategorias();
    }
    
    public String loginUsuario(){
        usuario = new Usuario();
        usuarios = usuarioDao.getUsuarios();
        return "Login";
    }
    
    public String logarUsuario(){
        usuario = usuarioDao.getUsuarioLogin(usuario.getEmail(), usuario.getSenha());
        if (usuario == null) {
            usuario = new Usuario();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!","Erro no Login!"));
            return null;
        } else {
            fornecedor = fornecedorDao.getFornecedorUsuario(usuario.getId());
            anunciosfornecedor = anuncioDao.getAnunciosFornecedor(usuario.getId());            
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fornecedor", fornecedor);
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
            return "LstAnuncio";
        }
    }  
     
    public String cadastrarUsuario(){
        usuario = new Usuario();
        usuarios = usuarioDao.getUsuarios();
        alterando = false;
        return "CadUsuario";
    }
    
    public String alterarUsuario(Usuario _usuario){
        this.usuario = _usuario;
        alterando = true;
        return "CadUsuario";
    }
    
    public String deletarUsuario(Usuario _usuario) {
        usuarioDao.deletar(_usuario);
        return "CadUsuario";
    }
    
    public String gravarUsuario(){
        _usuario = usuarioDao.getUsuario(usuario.getEmail());
        if (_usuario!=null){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário já cadastrado!","Erro no Cadastro!"));
            return "CadUsuario";
        } else {
            fornecedor = new Fornecedor();
            fornecedor.setUsuario(usuario);
            usuarioDao.gravar(usuario);
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fornecedor", fornecedor);
            return "CadFornecedor";
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
     public String cadastrarFornecedor(){
        fornecedor = new Fornecedor();
        alterando = false;
        bairros = bairroDao.getBairros();
        return "cadFornecedor";
    }
    
    public String alterarFornecedor(Fornecedor _fornecedor){
        this.fornecedor = _fornecedor;
        this.idUsuario = fornecedor.getUsuario().getId();
        alterando = true;
        return "cadFornecedor";
    }
    
    public String deletarFornecedor(Fornecedor _fornecedor) {
        usuarioDao.deletar(_fornecedor.getUsuario());
        fornecedorDao.deletar(_fornecedor);
        return "cadFornecedor";
    }
    
    public String gravarFornecedor(){
        byte[] content = filefornecedor.getContent();
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

    public UploadedFile getFileFornecedor() {
        return filefornecedor;
    }

    public void setFileFornecedor(UploadedFile file) {
        this.filefornecedor = file;
    }
    
    public String cadastrarAnuncio(){
        //fornecedor = (Fornecedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fornecedor");
        //usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        anuncio = new Anuncio();
        anuncio.setFornecedor(fornecedor);
        return "CadAnuncio";
    }
    
    public String alterarAnuncio(Anuncio _anuncio){
        this.anuncio = _anuncio;
        alterando = true;
        return "cadAnuncio";
    }
    
    public String deletarAnuncio(Anuncio _anuncio) {
        anuncioDao.deletar(_anuncio);
        return "LstAnuncio";
    }
    
    public String gravarAnuncio(){
        byte[] content = fileanuncio.getContent();
        String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
        fotoanuncio.setFoto(resp);
        fotoanuncioDao.gravar(fotoanuncio);
        anuncio.salvarFoto(fotoanuncio);
        anuncioDao.gravar(anuncio);
        anunciosfornecedor = anuncioDao.getAnunciosFornecedor(usuario.getId()); 
        
        return "LstAnuncio";
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
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

    public List<Anuncio> getAnunciosbusca() {
        return anunciosbusca;
    }

    public void setAnunciosbusca(List<Anuncio> anunciosbusca) {
        this.anunciosbusca = anunciosbusca;
    }
    
    public String buscarAnuncio(){
        anunciosbusca = anuncioDao.getAnunciosBusca(search);
        return "LstAnuncioBusca";
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    
    public String visualizarAnuncio(Anuncio _anuncio){
        anuncio = _anuncio;
                
        return "ViewAnuncio";
    }
    
    public UploadedFile getFileAnuncio() {
        return fileanuncio;
    }

    public void setFileAnuncio(UploadedFile file) {
        this.fileanuncio = file;
    }
    
    public void fileUploadListener(FileUploadEvent e) {
        this.fileanuncio = e.getFile();
    }
}
