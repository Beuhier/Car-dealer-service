package com.example.carservice;

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
public class CarServiceApplication {

	public static void main(String[] args) {SpringApplication.run(CarServiceApplication.class, args);}
	
	@Bean
	ApplicationRunner init(CarRepository repository) {
	  return args -> {
		  Stream.of("Ferrari","Maybach","Wagon","Honda","Toyota","Lexus","Benz").forEach(name ->{
			  repository.save(new Car(name));
		  });
		  repository.findAll().forEach(System.out::println);
		  };
	  }
}

@Data
@NoArgsConstructor
@Entity
class Car{
	
	public Car(@NonNull String carName) {
		super();
		this.carName = carName;
	}

	@Id
	@GeneratedValue
	private Long Id;
	
	@NonNull
	private String carName;
}

@RepositoryRestResource
interface CarRepository extends JpaRepository<Car, Long>{
	
}
