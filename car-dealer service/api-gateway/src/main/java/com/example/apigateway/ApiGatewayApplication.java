package com.example.apigateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.Data;
import lombok.NonNull;
//eureka.instance.instanceId=${spring.application.name}:${random.int}
@EnableFeignClients
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {
	public static void main(String[] args) {SpringApplication.run(ApiGatewayApplication.class, args);}
}

@Data
class Car {
	private String carName;
	
	public String getName() {
		return carName;
	}
}

@Data
class Dealer{
	private String dealerName;
	
	public String getName() {
		return dealerName;
	}
}

@FeignClient("car-service")
interface CarClient{
	@GetMapping("/cars")
	@CrossOrigin
	CollectionModel<Car> readCars();
}

@FeignClient("dealer-service")
interface DealerClient{
	@GetMapping("/dealers")
	@CrossOrigin
	CollectionModel<Dealer> readDealers();
}

@RestController
class CarController{
	private final CarClient carClient;
	
	public CarController(CarClient carClient) {
		super();
		this.carClient = carClient;
	}
	
	public Collection<Car> Carfallback(){
		return new ArrayList<>();
	}
	
	@HystrixCommand(fallbackMethod = "Carfallback")
	@CrossOrigin
	@GetMapping("/cool-cars")
	public Collection<Car> coolCars(){
		printCar(carClient.readCars().getContent());
		
		return carClient.readCars()
			.getContent()
			.stream()
			.filter(this::isCool)
			.collect(Collectors.toList());
	}
	
	private boolean isCool(Car car) {
		return !car.getName().equals("Honda")&&
				!car.getName().equals("Toyota");
	}
	
	private void printCar(Collection<Car> car) {
		System.out.println(car.toString());
	}
}

@RestController
class DealerController {
	private final DealerClient dealerClient;

	public DealerController(DealerClient dealerClient) {
		super();
		this.dealerClient = dealerClient;
	}
	
	public Collection<Dealer> Dealerfallback(){
		return new ArrayList<>();
	}
	
	@HystrixCommand(fallbackMethod ="Dealerfallback")
	@CrossOrigin
	@GetMapping("/fake-dealers")
	public Collection<Dealer> fakeDealers(){
		return dealerClient.readDealers()
				.getContent()
				.stream()
				.filter(this::isFakeDealers)
				.collect(Collectors.toList());
	}
	
	private boolean isFakeDealers(Dealer dealer) {
		return !dealer.getName().equals("Accord") &&
				!dealer.getName().equals("MDX");
	}
}

