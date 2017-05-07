package push.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyaoyu on 2017/3/11.
 */

// PushClient -> PushServer -> ConnectedClient
public class PushClient {

  public static void main(final String[] args) {
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    try {
      ChannelFuture future = bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
          .remoteAddress(new InetSocketAddress("127.0.0.1", 8888))
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
              channel.pipeline().addLast(new PushClientHandler());
            }
          }).connect().sync();
      future.channel().closeFuture().sync();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      eventLoopGroup.shutdownGracefully();
    }
  }

  public static class PushClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PushClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      ctx.writeAndFlush(Unpooled.copiedBuffer("deviceId:x", CharsetUtil.UTF_8));
//      ctx.writeAndFlush("clientId");
      ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("PushClientHandlerError", cause);
      cause.printStackTrace();
    }
  }
}
