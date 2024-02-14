package com.PlayerCommunicator;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.PlayerCommunicator.model.Message;
import com.PlayerCommunicator.model.Player;
import com.PlayerCommunicator.service.MessageProcessor;

public class PlayerCommunicator {
	public static void main(String[] args) {
		BlockingQueue<Message> sharedQueue = new LinkedBlockingQueue<>();
		MessageProcessor messageProcessor = new MessageProcessor(sharedQueue);
		Player player1 = new Player(UUID.randomUUID(), "ABC", messageProcessor);
		Player player2 = new Player(UUID.randomUUID(), "PQR", messageProcessor);
		Player player3 = new Player(UUID.randomUUID(), "MNP", messageProcessor);

		new Thread(() -> {
			player1.sendMessage(player2, "Started from " + player1.getName());
			messageProcessor.startProcessing(10);
		}).start();
		new Thread(() -> {
			player2.sendMessage(player3, "Started from " + player2.getName());
			messageProcessor.startProcessing(20);
		}).start();
		new Thread(() -> {
			player3.sendMessage(player1, "Started from " + player3.getName());
			messageProcessor.startProcessing(30);
		}).start();
		
	}
}
