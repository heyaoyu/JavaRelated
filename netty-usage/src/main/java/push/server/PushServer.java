package push.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyaoyu on 2017/2/25.
 */
public class PushServer {

  private final static AttributeKey<String> clientid = new AttributeKey<String>("clientid");

  private final static ConcurrentHashMap<String, ChannelHandlerContext> CHANNEL_STORE
      = new ConcurrentHashMap<String, ChannelHandlerContext>();

  private final static ExecutorService es = Executors.newFixedThreadPool(2);// run 2 servers

  private static void setUpConnectServer(final int port) {
    es.submit(new Runnable() {
      @Override
      public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
          ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                  channel.pipeline().addLast(new AuthServerHandler()).addLast(
                      new ConnectedServerHandler());
                }
              }).bind().sync();
          future.channel().closeFuture().sync();
        } catch (Exception ex) {
          ex.printStackTrace();
          workerGroup.shutdownGracefully();
          bossGroup.shutdownGracefully();
        }
      }
    });
  }

  private static void setUpPushServer(final int port) {
    es.submit(new Runnable() {
      @Override
      public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
          ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                  channel.pipeline().addLast(new PushServerHandler());
                }
              }).bind().sync();
          future.channel().closeFuture().sync();
        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
          workerGroup.shutdownGracefully();
          bossGroup.shutdownGracefully();
        }
      }
    });
  }

  public static void main(String[] args) {
    setUpConnectServer(7777);
    setUpPushServer(8888);
  }

  public static class AuthServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(AuthServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      ByteBuf copiedMsg = ((ByteBuf) msg).copy();
      byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      String clientStr = new String(bytes);
      if (clientStr.startsWith("deviceId")) {
        ctx.fireChannelRead(copiedMsg);
      } else {
        ctx.writeAndFlush(Unpooled.copiedBuffer("close".getBytes()));
        ctx.close();
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("AuthServerHandlerError", cause);
      cause.printStackTrace();
    }
  }

  public static class ConnectedServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ConnectedServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      String clientStr = new String(bytes);
      Attribute<String> attribute = ctx.channel().attr(clientid);
      attribute.set(clientStr);
      CHANNEL_STORE.put(attribute.get(), ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("ConnectedServerHandlerError", cause);
      cause.printStackTrace();
    }
  }

  public static class PushServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PushServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      String clientStr = new String(bytes);
      ChannelHandlerContext context = CHANNEL_STORE.get(clientStr);
      context.writeAndFlush(Unpooled.copiedBuffer("yes".getBytes()));
      ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("PushServerHandlerError", cause);
      cause.printStackTrace();
    }

  }

  public static class PushConnectServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PushConnectServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      String clientStr = new String(bytes);
      Attribute<String> attribute = ctx.channel().attr(clientid);
      attribute.set(clientStr);
      CHANNEL_STORE.put(attribute.get(), ctx);
      if (attribute.get().equals("push")) {
        ctx.fireChannelRead(msg);
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error("PushAuthServerHandlerError", cause);
      cause.printStackTrace();
    }
  }
}
