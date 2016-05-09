package com.premierinc.rule.action;

import com.premierinc.rule.run.SkGlobalContext;
import com.premierinc.rule.run.SkRuleRunner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 */
public class SkActionJmsConsumer extends SkAction {

	private String host;
	private String port = "61616";
	private String queue;
	private String macro;

	@Override
	public void run(final SkRuleRunner inRunner) {

		String newHost = (String) inRunner.getValue(this.host, this.host);
		String newPort = (String) inRunner.getValue(this.port, this.port);
		String newQueue = (String) inRunner.getValue(this.queue, this.queue);

		validate(newHost, newPort, newQueue, macro);

		try {
			String urlString = String.format("tcp://%s:%s", newHost, newPort);
			Connection connection = null;
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(urlString);
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			try {
				Queue localQueue = session.createQueue(newQueue);

				// Consumer
				MessageConsumer consumer = session.createConsumer(localQueue);

				Message receive = consumer.receive();
				String stringProperty = receive.getStringProperty("");
				ObjectMessage textMsg = (ObjectMessage) receive;
				System.out.println(textMsg);
				System.out.println("Received: " + textMsg.getObject());

				inRunner.setValue(macro, textMsg.getObject());

			} finally {
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void validate(String inNewHost, String inNewPort, String inNewQueue, String inMacro) {
		StringBuilder sb = new StringBuilder();

		if (null == inNewHost) {
			sb.append("\nHost can NOT be null.");
		}
		if (null == inNewPort) {
			sb.append("\nPort can NOT be null.");
		}
		if (null == inNewQueue) {
			sb.append("\nQueue can NOT be null.");
		}
		if (null == inMacro) {
			sb.append("\nMacro can NOT be null.");
		}

		if (0 < sb.length()) {
			throw new IllegalArgumentException(sb.toString());
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(final String inHost) {
		host = inHost;
	}

	public String getPort() {
		return port;
	}

	public void setPort(final String inPort) {
		port = inPort;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(final String inQueue) {
		queue = inQueue;
	}

	public String getMacro() {
		return macro;
	}

	public void setMacro(final String inMacro) {
		macro = inMacro;
	}
}
