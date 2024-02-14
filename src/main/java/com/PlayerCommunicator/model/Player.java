package com.PlayerCommunicator.model;

import java.util.HashMap;
import java.util.UUID;

import com.PlayerCommunicator.service.MessageProcessor;

public class Player {
	private UUID id;
	private String name;
	private MessageProcessor messageProcessor;
	private HashMap<UUID, Integer> counter;

	public Player(UUID id, String name, MessageProcessor messageProcessor) {
		this.id = id;
		this.name = name;
		this.messageProcessor = messageProcessor;
		this.counter = new HashMap<>();
	}

	public void sendMessage(Player recipient, String messageText) {
		Message newMessage = new Message(this, recipient, messageText);
		this.messageProcessor.addMessage(newMessage);
	}

	public String getName() {
		return this.name;
	}

	public HashMap<UUID, Integer> getCounter() {
		return counter;
	}

	public void setCounter(HashMap<UUID, Integer> counter) {
		this.counter = counter;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
