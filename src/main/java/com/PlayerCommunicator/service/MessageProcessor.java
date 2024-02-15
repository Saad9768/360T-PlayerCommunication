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

	private void processMessage(Message message, boolean firstMessage) {
		HashMap<UUID, Integer> senderCounterMap = message.getSender().getCounter();
		int currentCount = senderCounterMap.getOrDefault(message.getRecipient().getId(), 0);
		// adding the count to the map
		senderCounterMap.put(message.getRecipient().getId(), ++currentCount);

		HashMap<UUID, Integer> receiptentCounterMap = message.getRecipient().getCounter();
		int currentReceipentCount = receiptentCounterMap.getOrDefault(message.getSender().getId(), 0);

		String responseMessage = message.getMessageText() + currentReceipentCount;
		String senderName = message.getSender().getName();
		if (firstMessage) {
			senderName += " (initiator)";
		}
		System.out.println(senderName + ":" + message.getMessageText() + "");
		this.addMessage(new Message(message.getRecipient(), message.getSender(), responseMessage));
	}

	public void startProcessing(int maxProcessing) {
		try {
			boolean firstMessage = true;
			while (true) {
				Message message = this.incomingMessages.take();

				// Getting the sender count
				HashMap<UUID, Integer> senderCounterMap = message.getSender().getCounter();
				int currentSenderCount = senderCounterMap.getOrDefault(message.getRecipient().getId(), 0);

				// Getting the Recipient count
				HashMap<UUID, Integer> receiptentCounterMap = message.getRecipient().getCounter();
				int currentReceipentCount = receiptentCounterMap.getOrDefault(message.getSender().getId(), 0);

				if (currentSenderCount >= maxProcessing) {
					System.out.println(
							"Total Message Recieved, " + message.getSender().getName() + " = " + currentSenderCount
									+ " , " + message.getRecipient().getName() + " = " + currentReceipentCount);
					System.out.println("Exiting this Process Gracefully... ");
					break;
				}
				processMessage(message, firstMessage);
				firstMessage = false;
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
