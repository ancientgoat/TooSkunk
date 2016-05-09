package com.premierinc.rule.action;

import com.premierinc.rule.run.SkRuleRunner;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 */
public class SkActionJmsProducer extends SkAction {

	private String host;
	private String port = "61616";
	private String queue;
	private String message;

	@Override
	public void run(final SkRuleRunner inRunner) {

		String newHost = (String) inRunner.getValue(this.host, this.host);
		String newPort = (String) inRunner.getValue(this.port, this.port);
		String newQueue = (String) inRunner.getValue(this.queue, this.queue);
		String msg = (String) inRunner.getValue(this.message, this.message);

		// Does this have embedded macros in the format '${macro}'
		String newMessage = inRunner.expandMacros(msg);

		validate(newHost, newPort, newQueue, newMessage);

		try {
			String urlString = String.format("tcp://%s:%s", newHost, newPort);
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(urlString);

			JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setDefaultDestinationName(newQueue);

			jmsTemplate.send(new MessageCreator() {
				public ObjectMessage createMessage(Session session) throws JMSException {
					ObjectMessage objectMessage = session.createObjectMessage();
					System.out.println("NEW MESSAGE : " + newMessage);
					System.out.println("NEW MESSAGE : " + newMessage);
					System.out.println("NEW MESSAGE : " + newMessage);
					objectMessage.setObject(newMessage);
					return objectMessage;
				}
			});
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void validate(String inNewHost, String inNewPort, String inNewQueue, String inMessage) {
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
		if (null == inMessage) {
			sb.append("\nMessage can NOT be null.");
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

	public String getMessage() {
		return message;
	}

	public void setMessage(final String inMessage) {
		message = inMessage;
	}
}
