package com.gspzone.pushserver;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class MsgDecoder extends ReplayingDecoder<MsgDecoderState> {
	private byte head;
	private int length;
	private byte type;
	private String info;
    public MsgDecoder() {
        //init state.
        super(MsgDecoderState.READ_HEAD1);
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        switch (state()) {
	        case READ_HEAD1:
	        	head = in.readByte();
	            if(head==ConstantValue.HEAD_DATA1) {
	            	checkpoint(MsgDecoderState.READ_HEAD2);
	            }else {
	            	break;
	            }
	        case READ_HEAD2:
	        	head = in.readByte();
	            if(head==ConstantValue.HEAD_DATA1) {
	            	break;
	            }else if(head==ConstantValue.HEAD_DATA2) {
	            	checkpoint(MsgDecoderState.READ_LENGTH);
	            }else {
	            	checkpoint(MsgDecoderState.READ_HEAD1);
	            	break;
	            }
	            
	        case READ_LENGTH:
	            length = in.readInt();
	            if(length>10240) {
	            	checkpoint(MsgDecoderState.READ_HEAD1);
	            	break;
	            }
	            checkpoint(MsgDecoderState.READ_TYPE);
	        case READ_TYPE:
	            type = in.readByte();
	            checkpoint(MsgDecoderState.READ_INFO);
	        case READ_INFO:
	        	byte[] tmp=new byte[length];
	            in.readBytes(tmp);
	            info=new String(tmp);
	            checkpoint(MsgDecoderState.READ_HEAD1);
	        	MsgInfo msginfo=new MsgInfo(type,info);
	            out.add(msginfo);
	            break;
	        default:
	            throw new Error("不该到达");
        }
    }
}