package br.com.caiopaulucci.promisejava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example1Promise {

	public static ExecutorService exec = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		
		CompletableFuture<String> retornaNome = CompletableFuture.supplyAsync(()->{
			return "OI SOU O RETORNO ASYNC :D";
		});
		
		retornaNome.thenRun(()->{
			System.out.println("Rodando apos executar o async");
		});
		
		retornaNome.thenAccept(it->{
			System.out.println("Deps da execução ele recebeu1 : "+it);
		});
		
		retornaNome.thenAccept(it->{
			System.out.println("Deps da execução ele recebeu2 : "+it);
		});
		
		exec.execute(()->{
			retornaNome.complete("Completou com sucesso ! :D");
			
			//Execução sem a estrutura completa do promise : 
			/*try {
				retornaNome.get(); 
		    } catch (InterruptedException | ExecutionException ex) {
		        ex.printStackTrace();
		    }*/
		});
		
		exec.shutdown();
		
	}
	
}
