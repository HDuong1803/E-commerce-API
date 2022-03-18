package com.bezkoder.springjwt.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.entity.Product;
import com.bezkoder.springjwt.entity.ProductSize;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.model.dto.ProductDto;
import com.bezkoder.springjwt.model.request.CreateProductReq;
import com.bezkoder.springjwt.model.request.UpdateSizeCountReq;
import com.bezkoder.springjwt.service.ProductService;
import com.bezkoder.springjwt.service.UserService;
import com.bezkoder.springjwt.utils.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") Integer id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    // @PreAuthorize("hasRole('ROLE_SALER')")
    // @PutMapping("{id}")
    // public ResponseEntity<?> updateProduct(@PathVariable(name = "id") Integer id,
    // @RequestBody CreateProductReq req) {

    // productService.updateProduct(id, req);
    // return ResponseEntity.ok("Cập nhật thành công");

    // }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {

        productService.delete(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "productId") String sortBy) {

        List<Product> products = productService.getAllProduct(pageNo, pageSize, sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateProductReq req) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User user = userService.findByUsername(username);

        productService.create(req, user);
        return new ResponseEntity<MessageResponse>(new MessageResponse("Sucesss"), HttpStatus.OK);

    }

    @GetMapping("/search/priceRange")
    public ResponseEntity<List<ProductDto>> getProductByPriceRange(
            @RequestParam(name = "startPrice", defaultValue = "0") float startPrice,
            @RequestParam("endPrice") float endPrice,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "productId") String sortBy) {

        List<Product> products = productService.getAllProductByRangePrice(startPrice,
                endPrice, pageNo, pageSize,
                sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ProductDto>> getProductByName(@RequestParam("productName") String productName,

            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "productId") String sortBy) {

        List<Product> products = productService.searchProductByName(productName,
                pageNo, pageSize, sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable("categoryId") int categoryId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "productId") String sortBy) {

        List<Product> products = productService.getAllProductByCategory(categoryId,
                pageNo, pageSize, sortBy);
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/top12")
    public ResponseEntity<List<ProductDto>> getTop12Product() {

        List<Product> products = productService.getTop12Product();
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/newProduct")
    public ResponseEntity<List<ProductDto>> getListNewProduct() {

        List<Product> products = productService.getListNewProduct();
        List<ProductDto> dtos = products.stream().map(Product::toDto).collect(Collectors.toList());

        return new ResponseEntity<List<ProductDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/countAllProduct")
    public ResponseEntity<?> countAllProduct() {

        Integer amount = productService.countAllProduct();

        return new ResponseEntity<Integer>(amount, HttpStatus.OK);

    }

    @GetMapping("/countAllProductOfCategory/{categoryId}")
    public ResponseEntity<?> countAllProductOfCategory(@PathVariable("categoryId") int categoryId) {

        Integer amount = productService.countAllProductOfCategory(categoryId);

        return new ResponseEntity<Integer>(amount, HttpStatus.OK);

    }

    @PutMapping("/products/update-size-count")
    public ResponseEntity<?> updateSizeCount(@RequestBody UpdateSizeCountReq req) {
        productService.updateSizeCount(req);

        return ResponseEntity.ok("Cập nhật thành công");
    }

}
