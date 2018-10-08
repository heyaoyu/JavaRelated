package websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.List;

/**
 * Created by heyaoyu on 2018/10/8.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof FullHttpRequest) {
      FullHttpRequest request = (FullHttpRequest) msg;
      QueryStringDecoder paramDecoder = new QueryStringDecoder(request.getUri());
      List<String> userParam = paramDecoder.parameters().get("user");
      String user = userParam == null ? "" : userParam.get(0);
      if (user.equals("test")) {
        ctx.fireChannelRead(msg);
      } else {
        System.out.println("close not pass auth...");
        ctx.channel().close();
      }
    } else {
      ctx.fireChannelRead(msg);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
      throws Exception {
    cause.printStackTrace();
  }
}
