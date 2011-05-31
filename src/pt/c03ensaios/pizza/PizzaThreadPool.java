package pt.c03ensaios.pizza;

import java.util.LinkedList;

/**
 * This is a thread pool implementation made for the PIZZA project. It implements a 
 * Thread Pool with a queue, but not any thread, but a PizzaThread. See the PizzaThread 
 * class to more information.
 * The idea is to have a pool with a maximum number of working threads, and a queue of 
 * threads waiting to be run. When a thread finishes the run() method, it calls the 
 * ThreadDies() method of the pool, that will decrement the running threads counter 
 * and check for any other waiting threads in the queue. If the queue is not empty, 
 * then ThreadDies() dequeues a thread and starts it.
 * 
 * @author Giuliano Roberto Pinheiro
 * @author Lucas Dermondes Gonçalves
 */
public final class PizzaThreadPool {
	private LinkedList<PizzaThread> waiting_threads;
	private final int max_running_threads;
	private volatile int running_threads; // Not certain about the volatile...
	
	public PizzaThreadPool(int max_threads) {
		this.waiting_threads = new LinkedList<PizzaThread>();
		this.max_running_threads = max_threads;
	}
	
	// Enqueues the thread in the pool, and starts the thread if applicable.
	public void EnqueueThread(PizzaThread t) {
		t.setPool(this);
		
		if (this.running_threads < this.max_running_threads) {
			t.run();
			this.running_threads++;
		}
		else this.waiting_threads.add(t);
	}
	
	// Dequeues the thread, just removing it from the queue.
	public void DequeueThread(Runnable t) {
		if (this.waiting_threads.contains(t)) this.waiting_threads.remove(t);
	}
	
	public synchronized void ThreadDies() {
		if (this.running_threads > 0) this.running_threads--;
		else this.running_threads = 0;
		
		if (this.running_threads < this.max_running_threads) {
			PizzaThread t = this.waiting_threads.poll();
			
			if (t != null) {
				t.run();
				this.running_threads++;
			}
		}
	}
}
