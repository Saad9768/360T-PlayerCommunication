package com.PlayerCommunicator.service;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;

import com.PlayerCommunicator.model.Message;
import com.PlayerCommunicator.model.Player;

public class MessageProcessorTest {

	@Test
	public void testProcessMessage() {
		// Create a mock for BlockingQueue<Message>
		BlockingQueue<Message> mockQueue = mock(BlockingQueue.class);

		// Create a player and a message
		Player sender = new Player(UUID.randomUUID(), "Sender", null);
		Player recipient = new Player(UUID.randomUUID(), "Recipient", null);
		Message message = new Message(sender, recipient, "Hello");

		// Create a MessageProcessor with the mock BlockingQueue
		MessageProcessor messageProcessor = new MessageProcessor(mockQueue);

		// Call addMessage on MessageProcessor
		messageProcessor.addMessage(message);

		// Verify that addMessage was called on the mockQueue with the correct message
		verify(mockQueue, times(1)).add(message);
	}

	@Test
	public void testStartProcessing() throws InterruptedException {
		// Create a mock for BlockingQueue<Message>
		BlockingQueue<Message> mockQueue = mock(BlockingQueue.class);

		// Create a player and a message
		Player sender = new Player(UUID.randomUUID(), "Sender", null);
		Player recipient = new Player(UUID.randomUUID(), "Recipient", null);
		Message message = new Message(sender, recipient, "Hello");

		// Create a MessageProcessor with the mock BlockingQueue
		MessageProcessor messageProcessor = new MessageProcessor(mockQueue);

		// Enqueue the message in the mockQueue
		when(mockQueue.take()).thenReturn(message);

		// Call startProcessing on a separate thread
		Thread processingThread = new Thread(() -> messageProcessor.startProcessing(5));
		processingThread.start();

		// Wait for the processing thread to finish
		processingThread.join();

		// Verify that addMessage was called on the mockQueue with the response message
		verify(mockQueue, times(5)).add(argThat(arg -> ((Message) arg).getMessageText().startsWith("Hello")));
	}
}
