package miranda.gabriel.tenus.adapters.outbounds.cloudstorage;

import java.io.InputStream;

public interface CloudStoragePort {
    
    String uploadFile(String fileName, InputStream fileContent, String contentType, long fileSize, String folderPath);
    
    boolean deleteFile(String fileUrl);
    
    boolean fileExists(String fileUrl);
}