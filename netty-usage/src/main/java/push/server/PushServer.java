package push.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyaoyu on 2017/2/25.
 */
public class PushServer {

  private final static AttributeKey<String> clientid = new AttributeKey<String>("clientid");

  private final static ConcurrentHashMap<String, ChannelHandlerContext> CHANNEL_STORE
      = new ConcurrentHashMap<String, ChannelHandlerContext>();

  public static void main(String[] args) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap bootstrap = new ServerBootstrap();
    try {
      ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(7777))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
              channel.pipeline().addLast(new PushAuthServerHandler()).addLast(
                  new PushRegisterServerHandler());
            }
          }).bind().sync();
      future.channel().closeFuture().sync();
    } catch (Exception ex) {
      ex.printStackTrace();
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static class PushAuthServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PushAuthServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      System.out.println(1);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      System.out.println(1);
      String clientStr = new String(msg.toString());
      Attribute<String> attribute = ctx.channel().attr(clientid);
      attribute.set(clientStr);
      ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("PushAuthServerHandlerError", cause);
      cause.printStackTrace();
    }
  }

  public static class PushRegisterServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PushRegisterServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      System.out.println(2);
      Attribute<String> attribute = ctx.channel().attr(clientid);
      CHANNEL_STORE.put(attribute.get(), ctx);
      Iterator<Map.Entry<String, ChannelHandlerContext>> iter = CHANNEL_STORE.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry<String, ChannelHandlerContext> entry = iter.next();
        System.out.println(entry.getKey());
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("PushRegisterServerHandlerError", cause);
      cause.printStackTrace();
    }

  }
}
