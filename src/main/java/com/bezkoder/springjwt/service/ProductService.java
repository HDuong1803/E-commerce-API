package com.bezkoder.springjwt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bezkoder.springjwt.entity.Category;
import com.bezkoder.springjwt.entity.Product;
import com.bezkoder.springjwt.entity.ProductSize;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.exception.BadRequestException;
import com.bezkoder.springjwt.exception.NotFoundException;
import com.bezkoder.springjwt.model.request.CreateProductReq;
import com.bezkoder.springjwt.model.request.UpdateSizeCountReq;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.ProductSizeRepository;
import com.github.slugify.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.bezkoder.springjwt.utils.Constant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private CategoryService categoryService;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void create(CreateProductReq req, User user) {

        Category category = categoryService.getById(req.getCategoryId());
        Slugify slg = new Slugify();
        String slug = slg.slugify(req.getName());
        List<ProductSize> sizes = new ArrayList<>();

        ProductSize ps = productSizeRepository.findBySize(req.getSize());

        sizes.add(ps);

        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .detail(req.getDetail())
                .price(req.getPrice())
                .slug(slug)
                .created_at(new Date())
                .count_buy(0)
                .category(category)
                .user(user)
                .sizes(sizes)
                .build();

        save(product);

    }

    // public void updateProduct(Integer id, CreateProductReq req) {

    // Product product = productRepository.findById(id).get();

    // Product result = ProductMapper.toProduct(req);
    // result.setId(id);
    // productRepository.save(result);
    // }

    public void delete(int productId) {
        productRepository.deleteById(productId);
    }

    public Product getById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> getAllProduct(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Product> pageResult = productRepository.findAll(pageable);

        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new ArrayList<Product>();
        }

    }

    public List<Product> getAllProductByRangePrice(float startPrice, float endPrice, Integer pageNo, Integer pageSize,
            String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Product> result = productRepository.getAllProductByRangePrice(startPrice, endPrice, pageable);

        if (result.hasContent()) {
            return result.getContent();
        } else {
            return new ArrayList<Product>();
        }
    }

    public List<Product> searchProductByName(String keyword, Integer pageNo,
            Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> result = productRepository.searchProductByName(keyword,
                pageable);

        if (result.hasContent()) {
            return result.getContent();
        } else {
            return new ArrayList<Product>();
        }
    }

    public List<Product> getAllProductByCategory(int categoryId, Integer pageNo,
            Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> result = productRepository.getProductByCategory(categoryId,
                pageable);

        if (result.hasContent()) {
            return result.getContent();
        } else {
            return new ArrayList<Product>();
        }

    }

    public List<Product> getTop12Product() {
        return productRepository.getTop12Product();
    }

    public List<Product> getListProductByUser(Integer userId) {
        return productRepository.getListProductByUser(userId);
    }

    public List<Product> getListProductByCategoryOfShop(Integer catId, Integer shopId) {
        return productRepository.getListProductByCategoryOfShop(catId, shopId);
    }

    public void increaseCountBuyProduct(Integer productId, Integer quantity) {
        productRepository.increaseCountBuyProduct(productId, quantity);
    }

    public void reduceQuantityProduct(Integer productId, Integer number) {
        productRepository.reduceQuantityProduct(productId, number);
    }

    public List<Product> getListNewProduct() {
        return productRepository.getListNewProduct();
    }

    public int countAllProduct() {
        return productRepository.countAllProduct();
    }

    public int countAllProductOfCategory(Integer categoryId) {
        return productRepository.countAllProductOfCategory(categoryId);
    }

    public void updateSizeCount(UpdateSizeCountReq req) {

        boolean isValid = false;

        for (int size : SIZE_VN) {

            if (size == req.getSize()) {
                isValid = true;
            }

        }

        if (isValid == false) {
            throw new BadRequestException("Size khong hop le");
        }

        ProductSize ps = productSizeRepository.findBySize(req.getSize());

        if (ps == null) {
            ProductSize productSize = ProductSize.builder()
                    .size(req.getSize())
                    .quantity(req.getCount())
                    .build();

            productSizeRepository.save(productSize);
        } else {
            ps.setQuantity(req.getCount());
            productSizeRepository.save(ps);
        }

    }

}
