package com.santander.springbootwebflux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.springbootwebflux.model.Producto;
import com.santander.springbootwebflux.repository.ProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoRestController.class);

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public Flux<Producto> index() {
        Flux<Producto> productos = productoRepository.findAll()
                .map(producto -> {
                    producto.setNombre(producto.getNombre().toUpperCase());
                    return producto;
                }).doOnNext(prod -> logger.info(prod.getNombre()));
        return productos;
    }

    @GetMapping("/{id}")
    public Mono<Producto> show(@PathVariable String id) {
        // Mono<Producto> producto = productoRepository.findById(id);
        Flux<Producto> productos = productoRepository.findAll();
        Mono<Producto> producto = productos.filter(p -> p.getId().equals(id)).next()
                .doOnNext(prod -> logger.info(prod.getNombre()));
        return producto;
    }

}
