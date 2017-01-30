/**
 * Created by heyaoyu on 2017/1/30.
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

  private int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public void init() throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap sBootstrap = new ServerBootstrap();
      sBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new ThreeCharsDecoder())
                  .addLast(new EchoServerHandler());
            }
          });

      ChannelFuture cf = sBootstrap.bind().sync();
      cf.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    try {
      new EchoServer(8080).init();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}

//class EchoServerHandler extends ChannelInboundHandlerAdapter {
//
//    private byte[] bytes;
//
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf tmpBuf = (ByteBuf) msg;
//        tmpBuf.readBytes(bytes);
//        System.out.println(new String(bytes));
//        ReferenceCountUtil.release(msg);
//    }
//
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf buf = ctx.alloc().buffer(16);
//        buf.writeBytes(bytes);
//        ctx.writeAndFlush(buf);
//    }
//
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//    }
//
//}

class EchoServerHandler extends ChannelInboundHandlerAdapter {

  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("Server read:" + msg + " with size:" + ((ByteBuf) msg).readableBytes());
    if (((ByteBuf) msg).isReadable()) {
      System.out.println(new String(msg.toString()));
    }
    ctx.write(msg);
  }

  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}