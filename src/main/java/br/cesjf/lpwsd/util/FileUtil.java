/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author luisg
 */
public class FileUtil {
    //Método para gravação de arquivo
public static File write(String name, byte[] contents, String folder) throws IOException {
        File file = new File(rootFiles(folder), name);

        OutputStream out = new FileOutputStream(file);
        out.write(contents);
        out.close();

        return file;
    }

    //Método para listagem de arquivos
    public static List<File> list(String folder) {
        File dir = rootFiles(folder);

        return Arrays.asList(dir.listFiles());
    }

    //Caminho raiz de arquivo
    public static java.io.File rootFiles(String folder) {
        File dir;
        if ("files".equals(folder)) {
            dir = new File(root(), "files");
        } else {
            dir = new File(root(), "bookImages");
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    //Caminho do arquivo
    public static File root() {
        File dir = new File("C:\\Users\\luisg\\Desktop\\LPWSD\\src\\main\\webapp\\resources\\");
        
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }
}
