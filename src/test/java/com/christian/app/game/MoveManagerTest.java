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
  /*
  regular move testing
   */

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

  /*
  pawn move tests
   */

  @Test
  public void testPawnMovingForward() {
    final String move = "a3";
    board.add(Piece.create('P', "a2"));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertTrue(isValidMove);
  }

  @Test
  public void testPawnCantMoveForward() {
    final String move = "a3";
    board.add(Piece.create('P', "a2"));
    board.add(Piece.create('Q', "a3"));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertFalse(isValidMove);
  }

  @Test
  public void testPawnDoubleJumping() {
    final String move = "a4";
    board.add(Piece.create('P', "a2"));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertTrue(isValidMove);
  }

  @Test
  public void testPawnCantDoubleJumpIfMovedAlready() {
    final String move = "a4";
    Piece pawn = Piece.create('P', "a2");
    board.add(pawn);
    pawn.toggledMoved();

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertFalse(isValidMove);
  }

}