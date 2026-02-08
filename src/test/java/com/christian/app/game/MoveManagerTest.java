package com.christian.app.game;

import com.christian.app.piece.Piece;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MoveManagerTest {

  private Board board;
  private MoveManager moveManager;

  @BeforeClass
  public void setUp() {
    board = new Board();
    moveManager = new MoveManager(board, true);
  }

  @BeforeMethod
  public void testMethodSetUp() {
    board.clear();
  }

  @Test
  public void testQueenMovingOnEmptyBoard() {
    final String move = "Qh8";
    board.add(Piece.create('Q', 0, 0));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertTrue(isValidMove);
  }

  @Test
  public void testAmbiguousMove() {
    final String move = "Qh8";
    board.add(Piece.create('Q', 0, 0));
    board.add(Piece.create('Q', 7, 7));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertFalse(isValidMove);
  }

  @Test
  public void testNonAmbiguousMove() {
    final String move = "Qah8";
    board.add(Piece.create('Q', 0, 0));
    board.add(Piece.create('Q', 7, 7));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertTrue(isValidMove);
  }

  @Test
  public void testNonAmbiguousMove2() {
    final String move = "Q1a3";
    board.add(Piece.create('Q', 0, 0));
    board.add(Piece.create('Q', 0, 7));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertTrue(isValidMove);
  }

}