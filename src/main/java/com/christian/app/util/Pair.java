package com.christian.app.util;

public class Pair<T, R> {

  T first;
  R second;

  public Pair(T first, R second) {
    this.first = first;
    this.second = second;
  }

  public static <T, R> Pair<T, R> of(T first, R second) {
    return new Pair<>(first, second);
  }

  public T getFirst() {
    return first;
  }

  public void setFirst(T first) {
    this.first = first;
  }

  public R getSecond() {
    return second;
  }

  public void setSecond(R second) {
    this.second = second;
  }
}
