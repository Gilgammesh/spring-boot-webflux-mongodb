package com.santander.springbootwebflux;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.santander.springbootwebflux.model.Producto;
import com.santander.springbootwebflux.repository.ProductoRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	@Autowired
	private ProductoRepository productoRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.just(
				new Producto("TV Panasonic Pantalla LCD", 456.99),
				new Producto("Sony Camara HD Digital", 177.99),
				new Producto("Apple iPod", 46.99),
				new Producto("Sony Notebook", 846.99),
				new Producto("Hewlett Packard Multifuncional", 200.00),
				new Producto("Bianchi Bicicleta", 70.99),
				new Producto("HP Notebook Omen 17", 2500.99),
				new Producto("Mica Cómoda 5 Cajone", 150.99),
				new Producto("TV Sony Bravia OLED 4k", 2255.99))
				.flatMap(producto -> {
					producto.setCreatedAt(new Date());
					return productoRepository.save(producto);
				})
				.subscribe(producto -> {
					StringBuilder sb = new StringBuilder();
					sb.append("Se inserto el producto: ");
					sb.append(producto.getId());
					sb.append(" ");
					sb.append(producto.getNombre());
					logger.info(sb.toString());
				});
	}

}
