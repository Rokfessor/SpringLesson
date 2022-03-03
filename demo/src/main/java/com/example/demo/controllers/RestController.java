package com.example.demo.controllers;

import com.example.demo.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.image.BufferedImage;
import java.io.IOException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @PostMapping("/process_image")
    public ResponseEntity<ProcessImageResponse> processImage(@RequestBody ProcessImageRequest processImageRequest,
                                                            @RequestParam(name = "effect") EffectType effectType) {
        ProcessImageResponse response = new ProcessImageResponse();
        try {
            BufferedImage image = Utils.base64ToImage(processImageRequest.image);
            BufferedImage result;
            int originalWidth = image.getWidth(),
                originalHeight = image.getHeight();

            switch (effectType) {
                case Monochrome: {
                    result = Monochrome.processImage(image);
                    break;
                }
                case SeamCarving: {
                    result = SeamCarving.processImage(image, processImageRequest.width, processImageRequest.height);
                    if (processImageRequest.saveScale)
                        result = Utils.resizeImage(result, originalWidth, originalHeight);
                    break;
                } default:
                    return ResponseEntity.badRequest().body(null);
            }
            response.image = Utils.imageToBase64(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
