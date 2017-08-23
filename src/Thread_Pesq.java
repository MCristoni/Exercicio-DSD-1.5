import java.util.ArrayList;
import java.util.ListIterator;

public class Thread_Pesq implements Runnable {
	
	private ArrayList<Integer> lista;
	private boolean ordem;
	private int pesq;
	private int posicao;
	private Thread thread;
	
	//Construtor
	public Thread_Pesq(ArrayList<Integer> lista, int pesq, boolean ordem, int posicao) 
	{
		this.lista = lista;
		this.pesq = pesq;
		this.ordem = ordem;
		this.posicao = posicao;
	}
	
	//Getter
	public Thread getThread() {
		return thread;
	}

	//Setter Thread
	public void setThread(Thread thread) {
		this.thread = thread;
	}

	@Override
	public void run() {
		int aux = 0;
		//Ordem: Início - Fim
		if(this.ordem)
		{
			ListIterator<Integer> itr = lista.listIterator();
			while(itr.hasNext())
			{
				Integer i = itr.next();
				if(pesq == i)
				{
					posicao = aux;
					//Para a outra Thread
					thread.interrupt();
					break;
				}
				aux++;
			}
		}
		//Ordem: Fim - Início
		else 
		{
			ListIterator<Integer> itr = lista.listIterator(lista.size());
			while(itr.hasPrevious())
			{
				Integer i = itr.previous();
				if(pesq == i)
				{
					posicao = lista.size()-1-aux;
					//Para a outra Thread
					thread.interrupt();
					break;
				}
				aux++;
			}
		}
	}

	public int retornaPosicao()
	{
		return posicao;
	}
}
