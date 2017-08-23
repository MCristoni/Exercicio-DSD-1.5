import java.util.ArrayList;
import java.util.Random;

public class Main {

	private static ArrayList<Integer> lista;
	private static int posicao = -1;
	
	public static void main(String[] args) {
		//Inicializa uma lista e preenche ela
		preencherLista();
		//Passa o número 17 pra ser procurado dentro da lista acima
		buscarInteiro(17);
	}

	private static void buscarInteiro(int pesq) {
		//Cria um objeto que implementa a Interface Runnable
		//Params: 
		//	Lista criada acima
		//	Número a ser pesquisado
		//	Sequência em que a lista vai ser percorrida (true -> início ao fim)
		//	Variável que vai receber a posição do número pesquisado naquela lista
		Thread_Pesq tPesq = new Thread_Pesq(lista, pesq, true, posicao);
		//Igual ao acima, com a diferença da ordem de pesquisa
		Thread_Pesq tPesq2 = new Thread_Pesq(lista, pesq, false, posicao);
		try {
			//Cria uma nova thread passando o objeto Runnable criado acima
			Thread t1 = new Thread(tPesq);
			//Cria uma nova thread passando o objeto Runnable criado acima
			Thread t2 = new Thread(tPesq2);
			
			//Seta a thread necessária presente em tPesq.
			//Pois tPesq rodará na 1a Thread(t1), e caso ele encontre o resultado antes da Thread2(t2),
			//é necessário que ele pare a Thread2, então precisamos passar t2 para tPesq a partir de um setter.
			//No construtor new Thread_Pesq(lista, pesq, true, posicao) não seria possível,
			//pois as Threads ainda não haviam sido criadas
			tPesq.setThread(t2);
			//Faz o mesmo que o acima, porém invertendo as Threads
			tPesq2.setThread(t1);
			
			//Starta a Thread1(t1)
			t1.start();
			//Starta a Thread2(t2)
			t2.start();

			//É necessário esperar as duas Threads acabarem para que a variável posicao receba o retorno do método
			//retornaPosicao() presente em tPesq e tPesq2. Pois caso o programa não espere ambas terminarem,
			//a atribuição a essa variável poderá ser feita antes de todo o processamento necessário.
			//Caso contrário, um valor errado pode ser retornado.
			t1.join();
			t2.join();
			
			//Após as Threads terminarem, eu chamo o método retornaPosicao() para descobrir em qual posição da lista
			//o número desejado estava. Mas estou chamando o método presente no obejto tPesq, que roda na Thread1.
			posicao = tPesq.retornaPosicao();
			//Essa verificação está presente pois caso a Thread2 ache o resultado antes da Thread1, 
			//o resulado retornado pela Thread1 será errado, uma vez que ela ainda não teria achado o resultado.
			//Caso a Thread2 ache o resultado antes, ela interrompe a Thread1 e vice-versa.
			if (posicao == -1) {
				posicao = tPesq2.retornaPosicao();
			}
		}
		//Imprime no console caso seja encontrado algum erro
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//Se a variável aux for -1 ao final do processamento, significa que o número desejado não foi encontrado,
		//pois 'posicao' foi inicializada como -1
		if (posicao == -1) {
			System.out.println("O valor 17 não está presente na lista");
		}
		//Caso contrário, é informado em qual posição o número desejado estava
		else {
			System.out.println("O valor 17 está na posição " + posicao + " da lista");
		}	
	}

	private static void preencherLista() {
		//Inicializa o ArrayList
		lista = new ArrayList<>();
		//Instancia um novo elemento da Classe Random
		Random r = new Random();
		//Adiciona 10000 elementos a essa lista
		for (int i = 0; i < 10000; i++) {
			lista.add(r.nextInt(10000));
		}
	}

}