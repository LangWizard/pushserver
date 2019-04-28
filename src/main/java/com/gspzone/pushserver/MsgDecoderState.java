package com.gspzone.pushserver;

public enum MsgDecoderState {
	READ_HEAD1,
	READ_HEAD2,
    READ_LENGTH,
    READ_TYPE,
    READ_INFO;
}
