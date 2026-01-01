package com.christian.app.game;

import com.christian.app.parser.AlgebraicNotationParser;

public class AlgebraicNotation {

  private int startX;
  private int startY;
  private int endX;
  private int endY;
  private char symbol;
  private boolean isCapture;
  private boolean isCheck;
  private boolean isMate;
  private boolean isKingsideCastle;
  private boolean isQueensideCastle;
  private boolean isWhiteWin;
  private boolean isBlackWin;
  private boolean isDraw;

  // Getters/Setters

  public int getStartX() {
    return startX;
  }

  public void setStartX(int startX) {
    this.startX = startX;
  }

  public int getStartY() {
    return startY;
  }

  public void setStartY(int startY) {
    this.startY = startY;
  }

  public int getEndX() {
    return endX;
  }

  public void setEndX(int endX) {
    this.endX = endX;
  }

  public int getEndY() {
    return endY;
  }

  public void setEndY(int endY) {
    this.endY = endY;
  }

  public char getSymbol() {
    return symbol;
  }

  public void setSymbol(char symbol) {
    this.symbol = symbol;
  }

  public boolean isCapture() {
    return isCapture;
  }

  public void setCapture(boolean capture) {
    isCapture = capture;
  }

  public boolean isCheck() {
    return isCheck;
  }

  public void setCheck(boolean check) {
    isCheck = check;
  }

  public boolean isMate() {
    return isMate;
  }

  public void setMate(boolean mate) {
    isMate = mate;
  }

  public boolean isKingsideCastle() {
    return isKingsideCastle;
  }

  public void setKingsideCastle(boolean kingsideCastle) {
    isKingsideCastle = kingsideCastle;
  }

  public boolean isQueensideCastle() {
    return isQueensideCastle;
  }

  public void setQueensideCastle(boolean queensideCastle) {
    isQueensideCastle = queensideCastle;
  }

  public boolean isWhiteWin() {
    return isWhiteWin;
  }

  public void setWhiteWin(boolean whiteWin) {
    isWhiteWin = whiteWin;
  }

  public boolean isBlackWin() {
    return isBlackWin;
  }

  public void setBlackWin(boolean blackWin) {
    isBlackWin = blackWin;
  }

  public boolean isDraw() {
    return isDraw;
  }

  public void setDraw(boolean draw) {
    isDraw = draw;
  }
}
