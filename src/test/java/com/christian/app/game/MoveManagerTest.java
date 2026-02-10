package com.christian.app.game;

import com.christian.app.piece.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MoveManagerTest {

  private static final Logger log = LoggerFactory.getLogger(MoveManagerTest.class);
  private Board board;
  private Boolean activeColor;
  private MoveManager moveManager;

  @BeforeClass
  public void setUp() {
    board = new Board();
    activeColor = Boolean.TRUE;
    moveManager = new MoveManager(board, activeColor);
  }

  @BeforeMethod
  public void testMethodSetUp() {
    activeColor = Boolean.TRUE;
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

  @Test
  public void testPawnCanCaptureSideways() {
    final String move = "b3";
    board.add(Piece.create('P', "a2"));
    board.add(Piece.create('q', "b3"));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertTrue(isValidMove);
  }

  @Test
  public void testPawnCantCaptureSideways() {
    final String move = "b3";
    board.add(Piece.create('P', "a2"));

    boolean isValidMove = moveManager.isValidMove(move);

    Assert.assertFalse(isValidMove);
  }

  @Test
  public void testPawnEnPassantCapture() {
    Piece pawnA = Piece.create('P', "a2");
    Piece pawnB = Piece.create('p', "b4");
    board.add(pawnA);
    board.add(pawnB);

    boolean isValidMove = moveManager.isValidMove("a4");

    Assert.assertTrue(isValidMove);
    pawnA.getPosition().setRank(4);
    activeColor = Boolean.FALSE;

    isValidMove = moveManager.isValidMove("a3");

    Assert.assertTrue(isValidMove);
  }

}