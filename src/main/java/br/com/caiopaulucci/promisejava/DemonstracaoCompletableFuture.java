package br.com.caiopaulucci.promisejava;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemonstracaoCompletableFuture {

	static ExecutorService executor = Executors.newFixedThreadPool(5);

	public static void main(String... args) throws InterruptedException {
		CompletableFuture
        .supplyAsync(()->getValorInteiro())//Executa
        .thenApply(i -> String.valueOf(i))//Transforma para String
        .exceptionally(ex -> {
            System.out.println("Erro = " + ex.getMessage());
            return "Erro";
        }).thenAccept(str -> System.out.println("String = " + str));//Exibe
		executor.shutdown();
	}
	
	private static void thenComposeExemplo() {
		final CompletableFuture<Integer> c1 =  CompletableFuture
                .supplyAsync(()->getValorInteiro())//Executa
                .thenCompose(i -> CompletableFuture.supplyAsync(()->i*2));//Executa a composicao
        
        c1.exceptionally(ex -> {
            System.out.println("Erro = " + ex.getMessage());
            return -1;
        }).thenAccept(str -> System.out.println("String = " + str));//Exibe
	}
	
	private static void thenApplyExemplo() {
		final CompletableFuture<String> c1 =  CompletableFuture
                .supplyAsync(()->getValorInteiro())//Executa
                .thenApply(i -> String.valueOf(i));//Transforma para String
        
        c1.exceptionally(ex -> {
            System.out.println("Erro = " + ex.getMessage());
            return "Erro";
        }).thenAccept(str -> System.out.println("String = " + str));
	}
	
	public static int getValorInteiro() {
        Integer i = new Random().nextInt(1000);
        System.out.println("valor original = " + i);
        return i;
    }

	private static void exemploRunAsync() {
		final CompletableFuture<String> c1 = new CompletableFuture<>();
		CompletableFuture.runAsync(() -> {
			try {
				c1.complete(getValor());
			} catch (InterruptedException ex) {
				c1.completeExceptionally(ex);
			}
		},executor);
		c1.exceptionally(ex -> {
			System.out.println("Erro = " + ex.getMessage());
			return "Erro";
		}).thenAccept(str -> System.out.println("Ok = " + str));
	}

	public static String getValor() throws InterruptedException {
		if (new Random().nextInt(2) == 0) {
			throw new InterruptedException("Erro");
		} else {
			return "OK";
		}
	}

	private static void executaSuplyAsync() {
		final CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> "OK");
		c1.handle((content, ex) -> {
			if (ex == null) {
				System.out.println("Conteudo = " + content);
			} else {
				ex.printStackTrace();
			}
			return "";
		});
	}

	private static void executaCompletableFutureComHandle() {
		CompletableFuture<String> c1 = new CompletableFuture<>();
		executor.execute(() -> { /* Lambda 4tw ! */
			try {
				if (new Random().nextInt(2) == 0) {
					throw new InterruptedException("Excecao");
				} else {
					c1.complete("OK");
				}
			} catch (Exception ex) {
				c1.completeExceptionally(ex);
			}
		});
		c1.handle((content, ex) -> {
			if (ex == null) {
				System.out.println("Conteudo = " + content);
			} else {
				ex.printStackTrace();
			}
			return "";
		});
		c1.exceptionally(ex -> {
			System.out.println("Erro = " + ex.getMessage());
			return "Deu ruim :(";
		});
		c1.thenAccept(str -> System.out.println(str));
	}

	public void executaCompletableFutureException() {
		CompletableFuture<String> c1 = new CompletableFuture<>();
		executor.execute(() -> { /* Lambda 4tw ! */
			try {
				throw new InterruptedException("Excecao");
			} catch (Exception ex) {
				c1.completeExceptionally(ex);
			}
		});
		c1.exceptionally(ex -> {
			System.out.println("Erro = " + ex.getMessage());
			return "Deu ruim :(";
		});
		c1.thenAccept(str -> System.out.println(str));
	}

	public void executaCompletableFuture1() {
		CompletableFuture<String> c1 = new CompletableFuture<>();
		executor.execute(() -> { /* Lambda 4tw ! */
			c1.complete("Ola Mundo");
		});
		c1.thenAccept(str -> System.out.println(str));
	}
}
