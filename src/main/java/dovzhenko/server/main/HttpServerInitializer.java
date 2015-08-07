package dovzhenko.server.main;
import java.time.LocalDateTime;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

   @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setIp(HttpServerStatistics.getIpFromChannel(ch));
        connectionInfo.setEstablished(LocalDateTime.now());
        HttpServerStatistics.getInstance().addConnectionInfo(connectionInfo);
        p.addLast(new HttpServerStatisticHandler(0,connectionInfo));
        p.addLast(new HttpRequestDecoder());
        p.addLast(new HttpResponseEncoder());
        p.addLast(new HttpContentCompressor());
        p.addLast(new HttpServerHandler(connectionInfo));
 
    }

}
