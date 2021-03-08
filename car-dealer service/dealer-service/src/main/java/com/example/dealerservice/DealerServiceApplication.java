package com.example.dealerservice;

import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EnableEurekaClient
@SpringBootApplication
public class DealerServiceApplication {

	public static void main(String[] args) {SpringApplication.run(DealerServiceApplication.class, args);}
	
	@Bean
	ApplicationRunner init(DealerRepository dealerRepo) {
		return args -> {
			Stream.of("Bra Bus","MDX","ACURA","4Matic","Accord").forEach(name -> {
				dealerRepo.save(new Dealer(name));
			});
			dealerRepo.findAll().forEach(System.out::println);
		};
	}
}

@Data
@Entity
@NoArgsConstructor
class Dealer {
	
	@Id
	@GeneratedValue
	private Long Id;
	
	@NonNull
	private String dealerName;

	public Dealer(@NonNull String dealerName) {
		super();
		this.dealerName = dealerName;
	}
}

@RepositoryRestResource
interface DealerRepository extends JpaRepository<Dealer, Long> {
	
}
