package org.esfinge.virtuallab.web.op;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.web.IJsonRequestHandler;
import org.esfinge.virtuallab.web.JsonReturn;

/**
 * Trata as requisicoes de upload de novas classes/jar.
 */
public class UploadFileHandler implements IJsonRequestHandler {

    public JsonReturn handleAsync(HttpServletRequest request) {
        var jsonReturn = new JsonReturn();

        try {
            var fileName = this.saveFile(request);
            jsonReturn.setSuccess(true);
            jsonReturn.setMessage("Arquivo '" + fileName + "' carregado com sucesso!");
        } catch (Exception e) {
            // TODO: debug..
            e.printStackTrace();

            jsonReturn.setSuccess(false);
            jsonReturn.setMessage("Erro: " + e.toString());
        }

        return jsonReturn;
    }

    /**
     * Processa a requisicao e salva o arquivo de upload se o mesmo for valido.
     */
    private String saveFile(HttpServletRequest request) throws Exception {
        var utils = Utils.getInstance();
        // Create a factory for disk-based file items
        var diskFactory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        diskFactory.setSizeThreshold(1024 * 1024 * utils.getPropertyAsInt("upload.memory_threshold", 3));
        diskFactory.setRepository(FileUtils.getTempDirectory());

        // Create a new file upload handler
        var fileUpload = new ServletFileUpload(diskFactory);
        fileUpload.setFileSizeMax(1024 * 1024 * utils.getPropertyAsInt("upload.max_file_size", 40));
        fileUpload.setSizeMax(1024 * 1024 * utils.getPropertyAsInt("upload.max_request_size", 50));

        // Parse the request
        var files = fileUpload.parseRequest(request);
        if (files.size() > 0) {
            // Process a file upload
            var item = files.get(0);

            if (!item.isFormField()) {
                // obtem o nome do arquivo
                var fileName = FilenameUtils.getName(item.getName());

                // tenta salvar o arquivo
                PersistenceService.getInstance().saveUploadedFile(item.getInputStream(), fileName);

                // retorna o nome do arquivo salvo
                return fileName;
            }

            throw new Exception("Arquivo '" + item.getName() + "' não é uma classe/jar válido!");
        }

        throw new Exception("Nenhum arquivo compatível encontrado!");
    }
}
