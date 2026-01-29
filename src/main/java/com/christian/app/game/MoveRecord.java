package com.christian.app.game;

public record MoveRecord(Position start, Position end, char pieceMoved, char pieceAttacked) {

}
