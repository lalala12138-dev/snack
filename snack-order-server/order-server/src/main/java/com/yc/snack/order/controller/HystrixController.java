package com.yc.snack.order.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@DefaultProperties(defaultFallback = "defaultfallback")
public class HystrixController {
	//第一种方式直接写对方服务器的ip地址
	//@HystrixCommand(fallbackMethod = "fallback") //降级之后哦要调用的方法
	/*@HystrixCommand(commandProperties = {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000") //默认是一秒
	})*/
	@GetMapping("/list1")
	public String list() {
		RestTemplate restTemplate=new RestTemplate();
		String response=restTemplate.postForObject("http://127.0.0.1:6644/product/listForGno", Arrays.asList("1","2","3"), String.class);
		return response;
	}
	
	@HystrixCommand
	@GetMapping("/list2")
	public String list2() {
		RestTemplate restTemplate=new RestTemplate();
		String response=restTemplate.postForObject("http://127.0.0.1:6644/product/listForGnos", Arrays.asList("1","2","3"), String.class);
		return response;
	}
	
	@GetMapping("/list3")
	@HystrixCommand(commandProperties = {
			@HystrixProperty(name="circuitBreaker.enabled",value="true"), //是否允许熔断
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"), //设置在滚动时间窗口中，短路器的最小请求数
			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"), //窗口期
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60") //设置短路器打开的错误百分比，比如10次请求中有7次错误，那么短路器就会被设置为打开状态
	})
	public String list3(Integer num) {
		if(num==1) {
			return "success";
		}
		RestTemplate restTemplate=new RestTemplate();
		String response=restTemplate.postForObject("http://127.0.0.1:6644/product/listForGno", Arrays.asList("1","2","3"), String.class);
		return response;
	}
	
	public String fallback() {
		return "哎呀服务器被挤爆了，等一下在来吧...";
	}
	
	public String defaultfallback() {
		return "不好意思，网络开小差了，请求走丢了...";
	}
	
	/*
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	//第二种方式，先从注册中心通过服务器名获取一个服务器地址，然后在访问
	@GetMapping("/list2")
	public String list2() {
		ServiceInstance serviceInstance=loadBalancerClient.choose("product-server");
		String url=String.format("http://%s:%s/%s", serviceInstance.getHost(),serviceInstance.getPort(),"/product/listForGno");
		RestTemplate restTemplate=new RestTemplate();
		String response=restTemplate.postForObject(url, Arrays.asList("1","2","3"), String.class);
		return response;
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/list3")
	public String list3() {
		String response=restTemplate.postForObject("http://product-server/product/listForGno", Arrays.asList("1","2","3"), String.class);
		return response;
	}*/
		
}
