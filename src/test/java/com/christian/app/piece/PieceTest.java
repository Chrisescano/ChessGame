package com.christian.app.piece;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PieceTest {

  /*
   * test creating pieces
   */

  @Test
  public void testCreatingIllegalPiece() {
    Assert.assertNull(Piece.create('x', 0, 0));
  }

  @Test
  public void testCreatingPieceOutsideBoard() {
    Assert.assertNull(Piece.create('p', -1, 0));
  }

  @Test
  public void testCreatingValidPiece() {
    Assert.assertNotNull(Piece.create('p', 0, 0));
  }

}