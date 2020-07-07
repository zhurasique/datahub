package com.space.datahub.service;

import com.space.datahub.domain.Order;
import com.space.datahub.domain.ProductInOrder;
import com.space.datahub.repo.ProductInOrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductInOrderService {
    private final ProductInOrderRepo productInOrderRepository;
    private final OrderService orderService;
    private final BagService bagService;
    private final ProductService productService;
    private final ProductInBagService productInBagService;

    public ProductInOrderService(ProductInOrderRepo productInOrderRepository, OrderService orderService, BagService bagService, ProductService productService, ProductInBagService productInBagService) {
        this.productInOrderRepository = productInOrderRepository;
        this.orderService = orderService;
        this.bagService = bagService;
        this.productService = productService;
        this.productInBagService = productInBagService;
    }

    public List<ProductInOrder> findByOrderNumber(String number){
        return productInOrderRepository.findByOrderNumber(number);
    }

    public List<ProductInOrder> findAll(){
        return productInOrderRepository.findAll();
    }

    public void delete(ProductInOrder productInOrder){
        productInOrderRepository.delete(productInOrder);
    }

    public List<ProductInOrder> user(@RequestParam String user){
        List<Order> listOrder = orderService.findByUserUsername(user);
        List<ProductInOrder> listProductInOrder = new ArrayList<>();
        for(int i = 0; i < listOrder.size(); i++){
            listProductInOrder.addAll(productInOrderRepository.findByOrderNumber(listOrder.get(i).getNumber()));
        }
        return listProductInOrder;
    }

    public List<ProductInOrder> save(String bag, int length, String number){
        List<ProductInOrder> productsInOrder = new ArrayList<>();

        for(int i = 0; i < length; i++){
            ProductInOrder productInOrder = new ProductInOrder();
            productInOrder.setOrder(orderService.findByNumber(number));
            productInOrder.setProduct(productInBagService.findByBagName(bag).get(i).getProduct());

            productInOrderRepository.save(productInOrder);
            productsInOrder = productInOrderRepository.findByOrderNumber(number);
        }

        for(int i = 0; i < length; i++){
            productInBagService.delete(productInBagService.findByBagName(bag).get(0));
        }

        bagService.delete(bagService.findByName(bag));

        return productsInOrder;
    }
}
