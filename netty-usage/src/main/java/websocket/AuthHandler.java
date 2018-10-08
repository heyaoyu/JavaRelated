package websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by heyaoyu on 2018/10/8.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

  public final static AttributeKey<String> userKey = AttributeKey.valueOf("user");

  public final static ConcurrentHashMap<String, ChannelHandlerContext> store = new ConcurrentHashMap<>();

  @Override
  public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof FullHttpRequest) {
      FullHttpRequest request = (FullHttpRequest) msg;
      QueryStringDecoder paramDecoder = new QueryStringDecoder(request.getUri());
      List<String> userParam = paramDecoder.parameters().get("user");
      String user = userParam == null ? "" : userParam.get(0);
      if (user.startsWith("test")) {
        Attribute<String> attr = ctx.channel().attr(userKey);
        attr.setIfAbsent(user);
        store.putIfAbsent(user, ctx);
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
