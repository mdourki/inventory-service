package com.jeetp.inventoryservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@RequiredArgsConstructor
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    private final RepositoryRestConfiguration restConfiguration;
    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        restConfiguration.exposeIdsFor(Product.class);
        return args -> {
            productRepository.save(new Product(null , "Ordinateur" , 788 , 12));
            productRepository.save(new Product(null , "Imprimante" , 78 , 128));
            productRepository.save(new Product(null , "Smartphone" , 88 , 172));
            productRepository.findAll().forEach(p -> {
                System.out.println(p.getName());
            });
        };
    }

}

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private double quantity;
}

@CrossOrigin("*")
@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product , Long> {

}
