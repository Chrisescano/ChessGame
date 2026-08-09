package com.game.io;

import java.util.Scanner;

public class TerminalIO {

  private final Scanner scanner;

  public TerminalIO() {
    scanner = new Scanner(System.in);
  }

  public String prompt(String prompt) {
    print(prompt);
    return scanner.nextLine();
  }

  public void print(String message) {
    System.out.print(message);
  }

  public void println(String message) {
    System.out.println(message);
  }
}
