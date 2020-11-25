package com.instagram.services;

import com.sun.mail.iap.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@Service
public class FileDeletingService {

    public ResponseEntity<?> deleteFile(String fileName, String folderName) {
        try{
            Files.deleteIfExists(Paths.get("S:\\instagram\\Instagram\\Instagram\\src\\main\\resources\\images\\" + fileName));
            System.out.println("file deleted");
            return new ResponseEntity<>("File successfully deleted", HttpStatus.OK);
        }
        catch (NoSuchFileException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}
