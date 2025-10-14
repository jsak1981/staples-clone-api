package com.ecommerce.staples_clone.coding;

public class Singleton {

  private Singleton() {
    // do nothing.
  }

  private static class SingletonHolder {
    private static final Singleton INSTANCE = new Singleton();
  }

  public static Singleton getInstance() {
    return SingletonHolder.INSTANCE;
  }
}
