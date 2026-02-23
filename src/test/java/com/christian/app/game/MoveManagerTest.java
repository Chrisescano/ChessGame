package com.christian.app.game;

import com.christian.app.piece.Piece;
import com.christian.app.util.MoveRecord;
import com.christian.app.util.MoveStatus;
import com.christian.app.util.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MoveManagerTest {

  private static final Logger log = LoggerFactory.getLogger(MoveManagerTest.class);
  private Board board;
  private MoveManager moveManager;

  @BeforeClass
  public void setUp() {
    board = new Board();
  }

  @BeforeMethod
  public void testMethodSetUp() {
    board.clear();
    moveManager = new MoveManager(board, 0, true);
  }

  @Test
  public void testUnparseableMove() {
    String move = "x";
    boolean result = moveManager.isMoveLegal("x");
    int moveRecordId = moveManager.getLastMoveRecordId();
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.UNPARSEABLE_MOVE, null, null, null, move, false, false);
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testNoPiecesFound() {
    String move = "a3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.NO_PIECES_FOUND, null, null, null, move, false, false);
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testNoLegalPiecesFound() {
    board.add(Piece.create('Q', "a2"));
    board.add(Piece.create('Q', "a1"));
    board.add(Piece.create('p', "a3"));
    String move = "Qa5";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create("a5");
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.NO_LEGAL_PIECES_FOUND, null, null, movePosition, move, false, false);
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testAmbiguousMove() {
    board.add(Piece.create('R', "a1"));
    board.add(Piece.create('R', "a7"));
    String move = "Ra3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create("a3");
    String extraInfo = "did you mean [R1a3] or [R7a3]";
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.AMBIGUOUS_MOVE, null, null, movePosition, extraInfo, false, false);
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testLegalMoveToEmptyTile() {
    Piece rook = Piece.create('R', "a1");
    board.add(rook);
    String move = "Ra3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create("a3");
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.LEGAL_MOVE, rook, null, movePosition, "a3", false, false);
    Assert.assertTrue(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testLegalMoveToCapture() {
    Piece rook = Piece.create('R', "a1");
    Piece queen = Piece.create('q', "a3");
    board.add(rook);
    board.add(queen);
    String move = "Rxa3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create("a3");
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.LEGAL_CAPTURE, rook, queen, movePosition, "a3", false, false);
    Assert.assertTrue(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testIllegalMovePathBlocked() {
    Piece rook = Piece.create('R', "a1");
    Piece queen = Piece.create('q', "a2");
    board.add(rook);
    board.add(queen);
    String move = "Ra3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create("a3");
    String extraInfo = "path blocked";
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.ILLEGAL_MOVE, rook, queen, movePosition, extraInfo, false, false );
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testPawnLegalMovingForward() {
    Piece pawn = Piece.create('P', "a2");
    board.add(pawn);
    String move = "a3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create(move);
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.LEGAL_MOVE, pawn, null, movePosition, move, false, false);
    Assert.assertTrue(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testPawnDoubleJumping() {
    Piece pawn = Piece.create('P', "a2");
    board.add(pawn);
    String move = "a4";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create(move);
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.LEGAL_DOUBLE_JUMP, pawn, null, movePosition, move, false, false);
    Assert.assertTrue(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testPawnIllegalDoubleJump() {
    Piece pawn = Piece.create('P', "a2");
    pawn.toggledMoved();
    board.add(pawn);
    String move = "a4";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePosition = Position.create(move);
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.ILLEGAL_MOVE, pawn, null, movePosition, move, false, false);
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testPawnCapturing() {
    Piece pawnA = Piece.create('P', "a2");
    Piece pawnB = Piece.create('p', "b3");
    board.add(pawnA);
    board.add(pawnB);
    String move = "b3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePositon = Position.create(move);
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.LEGAL_CAPTURE, pawnA, pawnB, movePositon, move, false, false);
    Assert.assertTrue(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testPawnIllegalCapture() {
    Piece pawn = Piece.create('P', "a2");
    board.add(pawn);
    String move = "b3";
    boolean result = moveManager.isMoveLegal(move);
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePositon = Position.create(move);
    MoveRecord expected = MoveRecord.create(moveRecordId, 0, true, MoveStatus.ILLEGAL_MOVE, pawn, null, movePositon, move, false, false);
    Assert.assertFalse(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }

  @Test
  public void testPawnEnPassant() {
    //Given
    Piece pawnA = Piece.create('P', "a2");
    Piece pawnB = Piece.create('p', "b4");
    board.add(pawnA);
    board.add(pawnB);
    String move = "a3";

    //When
    Assert.assertTrue(moveManager.isMoveLegal("a4"));
    Position pawnAMove = Position.create("a4");
    pawnA.getPosition().setFile(pawnAMove.getFile());
    pawnA.getPosition().setRank(pawnAMove.getRank());
    boolean result = moveManager.isMoveLegal(move);

    //Then
    int moveRecordId = moveManager.getLastMoveRecordId();
    Position movePositon = Position.create(move);
    MoveRecord expected = MoveRecord.create(moveRecordId, 1, false, MoveStatus.LEGAL_EN_PASSANT, pawnB, pawnA, movePositon, move, false, false);
    Assert.assertTrue(result);
    Assert.assertEquals(moveManager.getLastMoveRecord(), expected);
  }
}