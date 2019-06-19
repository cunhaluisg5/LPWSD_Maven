/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.model.Livro;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class arquivoBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Livro livro;
    private UploadedFile uploadedFile;
    private final String diretorio;

    public arquivoBean() {
        this.diretorio = "C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\resources\\files\\";
    }
    
    public void fileUploadAction() {
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
           
            String realPath = context.getRealPath("/");
 
            // Aqui cria o diretorio caso não exista
            File file = new File(diretorio);
            file.mkdirs();
            byte[] arquivo = uploadedFile.getContents();
            String caminho = diretorio + livro.getIsbn() + uploadedFile.getContentType().replace("application/", ".");
      
            // Esse trecho grava o arquivo no diretório
            FileOutputStream fos = new FileOutputStream(caminho);
            fos.write(arquivo);
            fos.close();
            
            aFacesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload", "Arquivo gravado com sucesso!"));
            

        } catch (Exception ex) {
            System.out.println("Erro: " + ex);
            aFacesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload", "Erro ao gravar arquivo!"));
        }
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
