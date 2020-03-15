Project Details

This project is a Spring MVC application for the simplified version of the Black Jack Game. 

<Game Rules>

- For each game two cards will be drawn for the player and the dealer from four new decks (52 cards per deck) that will be shuffled.
- The program will deal two cards face up for the player and deal one card face up and one card face down (hidden card) for the dealer.
- Whenever the sum of the displayed cards for the player is less than 21, the player can decide whether they will hit (receive another card). The hand continues until the player decides to stand (not receive another card) or they bust (their sum is over 21).
- After that, if the player has not busted, the dealer must hit until the sum of the cards is 17 or more.
- The player wins by not busting and having a total higher than the dealer's. The dealer loses by busting or having a lesser total than the player.
- If the player and the dealer have the same total, it is considered a push and it is a tie. If the player busts, the dealer wins and they do not need to draw any cards.
- After a game ends, the player can either quit or start a new game.

Use the following rules to calculate the score (total):
- Ace : +1 or +11 (The ace is always counted as 11. However, if the Ace makes the total over 21, it is counted as 1.)
- 10, jack, queen, king : +10 
- 2 to 9 : + its value. 


<Application Design>

Since it is an MVC application, the project is structured in such a way that each layer is designed to perform specific tasks. 

- The view layer comprises of JSP pages. The model, controller and service classes have been kept separate to provide loose coupling and separation of logic.
- Since the game involves deck of cards, I have used Enum classes for defining the Suit and Rank values. The Card class encapsulates these enum constants to represent a Card object. 
- The Deck object holds a collection of Card objects and represents a standard deck of cards(52 cards).
- The Hand class is used to denote the player's/dealer's hand. We have declared two classes called PlayerHand and DealerHand that extend the Hand class. These classes can be useful in the future when additional game features like placing bets, insurance, splitting, doubling down etc need to be implemented. The PlayerHand and DealerHand classes can be used for depicting specific state and behavior pertaining to the player and dealer respectively. They make the application extensible to accommodate future changes.
- A BlackJackServiceService interface and a BlackJackServiceImpl class implementing that interface have been designed to provide the business logic for the different game play actions. This makes the application extensible and easy to test since the components are loosely coupled. Also, in the future lets say we want to provide a different implementation for the game, we can easily create a new Service implementation that can implement the existing interface.
- The BlackJackController handles the routing of requests. It calls the required methods in the Service class to perform the desired operation and passes the data to update the view. The game allows the player to perform two actions(Hit and Stand) while the game is in progress. We have defined specific methods in the controller to handle each user action. 
- Junit test classes have been provided for testing the Service and Model classes.


<Build and Configuration>
- This project has been developed and tested in Eclipse IDE
- Apache Maven 3.3.9 has been used to build the application. It takes care of fetching and configuring the dependencies.
- The project has been built using Java 1.7


<Running the Application>
An application server like Apache Tomcat is required for running the application. I have used Apache Tomcat version 8.5.9 for running this application.

- For running the project in Eclipse, you need to create a new Server instance and publish the project to the server.
- Alternatively, you can go to the command line and use the the following command to generate the war file
	mvn clean install (For this to work,maven bin path needs to be added to the PATH enviroment variable in Windows)

- For generating the war file, go the the root folder of the project. Run the above command. The war file will be created in the target folder of the project.

To deploy the war file on the Tomcat server, 
1. Place the war file inside the webapps folder in Tomcat installation.
2. Navigate to the Tomcat bin directory, e.g., c:/apache-tomcat-8.5.9/bin
3. Type in startup and then hit Enter to execute the Tomcat server start up script
4. A separate window will open and a series of messages will appear, followed by the message indicating the server is started:
5. Once the server is started, go the browser and enter the following URL to test the application 
http://localhost:8080/blackjack
(Assuming your server instance is running on port 8080)
6. To stop the Tomcat server, type in shutdown and then hit Enter in the original command prompt: 


Hope you enjoy playing the game.