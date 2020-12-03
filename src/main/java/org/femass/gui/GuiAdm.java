/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.femass.gui;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDate;
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
import org.femass.model.TipoUsuario;
import org.femass.model.Usuario;
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
    private FotoAnuncio fotoanuncio2;
    private FotoAnuncio fotoanuncio3;
    private FotoAnuncio fotoanuncio4;
    private FotoAnuncio fotoanuncio5;
    private FotoAnuncio fotoanuncio6;
    
    private Long idSubcategoria;
    private Long idUsuario;
    private Long idBairro;
    private Long idAnuncio;
    
    private UploadedFile filefornecedor;
    private UploadedFile fileanuncio;
    private UploadedFile fileanuncio2;
    private UploadedFile fileanuncio3;
    private UploadedFile fileanuncio4;
    private UploadedFile fileanuncio5;
    private UploadedFile fileanuncio6;
    
    
    private TipoProduto[] tiposprodutos;
    private List<Anuncio> anuncios;
    private List<Anuncio> anunciosaprovados;
    private List<Anuncio> anunciosfornecedor;
    private List<Anuncio> anunciosbusca;
    private List<Anuncio> anunciosbusca_;
    private List<Anuncio> anunciosaprovar;
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
        anunciosaprovar = anuncioDao.getAnunciosAprovar();
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
        } else if(usuario.getTipousuario()==usuario.getTipousuario().FORNECEDOR){
            fornecedor = fornecedorDao.getFornecedorUsuario(usuario.getId());
            anunciosfornecedor = anuncioDao.getAnunciosFornecedor(usuario.getId());            
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fornecedor", fornecedor);
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
            return "LstAnuncio";
        }else if(usuario.getTipousuario()==usuario.getTipousuario().ADMINISTRADOR){
            anunciosaprovar = anuncioDao.getAnunciosAprovar();
            return "LstAnuncioAdm";
        } else
            return null;
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
            usuarioDao.gravar(usuario);
            fornecedor = new Fornecedor();
            fornecedor.setUsuario(usuario);
            usuario.setTipousuario(TipoUsuario.FORNECEDOR);
            usuarioDao.alterar(usuario);
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
        for (Bairro b: bairros){
            if(b.getId().equals(idBairro)){
                fornecedor.setBairro(b);
            }
        }
        fornecedorDao.gravar(fornecedor);
        idBairro = null;
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

    public String cadastrarAnuncio(){
        //fornecedor = (Fornecedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fornecedor");
        //usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        anuncio = new Anuncio();
        anuncio.setFornecedor(fornecedor);
        return "CadAnuncio";
    }
    
    public String aprovarAnuncio(Anuncio _anuncio){
        _anuncio.setDataaprovacao(LocalDate.now());
        _anuncio.setAprovacao(usuario);
        anuncioDao.alterar(_anuncio);
        anunciosaprovar = anuncioDao.getAnunciosAprovar();
        anunciosaprovados = anuncioDao.getAnunciosAprovados();
        return "LstAnuncioAdm";
    }
    
    public String rejeitarAnuncio (Anuncio _anuncio){
        _anuncio.setDataaprovacao(null);
        _anuncio.setAprovacao(null);
        anuncioDao.alterar(_anuncio);
        anunciosaprovar = anuncioDao.getAnunciosAprovar();
        anunciosaprovados = anuncioDao.getAnunciosAprovados();
        return "LstAnuncioAdm";
    }
    
    public String alterarAnuncio(Anuncio _anuncio){
        this.anuncio = _anuncio;
        alterando = true;
        return "cadAnuncio";
    }
    
    public String deletarAnuncio(Anuncio _anuncio) {
        anuncioDao.deletar(_anuncio);
        anunciosfornecedor = anuncioDao.getAnunciosFornecedor(usuario.getId()); 
        anuncios = anuncioDao.getAnuncios();
        anunciosaprovar = anuncioDao.getAnunciosAprovar();
        anunciosaprovados = anuncioDao.getAnunciosAprovados();
        return this.meusAnuncios();
    }
    
    public String gravarAnuncio(){
        anuncioDao.gravar(anuncio);
        
        if(!(fileanuncio.getContent()==null)){
            fotoanuncio = new FotoAnuncio();
            fotoanuncioDao.gravar(fotoanuncio);
            byte[] content = fileanuncio.getContent();
            String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
            fotoanuncio.setFoto(resp);
            anuncio.salvarFoto(fotoanuncio);
            fotoanuncioDao.alterar(fotoanuncio);
        }
        if(!(fileanuncio2.getContent()==null)){
            fotoanuncio2 = new FotoAnuncio();
            fotoanuncioDao.gravar(fotoanuncio2);
            byte[] content = fileanuncio2.getContent();
            String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
            fotoanuncio2.setFoto(resp);
            anuncio.salvarFoto(fotoanuncio2);
            fotoanuncioDao.alterar(fotoanuncio2);
        }
        if(!(fileanuncio3.getContent()==null)){
            fotoanuncio3 = new FotoAnuncio();
            fotoanuncioDao.gravar(fotoanuncio3);
            byte[] content = fileanuncio3.getContent();
            String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
            fotoanuncio3.setFoto(resp);
            anuncio.salvarFoto(fotoanuncio3);
            fotoanuncioDao.alterar(fotoanuncio3);
        }
        if(!(fileanuncio4.getContent()==null)){
            fotoanuncio4 = new FotoAnuncio();
            fotoanuncioDao.gravar(fotoanuncio4);
            byte[] content = fileanuncio4.getContent();
            String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
            fotoanuncio4.setFoto(resp);
            anuncio.salvarFoto(fotoanuncio4);
            fotoanuncioDao.alterar(fotoanuncio4);
        }
        if(!(fileanuncio5.getContent()==null)){
            fotoanuncio5 = new FotoAnuncio();
            fotoanuncioDao.gravar(fotoanuncio5);
            byte[] content = fileanuncio5.getContent();
            String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
            fotoanuncio5.setFoto(resp);
            anuncio.salvarFoto(fotoanuncio5);
            fotoanuncioDao.alterar(fotoanuncio5);
        }
        if(!(fileanuncio6.getContent()==null)){
            fotoanuncio6 = new FotoAnuncio();
            fotoanuncioDao.gravar(fotoanuncio6);
            byte[] content = fileanuncio6.getContent();
            String resp = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(content); 
            fotoanuncio6.setFoto(resp);
            anuncio.salvarFoto(fotoanuncio6);
            fotoanuncioDao.alterar(fotoanuncio6);
        }
        anuncioDao.alterar(anuncio);
        anunciosfornecedor = anuncioDao.getAnunciosFornecedor(usuario.getId()); 
        anuncios = anuncioDao.getAnuncios();
        return "LstAnuncio";
    }
    
    public String meusAnuncios(){
        if (!(usuario==null)){
            if(usuario.getTipousuario()==usuario.getTipousuario().FORNECEDOR)
                return "LstAnuncio";
            if(usuario.getTipousuario()==usuario.getTipousuario().ADMINISTRADOR)
                return "LstAnuncioAdm";
        } else {
            return this.loginUsuario();
        }
        return null;
    }
    
   public String sairUsuario(){
        usuario = null;
        return "index";
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

    public List<Anuncio> getAnunciosaprovar() {
        return anunciosaprovar;
    }

    public void setAnunciosaprovar(List<Anuncio> anunciosaprovar) {
        this.anunciosaprovar = anunciosaprovar;
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
        if(idBairro==null){
            anunciosbusca = anuncioDao.getAnunciosBusca(search);
        } else {
            anunciosbusca.clear();
            anunciosbusca_ = anuncioDao.getAnunciosBusca(search);
            for(Anuncio a: anunciosbusca_){
                if(a.getFornecedor().getBairro().getId().equals(idBairro)){
                        anunciosbusca.add(a);
                }
            }
        }
        
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
    
    public UploadedFile getFilefornecedor() {
        return filefornecedor;
    }

    public void setFilefornecedor(UploadedFile filefornecedor) {
        this.filefornecedor = filefornecedor;
    }

    public UploadedFile getFileanuncio() {
        return fileanuncio;
    }

    public void setFileanuncio(UploadedFile fileanuncio) {
        this.fileanuncio = fileanuncio;
    }

    public UploadedFile getFileanuncio2() {
        return fileanuncio2;
    }

    public void setFileanuncio2(UploadedFile fileanuncio2) {
        this.fileanuncio2 = fileanuncio2;
    }

    public UploadedFile getFileanuncio3() {
        return fileanuncio3;
    }

    public void setFileanuncio3(UploadedFile fileanuncio3) {
        this.fileanuncio3 = fileanuncio3;
    }

    public UploadedFile getFileanuncio4() {
        return fileanuncio4;
    }

    public void setFileanuncio4(UploadedFile fileanuncio4) {
        this.fileanuncio4 = fileanuncio4;
    }

    public UploadedFile getFileanuncio5() {
        return fileanuncio5;
    }

    public void setFileanuncio5(UploadedFile fileanuncio5) {
        this.fileanuncio5 = fileanuncio5;
    }

    public UploadedFile getFileanuncio6() {
        return fileanuncio6;
    }

    public void setFileanuncio6(UploadedFile fileanuncio6) {
        this.fileanuncio6 = fileanuncio6;
    }
    
    
}
