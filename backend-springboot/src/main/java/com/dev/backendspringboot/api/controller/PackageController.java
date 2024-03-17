package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.request.PackageRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.PackageDto;
import com.dev.backendspringboot.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package")
public class PackageController {
    private PackageService packageService;
    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }
    @GetMapping("/")
    public ResponseEntity<List<PackageDto>> findAllPackage(){
        return ResponseEntity.ok(packageService.findAllPackage());
    }
    @PostMapping("/")
    public ResponseEntity<PackageDto> createNewPackage(@RequestBody PackageRequest packageRequest){
        return ResponseEntity.ok(packageService.addNewPackage(packageRequest));
    }
    @DeleteMapping("/")
    public ResponseEntity<MessageApi> deleteAllPackage(){
        return ResponseEntity.ok(packageService.deleteAllPackage());
    }
    @GetMapping("/{packageId}")
    public ResponseEntity<PackageDto> getOnePackage(@PathVariable String packageId){
        return ResponseEntity.ok(packageService.getOnePackage(packageId));
    }
    @PutMapping("/{packageId}")
    public ResponseEntity<PackageDto> updateOnePackage(@RequestBody PackageRequest packageRequest, @PathVariable String packageId){
        return ResponseEntity.ok(packageService.updateOnePackage(packageId,packageRequest));
    }
    @DeleteMapping("/{packageId}")
    public ResponseEntity<MessageApi> deleteOnePackage(@PathVariable String packageId){
        return ResponseEntity.ok(packageService.deleteOnePackage(packageId));
    }
}
