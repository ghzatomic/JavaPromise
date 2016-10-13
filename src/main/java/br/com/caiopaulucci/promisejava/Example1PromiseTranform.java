package br.com.caiopaulucci.promisejava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example1PromiseTranform {

	public static ExecutorService exec = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		
		CompletableFuture<String> retornaNome = CompletableFuture.supplyAsync(()->{
			return "OI SOU O RETORNO ASYNC :D";
		});
		
		retornaNome.thenApply(it->transform(it)).thenAccept(xx->{
			System.out.println(xx);
		});

		retornaNome.thenAccept(yy->{
			System.out.println("O Accept2 recebe : "+yy);
		});
		
		
		exec.execute(()->{
			//Execução sem a estrutura completa do promise : 
			try {
				retornaNome.get(); 
		    } catch (InterruptedException | ExecutionException ex) {
		        ex.printStackTrace();
		    }
		});
		
		exec.shutdown();
		
	}
	
	private static String transform(final String it){
		System.out.println("Antes de mudar era  : "+it);
		return "MUDOU !! :D";
	}
	
}
