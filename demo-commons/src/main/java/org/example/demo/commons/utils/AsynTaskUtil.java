package org.example.demo.commons.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynTaskUtil {
	private static ExecutorService pool = Executors.newSingleThreadExecutor();
	public static void  submit(Runnable task) {
		pool.execute(task);
	}
}
