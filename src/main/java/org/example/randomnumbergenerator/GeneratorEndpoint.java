package org.example.randomnumbergenerator;

import org.example.randomnumbergenerator.encode.BigNumberEncoder;
import org.example.randomnumbergenerator.model.BigNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(
		value = "/generate",
		encoders = BigNumberEncoder.class)
public class GeneratorEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(GeneratorEndpoint.class);

	private static final Map<String, String> connectedIpToSessionId = new ConcurrentHashMap<>();

	private final RandomBigIntegerGenerator generator = new RandomBigIntegerGenerator();

	@OnOpen
	public void onOpen(Session session) throws IOException {
		String address = extractIPAddress(session);
		if (connectedIpToSessionId.putIfAbsent(address, session.getId()) == null) {
			logger.info("Open connection for {}", address);
		} else {
			logger.warn("Don't open duplicate connection for {}", address);
			session.close();
		}
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		logger.info("Message has been received: {}", message);
		try {
			session.getBasicRemote().sendObject(new BigNumber(generator.generate()));
		} catch (EncodeException e) {
			logger.error("Failed to encode response", e);
		}
	}

	@OnClose
	public void onClose(Session session) {
		String address = extractIPAddress(session);
		String sessionId = connectedIpToSessionId.get(address);
		if (session.getId().equals(sessionId)) {
			connectedIpToSessionId.remove(address);
			logger.info("Close connection for {}", address);
		}
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.error("Error has occurred, session id: {}", session.getId(), throwable);
	}

	private String extractIPAddress(Session session) {
		Object address = session.getUserProperties().get("javax.websocket.endpoint.remoteAddress");
		if (address instanceof InetSocketAddress) {
			return ((InetSocketAddress) address).getAddress().toString().replace("/", "");
		} else {
			logger.warn("Failed to extract IP address of client");
			return "";
		}
	}
}
