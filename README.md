# Player Communicator Service
The Player Communicator Service is a Java-based messaging service designed for handling and processing messages between players. It leverages a BlockingQueue to manage incoming messages and provides functionality to process and respond to these messages based on sender and recipient interactions.

## Features
- Asynchronous Message Processing: Utilizes a BlockingQueue to handle incoming messages asynchronously.
- Sender-Recipient Interaction Tracking: Keeps track of the number of messages exchanged between each sender and recipient pair.
- Graceful Shutdown: Supports graceful shutdown after processing a maximum number of messages for a given sender-recipient pair.
## Getting Started
### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- An IDE that supports Java (e.g., IntelliJ IDEA, Eclipse).
### Installation
1. Clone the repository to your local machine.
2. Import the project into your IDE as a Maven or Gradle project, depending on your project setup.
3. Ensure that your IDE recognizes the src folder as the source root.
### Usage
1. Initialize a BlockingQueue<Message> instance with your desired capacity.
2. Create an instance of MessageProcessor by passing the BlockingQueue<Message> to its constructor.
3. Use the addMessage(Message message) method to add messages to the processing queue.
4. Start the message processing thread by calling startProcessing(int maxProcessing), where maxProcessing is the maximum number of messages to be processed for a sender-recipient pair before shutting down the service gracefully.
