package br.com.caiopaulucci.promisejava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example2PromiseReject {

	public static ExecutorService exec = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {

		CompletableFuture<String> retornaNome = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executando");
			return "OI SOU O RETORNO ASYNC :D";
		}).exceptionally(ex->log(ex));

		retornaNome.thenRun(() -> {
			System.out.println("Rodando apos executar o async");
		});

		retornaNome.thenAccept(it -> {
			System.out.println("Deps da execução ele recebeu1 : " + it);
		});

		retornaNome.thenAccept(it -> {
			System.out.println("Deps da execução ele recebeu2 : " + it);
		});

		retornaNome.exceptionally(ex->log(ex));
		
		exec.execute(() -> {
			retornaNome.completeExceptionally(new Exception("Promise rejected"));
		});

		// exec.shutdown();

	}

	private static String log(Throwable ex) {
		ex.printStackTrace();
		return "HUE";
	}

}
