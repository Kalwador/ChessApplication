package com.chess.spring.engine.board;
import java.util.BitSet;

public  class BoardConfiguration extends BitSet {

//	public BoardConfiguration() {
//		super(64);
//	}
//
//	public BoardConfiguration(BoardConfiguration bSet) {
//		super(64);
//		or(bSet);
//	}
//
//	public BoardConfiguration shift(int shiftValue) {
//		 int len = length();
//		if (shiftValue > 0) {
//			if (len + shiftValue < 64) {
//				for (int bitIndex = len; bitIndex >= 0; bitIndex--) {
//					set(bitIndex + shiftValue, get(bitIndex));
//				}
//				clear(0, shiftValue);
//			} else {
//				clear(shiftValue, len + shiftValue);
//			}
//		} else if (shiftValue < 0) {
//			if (len < -shiftValue) {
//				clear();
//			} else {
//				for (int bitIndex = -shiftValue; bitIndex < length(); bitIndex++) {
//					set(bitIndex + shiftValue, get(bitIndex));
//				}
//				clear(len + shiftValue, len);
//			}
//		}
//
//		return this;
//	}

	@Override
	public String toString() {
		 StringBuilder s = new StringBuilder();
		for (int i = 0; i < size(); i++) {
			 boolean bit_is_set = get(i);
			if (bit_is_set) {
				s.append(" 1 ");
			} else {
				s.append(" . ");
			}
			if ((i + 1) % 8 == 0) {
				s.append("\n");
			}
		}
		return s.toString();
	}

}
