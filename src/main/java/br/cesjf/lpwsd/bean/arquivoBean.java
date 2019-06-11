/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.model.Livro;
import java.io.File;
import java.io.FileOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class arquivoBean {
    public static Livro livro;
    
    public void fileUploadAction(FileUploadEvent event) {
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
           
            String realPath = context.getRealPath("/");
 
            // Aqui cria o diretorio caso não exista
            File file = new File("C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\files");
            file.mkdirs();
            
            byte[] arquivo = event.getFile().getContents();
            String caminho = "C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\files\\" + livro.getIsbn() + event.getFile().getContentType().replace("application/", ".");
      
            // Esse trecho grava o arquivo no diretório
            FileOutputStream fos = new FileOutputStream(caminho);
            fos.write(arquivo);
            fos.close();
            
            aFacesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload", "Arquivo gravado com sucesso!"));
            

        } catch (Exception ex) {
            System.out.println("Erro no upload de imagem" + ex);
            aFacesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload", "Erro ao gravar arquivo!"));
        }
    }
}
