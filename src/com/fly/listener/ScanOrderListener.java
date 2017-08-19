package com.fly.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fly.service.OrderService;
import com.fly.service.Impl.OrderServiceImpl;

public class ScanOrderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				OrderService os = new OrderServiceImpl();
				os.scan();
			}
		}, 1000, 1000 * 3600 * 2);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
