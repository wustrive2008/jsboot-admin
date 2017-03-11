package com.wubaoguo.springboot.async;



import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wubaoguo.springboot.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AsyncTaskTest {
	
	@Autowired
	private AsyncTask asyncTask;
	
	@Test
	public void test() throws Exception {
		long start = System.currentTimeMillis();
		Future<String> task1 = asyncTask.doTaskOne();
		Future<String> task2 = asyncTask.doTaskTwo();
		Future<String> task3 = asyncTask.doTaskThree();
		while(true) {
			if(task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(2000);
		}
		long end = System.currentTimeMillis();
		System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
	}
}
