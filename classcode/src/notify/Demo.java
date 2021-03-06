package notify;

class Producer extends Thread {
  public void run() {
    while (true) {
      Demo.drop.put(getId() + " "+ Math.random());
    }
  }
}

class Consumer extends Thread {
  public void run() {
    while (true) {
      System.out.println("   Taking: " + Demo.drop.take());
    }
  }
}


class Drop {
  private String message;
  private boolean full = false;

  public synchronized String take() {
    try {
      while (!full) wait();
      full = false;
      notifyAll();
      return message;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void put(String message) {
    try {
      while (full) wait();
      full = true;
      System.out.println("Putting: " + message);
      this.message = message;
      notifyAll();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}


public class Demo {
  public static Drop drop = new Drop();
  public static void main(String[] args) {
    (new Producer()).start();
    (new Producer()).start();
    (new Consumer()).start();
  }}
