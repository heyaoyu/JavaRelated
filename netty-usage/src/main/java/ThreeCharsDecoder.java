import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by heyaoyu on 2017/1/30.
 */


public class ThreeCharsDecoder extends ByteToMessageDecoder {


  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    while (in.readableBytes() >= 3) {
      ByteBuf tmp = in.readBytes(3);
      out.add(tmp);
    }
//        out.add(in.readBytes(in.readableBytes()));
  }
}

