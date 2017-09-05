package com.santhosh.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveFileService{

	private final Path location = Paths.get("filesFolder");

	public File getStorageLocation(){
		return this.location.toFile();
	}
	
	public boolean saveFile(MultipartFile file) {
		if(file.isEmpty()){
			return false;
		}
		try {
            Files.copy(file.getInputStream(), this.location.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
        	return false;
        }
		return true;
	}
	
	 public Resource getFile(String filename) {
	        try {
	            Path file = location.resolve(filename);
	            Resource resource = new UrlResource(file.toUri());
	            if(resource.exists() || resource.isReadable()) {
	                return resource;
	            }else{
	            	throw new RuntimeException("failed to fetch file");
	            }
	        } catch (MalformedURLException e) {
	        	throw new RuntimeException("FAIL");
	        }
	    }

	 public void deleteAll() {
	        FileSystemUtils.deleteRecursively(location.toFile());
	    }
	 
	 public void init() {
	        try {
	        	if(!location.toFile().exists())
	            Files.createDirectory(location);
	        } catch (IOException e) {
	            throw new RuntimeException("Could not initialize storage!");
	        }
	    }
}
