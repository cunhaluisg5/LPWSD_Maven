/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.model.Livro;
import br.cesjf.lpwsd.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class arquivoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    //Controle de arquivo
    private List<File> files = new ArrayList<>();
    private UploadedFile uploadedFile;
    private StreamedContent streamedContent;

    //Id do livro
    private int idBook;

    //Método para fazer upload
    public void upload(String name, String folder) {
        try {
            File arquivo = FileUtil.write(name, uploadedFile.getContents(), folder);

            files.add(arquivo);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Upload completo", "O arquivo " + arquivo.getName() + " foi salvo!"));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
        }
    }

    //Método para fazer download
    public void download(Livro book) throws IOException {
        try {
            File file = new File("C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\resources\\files\\" + book.getId() + ".pdf");
            InputStream inputStream = new FileInputStream(file);

            streamedContent = new DefaultStreamedContent(inputStream, Files.probeContentType(file.toPath()), book.getTitulo() + ".pdf");
        } catch (FileNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Que pena!", "Não existe a versão digital deste livro"));
        }
    }
    
    //Remove os arquivos dos diretórios
    public void remove(Livro book){
        try{
            new File("C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\resources\\files\\" + book.getId() + ".pdf").delete();
            new File("C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\resources\\bookImages\\" + book.getId() + ".png").delete();
        }catch(Exception e){
            System.out.println("Erro ao apagar arquivos! " + e);
        }
    }

    //Getters and Setters
    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
}
