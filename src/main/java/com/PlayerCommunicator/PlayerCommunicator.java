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
		Player player1 = new Player(UUID.randomUUID(), "Player1", messageProcessor);
		Player player2 = new Player(UUID.randomUUID(), "Player2", messageProcessor);

		new Thread(() -> {
			player1.sendMessage(player2, "Hello");
			messageProcessor.startProcessing(10);
		}).start();

	}
}
