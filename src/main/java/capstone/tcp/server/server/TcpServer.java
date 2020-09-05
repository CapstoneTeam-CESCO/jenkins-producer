package capstone.tcp.server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import capstone.tcp.server.common.LogUtil;
import capstone.tcp.server.handler.ServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Component
@PropertySource(value = "classpath:/application-dev.properties")
public class TcpServer {
    
    @Value("${boss.thread.count}")
    private int bossCount;
    
    @Value("${worker.thread.count}")
    private int workerCount;
    
    @Value("${tcp.port}")
    private int port;
    
    @Autowired
    private ServiceHandler handler;
    
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossCount);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerCount);
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 150 * 1000)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast(handler);
                    }
                });

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();
            
            System.out.println("TCP server starts with port " + port);
            System.out.println("");

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
            
        } catch (InterruptedException e) {
            LogUtil.errorLog.error("::TcpServer Error::",e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }//start
}
