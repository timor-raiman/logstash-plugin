package jenkins.plugins.logstash.persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class LogstashDao extends HostBasedLogstashIndexerDao {

  public LogstashDao(String logstashHostString, int logstashPortInt) {
    super(logstashHostString, logstashPortInt);
  }

  @Override
  public void push(String data) throws IOException {
    byte[] buf = (data+"\n").getBytes(StandardCharsets.UTF_8);

    try(DatagramSocket socket = new DatagramSocket())
    {
      DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(getHost()), getPort());
      socket.send(packet);
      socket.close();
    }
  }
}

