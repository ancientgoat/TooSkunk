package com.premierinc.rule.action;

import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
public class SkActionJmsProducer extends SkAction {

	@Override
	public void run(final SkRuleRunner inRunner) {

		Connection connection = null;
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		try {
			Queue queue = session.createQueue("customerQueue");

			// Consumer
			MessageConsumer consumer = session.createConsumer(queue);
			TextMessage textMsg = (TextMessage) consumer.receive();
			System.out.println(textMsg);
			System.out.println("Received: " + textMsg.getText());
		} finally {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
