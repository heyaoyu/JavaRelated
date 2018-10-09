package websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyaoyu on 2018/10/8.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
    // ping and pong frames already handled

    if (frame instanceof TextWebSocketFrame) {
      // Send the uppercase string back.
//      get username thru attributekey
//      Attribute<String> attr = ctx.channel().attr(AuthHandler.userKey);
//      String user = attr.get();
      String request = ((TextWebSocketFrame) frame).text();
      if (request.startsWith("push")) {
        String[] contents = request.split("_");
        ChannelHandlerContext context = AuthHandler.store.get(contents[1]);
        if (context != null) {
          context.writeAndFlush(new TextWebSocketFrame(contents[2].toUpperCase(Locale.US)));
        }
      }
      logger.info("{} received {}", ctx.channel(), request);
      ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
    } else {
      String message = "unsupported frame type: " + frame.getClass().getName();
      throw new UnsupportedOperationException(message);
    }
  }
}

