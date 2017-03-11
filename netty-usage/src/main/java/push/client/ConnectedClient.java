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
 * Created by heyaoyu on 2017/2/25.
 */
public class ConnectedClient {

  public static void main(final String[] args) {
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    try {
      ChannelFuture future = bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
          .remoteAddress(new InetSocketAddress("127.0.0.1", 7777))
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
              channel.pipeline().addLast(new ConnectClientHandler());
            }
          }).connect().sync();
      future.channel().closeFuture().sync();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      eventLoopGroup.shutdownGracefully();
    }
  }

  public static class ConnectClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ConnectClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      ctx.writeAndFlush(Unpooled.copiedBuffer("deviceId:x", CharsetUtil.UTF_8));
//      ctx.writeAndFlush("clientId");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      String response = new String(bytes);
      System.out.println("From server:" + response);
      if (response.equals("close")) {
        ctx.close();
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("ConnectClientHandlerError", cause);
      cause.printStackTrace();
    }
  }

}
