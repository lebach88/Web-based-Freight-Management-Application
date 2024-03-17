package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.request.PackageRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.PackageDto;
import com.dev.backendspringboot.api.error.PackageNotFoundException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import com.dev.backendspringboot.document.Package;
import com.dev.backendspringboot.document.Product;
import com.dev.backendspringboot.repository.PackageRepository;
import com.dev.backendspringboot.service.PackageService;
import com.dev.backendspringboot.service.util.PackageUtil;
import io.jsonwebtoken.lang.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {
    public static final LocalDateTime LOCAL_DATE_TIME = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
    private PackageRepository packageRepository;
    private PackageUtil packageUtil;
    @Autowired
    public PackageServiceImpl(PackageRepository packageRepository, PackageUtil packageUtil) {
        this.packageRepository = packageRepository;
        this.packageUtil = packageUtil;
    }

    @Override
    public List<PackageDto> findAllPackage() {
        try {
            List<Package> packages = packageRepository.findAll();
            return packages.stream().map(packageItem -> packageUtil.mapPackageToPackageDto(packageItem)).collect(Collectors.toList());
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy danh sách package  ", ex);
        }
    }

    @Override
    public PackageDto addNewPackage(PackageRequest packageRequest) {
        try {
            List<Product> products = packageUtil.extraProducts(packageRequest);
            Package newPackage = Package.builder()
                    .name(packageRequest.getName())
                    .type(packageRequest.getType())
                    .createdAt(LOCAL_DATE_TIME).products(products)
                    .build();
            return packageUtil.mapPackageToPackageDto(packageRepository.save(newPackage));
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi thêm package ", ex);
        }
    }

    @Override
    public MessageApi deleteAllPackage() {
        try {
            packageRepository.deleteAll();
            return MessageApi.builder().message("deleted all successfully").timestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))).build();
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa package", ex);
        }
    }

    @Override
    public PackageDto getOnePackage(String packageId) {
        try {
            Package packageItem = packageRepository.findById(packageId).orElseThrow(() -> new PackageNotFoundException("Package not found"));
            return packageUtil.mapPackageToPackageDto(packageItem);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy 1 package ", ex);
        }
    }

    @Override
    public PackageDto updateOnePackage(String packageId, PackageRequest packageRequest) {
        try {
            Package packageItem = packageRepository.findById(packageId).orElseThrow(() -> new PackageNotFoundException("Package not found"));
            packageItem.setName(packageRequest.getName());
            packageItem.setType(packageRequest.getType());
            packageItem.setProducts(packageUtil.extraProducts(packageRequest));
            packageItem.setUpdatedOn(LOCAL_DATE_TIME);
            Package updatePackage = packageRepository.save(packageItem);
            return packageUtil.mapPackageToPackageDto(updatePackage);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi cập nhật package ", ex);
        }
    }

    @Override
    public MessageApi deleteOnePackage(String packageId) {
        try {
            if (!Objects.isEmpty(new PackageDto[]{getOnePackage(packageId)})){
                packageRepository.deleteById(packageId);
                return MessageApi.builder().message("deleted successfully").timestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))).build();
            }
            return null;
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa 1 package ", ex);
        }
    }
}
