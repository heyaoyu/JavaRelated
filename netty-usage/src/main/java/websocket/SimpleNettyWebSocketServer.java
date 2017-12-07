package websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by heyaoyu on 2017/12/7.
 */
public class SimpleNettyWebSocketServer {

  public static void main(String[] args) {
    ServerBootstrap bootstrap = new ServerBootstrap();
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<NioSocketChannel>() {
              @Override
              protected void initChannel(NioSocketChannel channel) throws Exception {
                channel.pipeline().addLast("httpServerCodec", new HttpServerCodec());
                channel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                // http
//                channel.pipeline().addLast("httpHandler", new SimpleHttpController());
                // http-end
                // websocket
                channel.pipeline()
                    .addLast("websocket", new WebSocketServerProtocolHandler("/websocket"));
                channel.pipeline().addLast("websocketHandler", new TextFrameHandler());
                // websocket-end
              }
            })
        .bind(8888);
    try {
      future.sync();
      future.channel().closeFuture().sync();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  final static public class SimpleHttpController extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
      if (msg instanceof HttpRequest) {
        System.out.println(((HttpRequest) msg).getUri());
      }
      if (msg instanceof HttpContent) {
        HttpContent content = (HttpContent) msg;
        ByteBuf buf = content.content();
        System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
        String res = "I am OK";
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
            Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        ctx.write(response);
      }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      System.out.println(cause.getMessage());
      ctx.close();
    }
  }

  final static public class TextFrameHandler extends
      SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
      System.out.println(msg.text());
      String res = "I am OK from websocket";
      WebSocketFrame out = new TextWebSocketFrame(res);
      ctx.write(out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      System.out.println(cause.getMessage());
      ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
    }

  }
}
