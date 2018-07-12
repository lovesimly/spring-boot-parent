package com.lovesimly.mcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.fastjson.TypeReference;
import com.lovesimly.mcache.core.MCache;
import com.lovesimly.mcache.core.McacheBuilder;


@SpringBootApplication
public class McacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(McacheApplication.class, args);
		
		try {
			MCache<String> cache2 = McacheBuilder.newBuilder("test2" // 命名，不同本地缓存必须不一样
					, new TypeReference<String>() {
					}) // 泛型类型
					.expireAfterWrite(3) // 本地缓存过期时间(秒)，默认远程缓存过期*2
					.maximumSize(3).useRemote() // 本地缓存最大key数量，默认16个
					.build(); // 生成缓存实例
			
			Thread.sleep(1000); // 本地缓存扩散需要少量时间

			cache2.set("name_newkey", new String("songhui"));
			
			for (int i = 0; i < 10; i++) {
				System.out.println(cache2.get("name_newkey"));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
