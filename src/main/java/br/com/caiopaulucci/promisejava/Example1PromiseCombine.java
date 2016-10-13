package br.com.caiopaulucci.promisejava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example1PromiseCombine {

	public static ExecutorService exec = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		
		CompletableFuture<String> retornaNome1 = CompletableFuture.supplyAsync(()->{
			System.out.println("Aqui 1");
			return "retorno 1";
		});

		CompletableFuture<String> retornaNome2 = CompletableFuture.supplyAsync(()->{
			System.out.println("Aqui 2");
			return "retorno 2";
		});
		
		CompletableFuture<String> retornaNome3 = CompletableFuture.supplyAsync(()->{
			System.out.println("Aqui 3");
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "retorno 3";
		});
		
		retornaNome1.thenCombine(retornaNome2, (it,xx)->{
			System.out.println("it : "+it);
			System.out.println("xx : "+xx);
			return "";
		});
		
		try {
			retornaNome3.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		retornaNome1.allOf(retornaNome1, retornaNome2,retornaNome3).thenAccept(it->{
			System.out.println("EXECUTOU TUDO !! :D ");
		});
	}
	
}
