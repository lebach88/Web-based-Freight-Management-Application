package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.PackageRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.PackageDto;

import java.util.List;

public interface PackageService {
    List<PackageDto> findAllPackage();

    PackageDto addNewPackage(PackageRequest packageRequest);

    MessageApi deleteAllPackage();

    PackageDto getOnePackage(String packageId);

    PackageDto updateOnePackage(String packageId, PackageRequest packageRequest);

    MessageApi deleteOnePackage(String packageId);
}
