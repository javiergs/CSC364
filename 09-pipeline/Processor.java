package pipeline;

/**
 * Processor thread that takes items from the input depot, processes them,
 * and puts the results in the output depot.
 *
 * @author javiergs
 * @version 1.0
 */
public class Processor implements Runnable {

  private final String name;
	private final Depot in;
	private final Depot out;

	public Processor(String name, Depot in, Depot out) {
	  this.name = name;
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String item = in.take();
        if (Depot.STOP_CODE.equals(item)) break;
				String processed = item.toUpperCase();
				out.put(processed);
        System.out.println("\t -- ⚙️Processor " + name + " -> processed " + item + " into " + processed);
        Thread.sleep(1000 * Math.round(0.5 + Math.random()));
      }
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}

