package com.santander.springbootwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.santander.springbootwebflux.model.Producto;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

}
