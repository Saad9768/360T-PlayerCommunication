package com.PlayerCommunicator.model;

public class Message {
	private Player sender;
	private Player recipient;
	private String messageText;

	public Message(Player sender, Player recipient, String messageText) {
		this.sender = sender;
		this.recipient = recipient;
		this.messageText = messageText;
	}

	public Player getSender() {
		return this.sender;
	}

	public Player getRecipient() {
		return this.recipient;
	}

	public String getMessageText() {
		return this.messageText;
	}
}
