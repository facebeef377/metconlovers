package com.metcon.metconlovers.upload;

import com.metcon.metconlovers.resetpassword.PasswordGenerator;
import com.tinify.Options;
import com.tinify.Tinify;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.metcon.metconlovers.resetpassword.PasswordGeneratorDict.ALPHA;
import static com.metcon.metconlovers.resetpassword.PasswordGeneratorDict.NUMERIC;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String time= String.valueOf(System.currentTimeMillis());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        fileName=time+'_'+PasswordGenerator.generatePassword(15,ALPHA+NUMERIC) +"."+extension;
        MultipartFile newfile=null;
        Options options = new Options()
                .with("method", "fit")
                .with("width", 400)
                .with("height", 400);
        byte[] resultData =null;
        try {
            resultData= Tinify.fromBuffer(file.getBytes()).resize(options).toBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        newfile=new MockMultipartFile(file.getName(),
                file.getOriginalFilename(), file.getContentType(), resultData);
        file=newfile;
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}