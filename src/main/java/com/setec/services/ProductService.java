package com.setec.services;

import com.setec.models.Product;
import com.setec.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductService {
    @Value("${file.upload-dir}")
    private String uploadFolder;
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    public List<Product> getProductAll() {
        var products = productRepository.findAll();
        return products;
    }
    public String copyFile(MultipartFile file) throws IOException {
        Path uploadPath= Paths.get(uploadFolder);
        if(Files.notExists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path targetPath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(),targetPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    public void createItem(Product product, MultipartFile file) throws IOException {
        if(file!=null && !file.isEmpty()){
            product.setPhoto(this.copyFile(file));
        }
        this.productRepository.save(product);
    }
    @Transactional
    public void deleteById(int id) throws IOException {
        var product=this.productRepository.findProductById(id);
        if(product!=null){
            if(product.getPhoto()!=null && !product.getPhoto().isEmpty()) {
                Path uploadPath = Paths.get(uploadFolder, product.getPhoto());
                Files.deleteIfExists(uploadPath);
            }
        }
        productRepository.deleteById(product.getId());
    }

    public Product editById(int id) {
        var product=this.productRepository.findProductById(id);
        return product;
    }
    @Transactional
    public Product updateById(int id, Product product, MultipartFile file) throws IOException {
        var productExisting=this.productRepository.findProductById(id);
        if(productExisting!=null){
            productExisting.setName(product.getName());
            productExisting.setBarcode(product.getBarcode());
            productExisting.setSellprice(product.getSellprice());
            productExisting.setUnitinstock(product.getUnitinstock());
            productExisting.setCategory(product.getCategory());
            if(productExisting.getPhoto()!=null && !productExisting.getPhoto().isEmpty()){
                if(file!=null && !file.isEmpty()){
                    Path uploadPath= Paths.get(uploadFolder,productExisting.getPhoto());
                    Files.deleteIfExists(uploadPath);
                    productExisting.setPhoto(this.copyFile(file));
                }
            }else{
                if(file!=null && !file.isEmpty()){
                    productExisting.setPhoto(this.copyFile(file));
                }
            }
        }
        this.productRepository.save(productExisting);
        return productExisting;
    }

    public List<Product> searchByName(String search) {
        var products=this.productRepository.findProductByName(search);
        return products;
    }

    public Page<Product> paginated(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
        var products=this.productRepository.findAll(pageable);
        return products;
    }
}
