package com.PlayerCommunicator.service;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import com.PlayerCommunicator.model.Message;

public class MessageProcessor {
	private BlockingQueue<Message> incomingMessages;

	public MessageProcessor(BlockingQueue<Message> incomingMessages) {
		this.incomingMessages = incomingMessages;
	}

	public void addMessage(Message message) {
		this.incomingMessages.add(message);
	}

	private void processMessage(Message message) {
		HashMap<UUID, Integer> senderCounterMap = message.getSender().getCounter();
		int currentCount = senderCounterMap.getOrDefault(message.getRecipient().getId(), 0);
		senderCounterMap.put(message.getRecipient().getId(), ++currentCount);
		System.out.println("Sender :: '" + message.getSender().getName() + "', Receiver :: '"
				+ message.getRecipient().getName() + "', Message :: '" + message.getMessageText() + "'");

		String responseMessage = message.getMessageText() + "|" + currentCount;

		this.addMessage(new Message(message.getRecipient(), message.getSender(), responseMessage));
	}

	public void startProcessing(int maxProcessing) {
		try {
			while (true) {
				Message message = this.incomingMessages.take();
				HashMap<UUID, Integer> senderCounterMap = message.getSender().getCounter();
				int currentSenderCount = senderCounterMap.getOrDefault(message.getRecipient().getId(), 0);

				HashMap<UUID, Integer> receiptentCounterMap = message.getRecipient().getCounter();
				int currentReceipentCount = receiptentCounterMap.getOrDefault(message.getSender().getId(), 0);

				if (currentSenderCount >= maxProcessing) {
					System.out.println(
							"Total Message Recieved, " + message.getSender().getName() + " = " + currentSenderCount
									+ " , " + message.getRecipient().getName() + " = " + currentReceipentCount);
					System.out.println("Exiting this Process Gracefully... ");
					break;
				}
				processMessage(message);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Thread.currentThread().interrupt();
		}
	}
}
