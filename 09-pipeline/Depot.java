package pipeline;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Depot {

  private final LinkedList<String> items = new LinkedList<>();
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();

  public static final String STOP_CODE = "---STOP---";

  public void put(String item) {
		lock.lock();
		try {
			items.addLast(item);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public String take() throws InterruptedException {
		lock.lock();
		try {
			while (items.isEmpty()) {
				notEmpty.await();
			}
			return items.removeFirst();
		} finally {
			lock.unlock();
		}
	}


}
