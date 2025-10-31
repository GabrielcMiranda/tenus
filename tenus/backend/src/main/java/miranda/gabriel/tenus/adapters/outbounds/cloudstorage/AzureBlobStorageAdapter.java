package miranda.gabriel.tenus.adapters.outbounds.cloudstorage;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.PublicAccessType;

import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Slf4j
@Component
public class AzureBlobStorageAdapter implements CloudStoragePort {
    
    private final BlobServiceClient blobServiceClient;
    private final String containerName;
    
    public AzureBlobStorageAdapter(@Value("${azure.storage.connection-string}") String connectionString,
        @Value("${azure.storage.container-name:tenus-images}") String containerName) {
        
        log.info("Initializing Azure Blob Storage Adapter...");
        log.info("Container name: {}", containerName);
        
        this.containerName = containerName;
        
        try {
            this.blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
            
            log.info("Azure Blob Service Client created successfully");
            initializeContainer();
            
        } catch (Exception e) {
            log.error("ERRO ao conectar com Azure Blob Storage: {}", e.getMessage());
            log.error("Connection String (primeiros 50 chars): {}", 
                     connectionString.substring(0, Math.min(50, connectionString.length())));
            throw new RuntimeException("Failed to initialize Azure Blob Storage: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String uploadFile(String fileName, InputStream fileContent, String contentType, long fileSize, String folderPath) {
        try {
            
            String uniqueFileName = generateUniqueFileName(fileName, folderPath);
            
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(uniqueFileName);
            
            BlobHttpHeaders headers = new BlobHttpHeaders()
                    .setContentType(contentType)
                    .setCacheControl("public, max-age=31536000");
            
            blobClient.upload(fileContent, fileSize, true); 
            
     
            blobClient.setHttpHeaders(headers);
            
            String fileUrl = blobClient.getBlobUrl();
            log.info("File uploaded successfully to Azure Blob Storage: {}", fileUrl);
            
            return fileUrl;
            
        } catch (Exception e) {
            log.error("Error uploading file to Azure Blob Storage: {}", e.getMessage(), e);
            throw new TenusExceptions.BusinessRuleViolationException("Failed to upload image: " + e.getMessage());
        }
    }
    
    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String blobName = extractBlobNameFromUrl(fileUrl);
            
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            
            boolean deleted = blobClient.deleteIfExists();
            
            if (deleted) {
                log.info("File deleted successfully from Azure Blob Storage: {}", fileUrl);
            } else {
                log.warn("File not found for deletion in Azure Blob Storage: {}", fileUrl);
            }
            
            return deleted;
            
        } catch (Exception e) {
            log.error("Error deleting file from Azure Blob Storage: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean fileExists(String fileUrl) {
        try {
            String blobName = extractBlobNameFromUrl(fileUrl);
            
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            
            return blobClient.exists();
            
        } catch (Exception e) {
            log.error("Error checking file existence in Azure Blob Storage: {}", e.getMessage(), e);
            return false;
        }
    }
    
    private void initializeContainer() {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            
            if (!containerClient.exists()) {
                containerClient.createWithResponse(null, PublicAccessType.BLOB, null, null);
                log.info("Azure Blob Storage container created: {}", containerName);
            } else {
                log.info("Azure Blob Storage container already exists: {}", containerName);
            }
            
        } catch (Exception e) {
            log.error("Error initializing Azure Blob Storage container: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Azure Blob Storage", e);
        }
    }
    
    private String generateUniqueFileName(String originalFileName, String folderPath) {
        String fileExtension = "";
        
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        
        // Estrutura: user-{userId}/{entityType}/{uuid}.jpg
        return folderPath + "/" + UUID.randomUUID().toString() + fileExtension;
    }
    
    private String extractBlobNameFromUrl(String fileUrl) {
    
        try {
            String[] urlParts = fileUrl.split("/");
            
            int containerIndex = -1;
            for (int i = 0; i < urlParts.length; i++) {
                if (urlParts[i].equals(containerName)) {
                    containerIndex = i;
                    break;
                }
            }
            
            if (containerIndex != -1 && containerIndex < urlParts.length - 1) {
                StringBuilder blobName = new StringBuilder();
                for (int i = containerIndex + 1; i < urlParts.length; i++) {
                    if (i > containerIndex + 1) {
                        blobName.append("/");
                    }
                    blobName.append(urlParts[i]);
                }
                return blobName.toString();
            }
            
            if (urlParts.length >= 3) {
                return urlParts[urlParts.length - 3] + "/" + 
                       urlParts[urlParts.length - 2] + "/" + 
                       urlParts[urlParts.length - 1];
            }
            
            if (urlParts.length >= 2) {
                return urlParts[urlParts.length - 2] + "/" + urlParts[urlParts.length - 1];
            }
            
            return urlParts[urlParts.length - 1];
            
        } catch (Exception e) {
            log.warn("Could not extract blob name from URL: {}", fileUrl);
            return fileUrl;
        }
    }
}