package com.PlayerCommunicator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.PlayerCommunicator.model.Message;
import com.PlayerCommunicator.model.Player;

public class MessageProcessorTest {

	private MessageProcessor messageProcessor;
	private Player player1;
	private Player player2;
	private BlockingQueue<Message> incomingMessages;

	@BeforeEach
	public void setUp() {
		incomingMessages = new LinkedBlockingQueue<>();
		messageProcessor = new MessageProcessor(incomingMessages);
		player1 = new Player(UUID.randomUUID(), "Player1", messageProcessor);
		player2 = new Player(UUID.randomUUID(), "Player2", messageProcessor);
	}

	@Test
	public void testProcessMessage() throws InterruptedException {
		String messageText = "Hello, Player2!";
		Message message = new Message(player1, player2, messageText);
		incomingMessages.put(message);
		messageProcessor.startProcessing(10);
		assertTrue(player1.getCounter().containsKey(player2.getId()));
		assertEquals(10, player1.getCounter().get(player2.getId()).intValue());
		assertTrue(player2.getCounter().containsKey(player1.getId()));
		assertEquals(10, player2.getCounter().get(player1.getId()).intValue());
	}

	@Test
	public void testProcessMessageMultipleTimes() throws InterruptedException {
		String messageText = "Hello, Player2!";
		Message message = new Message(player1, player2, messageText);
		incomingMessages.put(message);
		incomingMessages.put(message);
		messageProcessor.startProcessing(40);
		assertTrue(player1.getCounter().containsKey(player2.getId()));
		assertEquals(40, player1.getCounter().get(player2.getId()).intValue());
		assertTrue(player2.getCounter().containsKey(player1.getId()));
		assertEquals(40, player2.getCounter().get(player1.getId()).intValue());
	}
}