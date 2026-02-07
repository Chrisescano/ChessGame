package com.christian.app.game;

import com.christian.app.piece.Piece;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BoardTest {

  private Board board;

  @BeforeClass
  private void setUp() {
    board = new Board();
  }

  @BeforeMethod
  private void testMethodSetUp() {
    board.clear();
  }

  @Test
  public void testAddingPiece() {
    board.add(Piece.create('Q', 0, 0));
    Piece piece = board.getPiece(0, 0);
    Assert.assertNotNull(piece);
    Assert.assertEquals(piece.getSymbol(), 'Q');
  }

  @Test
  public void testAddingPiecesAtSamePosition() {
    board.add(Piece.create('Q', 0, 0));
    board.add(Piece.create('B', 0, 0));
    Piece piece = board.getPiece(0, 0);
    Assert.assertEquals(piece.getSymbol(), 'Q');
    Assert.assertEquals(board.getPieces().size(), 1);
  }

  @Test
  public void testRemovingPiece() {
    Piece queen = Piece.create('Q', 0, 0);
    board.add(queen);
    board.remove(queen);
    Assert.assertEquals(board.getPieces().size(), 0);
  }

  @Test
  public void testRemovingPieceAtWrongPosition() {
    board.add(Piece.create('Q', 0, 0));
    Piece pawn = Piece.create('p', 1, 1);
    board.remove(pawn);
    Assert.assertEquals(board.getPieces().size(), 1);
  }

}