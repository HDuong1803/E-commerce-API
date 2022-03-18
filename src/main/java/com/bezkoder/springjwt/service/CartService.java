package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.entity.Cart;
import com.bezkoder.springjwt.entity.Product;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.repository.CartRepository;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Integer addProductToCart(Integer productId, Integer quantity, Integer customerId) {

        Product product = productRepository.findById(productId).get();
        User user = userRepository.findById(customerId).get();

        Cart cart = cartRepository.findByProductAndUser(productId, customerId);

        if (cart != null) {
            Integer updateQuantity = cart.getQuantity() + quantity;

            cart.setQuantity(updateQuantity);
        } else {
            cart = new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQuantity(quantity);
        }

        float amount = cart.getQuantity() * product.getPrice();
        cart.setAmount(amount);
        cartRepository.save(cart);

        return quantity;

    }

    public void updateQuantity(Integer productId, Integer quantity, Integer customerId) {

        cartRepository.updateQuantity(quantity, productId, customerId);
    }

    public void removeCart(Integer productId, Integer customerId) {
        cartRepository.removeCart(productId, customerId);
    }

}
