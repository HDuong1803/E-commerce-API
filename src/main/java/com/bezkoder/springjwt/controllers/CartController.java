package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.service.CartService;
import com.bezkoder.springjwt.utils.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<?> addProductToCart(Authentication authentication,
            @PathVariable("productId") Integer productId,
            @PathVariable("quantity") Integer quantity) {

        User user = (User) authentication.getPrincipal();

        Integer result = cartService.addProductToCart(productId, quantity, user.getId());

        return new ResponseEntity<MessageResponse>(
                new MessageResponse(result + " item(s) of this product were added to your shopping cart."),
                HttpStatus.OK);

    }

    @PutMapping("/update/{productId}/{quantity}")
    public ResponseEntity<?> updateQuantity(Authentication authentication, @PathVariable("productId") Integer productId,
            @PathVariable("quantity") Integer quantity) {

        User user = (User) authentication.getPrincipal();

        cartService.updateQuantity(productId, quantity, user.getId());

        MessageResponse msg = new MessageResponse("Updated quantity sucessfully");

        return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);

    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeCart(Authentication authentication, @PathVariable("productId") Integer productId) {

        User user = (User) authentication.getPrincipal();
        cartService.removeCart(productId, user.getId());

        MessageResponse msg = new MessageResponse("Deleted cart sucessfully");

        return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);

    }

}
