package dovzhenko.server.main;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;

public class HttpServerStatisticHandler extends ChannelTrafficShapingHandler {

    private final ConnectionInfo connectionInfo;

    public HttpServerStatisticHandler(long checkInterval, ConnectionInfo connectionInfo) {
        super(checkInterval);
        this.connectionInfo = connectionInfo;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.trafficCounter().start();
    }

    @Override
    public synchronized void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        this.trafficCounter().stop();
        connectionInfo.setClosed(LocalDateTime.now());
        connectionInfo.setBytesReceived(this.trafficCounter().cumulativeReadBytes());
        connectionInfo.setBytesSent(this.trafficCounter().cumulativeWrittenBytes());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

}
