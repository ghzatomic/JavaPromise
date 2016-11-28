package br.com.caiopaulucci.promisejava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DemonstracaoFuture {

	static ExecutorService executor = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long tempoIni = System.currentTimeMillis();
		executaAssincronoCallable();
		System.out.println("Demorou : "+(System.currentTimeMillis()-tempoIni));
		executor.shutdown();
	}
	
	public static void executaAssincronoCallable() throws InterruptedException, ExecutionException{
		List<Future<String>> listaDeFuturasExecucoes = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			final int indice = i;
			listaDeFuturasExecucoes.add(executor.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					return executaFuncao(indice);
				}
			}));
		}
		for (Future<String> future : listaDeFuturasExecucoes) {
			System.out.println(future.get());
		}
	}
	
	public static void executaSincrono(){
		for (int i = 0; i < 5; i++) {
			System.out.println(executaFuncao(i));
		}
	}
	
	public static void executaAssincronoThreadExec(){
		for (int i = 0; i < 5; i++) {
			final int indice = i;
			executor.execute(new Runnable() {
				public void run() {
					System.out.println(executaFuncao(indice));
				}
			});
		}
	}
	
	public static Callable<String> getExemploCallable1(){
		return new Callable<String>() {
			public String call() throws Exception {
				Thread.sleep(5000);//5 Seg
				return "Terminei !";
			}
		};
	}
	
	public static void executaCallable1() throws InterruptedException, ExecutionException{
		Future<String> futureExecution = executor.submit(getExemploCallable1());
		System.out.println(futureExecution.get());
	}
	
	public static Runnable getExemploRunnable1(){
		return new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);//5 Seg
					System.out.println("Terminei !");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static void executaRunnable1(){
		executor.execute(getExemploRunnable1());
	}
	
	public static String executaFuncao(int indice){
		try {
			System.out.println("Startou "+indice);
			Thread.sleep(2000);//2 segundos
			return "Finalizou "+indice;
		} catch (InterruptedException e) {
			return "Erro : "+indice;
		}
	}
	
}
