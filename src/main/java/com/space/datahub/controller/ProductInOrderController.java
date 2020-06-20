package com.space.datahub.controller;

import com.space.datahub.domain.ProductInOrder;
import com.space.datahub.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/productinorder")
public class ProductInOrderController {

    private final ProductInOrderService productInOrderService;
    private final OrderService orderService;
    private final BagService bagService;
    private final ProductService productService;
    private final ProductInBagService productInBagService;

    @Autowired
    public ProductInOrderController(ProductInOrderService productInOrderService, OrderService orderService, BagService bagService, ProductService productService, ProductInBagService productInBagService) {
        this.productInOrderService = productInOrderService;
        this.orderService = orderService;
        this.bagService = bagService;
        this.productService = productService;
        this.productInBagService = productInBagService;
    }

    @GetMapping
    public List<ProductInOrder> list(){
        return productInOrderService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ProductInOrder productInOrder){
        productInOrderService.delete(productInOrder);
    }

    @GetMapping("/order")
    public List<ProductInOrder> orders(@RequestParam String order){
        return productInOrderService.findByOrderNumber(order);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String bag, @RequestParam int length, @RequestParam String number){
        List<ProductInOrder> productsInOrder = new ArrayList<>();

        for(int i = 0; i < length; i++){
            ProductInOrder productInOrder = new ProductInOrder();
            productInOrder.setOrder(orderService.findByNumber(number));
            productInOrder.setProduct(productInBagService.findByBagName(bag).get(i).getProduct());

            productInOrderService.save(productInOrder);
            productsInOrder = productInOrderService.findByOrderNumber(number);
        }

        for(int i = 0; i < length; i++){
            productInBagService.delete(productInBagService.findByBagName(bag).get(0));
        }

        bagService.delete(bagService.findByName(bag));

        return new ResponseEntity<>(productsInOrder, HttpStatus.OK);
    }
}
