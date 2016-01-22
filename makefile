TARGET=Saboteur.class FunctionCard.class RoadCard.class Card.class Map.class ActiveStatus.class Player.class Deck.class MapTest.class Action.class GamerStatus.class AIPlayer.class Main.class
SRC=src/sample
ENV=env
GUI=gui
MAIN=main

all: $(TARGET)

Saboteur.class: $(MAIN)/Saboteur.java
	@echo "making: " $@
	javac $(MAIN)/Saboteur.java $(ENV)/*.java

Card.class: $(ENV)/Card.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/Card.java

Deck.class: $(ENV)/Deck.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/FunctionCard.java $(ENV)/RoadCard.java $(ENV)/Card.java $(ENV)/Deck.java

RoadCard.class: $(ENV)/RoadCard.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/RoadCard.java

FunctionCard.class: $(ENV)/FunctionCard.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/FunctionCard.java

ActiveStatus.class: $(ENV)/ActiveStatus.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/ActiveStatus.java

GamerStatus.class: $(ENV)/GamerStatus.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/ActiveStatus.java $(ENV)/GamerStatus.java

Player.class: $(ENV)/Player.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/ActiveStatus.java $(ENV)/DestinyStatus.java $(ENV)/Player.java

Map.class: $(ENV)/Map.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/Map.java $(ENV)/RoadCard.java

MapTest.class: $(ENV)/MapTest.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/Map.java $(ENV)/RoadCard.java $(ENV)/MapTest.java

Action.class: $(ENV)/Action.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/ToWhere.java $(ENV)/FunctionCard.java $(ENV)/RoadCard.java $(ENV)/Action.java

AIPlayer.class: $(ENV)/AIPlayer.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/ActiveStatus.java $(ENV)/Player.java $(ENV)/GamerStatus.java $(ENV)/ToWhere.java $(ENV)/WhatHappen.java $(ENV)/DestinyStatus.java $(ENV)/InnerMap.java $(ENV)/AIPlayer.java

Main.class: $(ENV)/Main.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/ActiveStatus.java $(ENV)/Player.java $(ENV)/GamerStatus.java $(ENV)/ToWhere.java $(ENV)/WhatHappen.java $(ENV)/DestinyStatus.java $(ENV)/InnerMap.java $(ENV)/AIPlayer.java $(ENV)/Main.java

ui:
	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/*.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/AlertBox.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ChooseBox.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/MapGUI.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/CardGUI.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/Shuffler.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/MainController.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/DestinyStatus.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/InnerMap.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ToWhere.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/WhatHappen.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/Card.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/FunctionCard.java $(SRC)/RoadCard.java $(SRC)/Card.java $(SRC)/Deck.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/RoadCard.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/FunctionCard.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ActiveStatus.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ActiveStatus.java $(SRC)/GamerStatus.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ActiveStatus.java $(SRC)/DestinyStatus.java $(SRC)/Player.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/Map.java $(SRC)/RoadCard.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/Map.java $(SRC)/RoadCard.java $(SRC)/MapTest.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ToWhere.java $(SRC)/FunctionCard.java $(SRC)/RoadCard.java $(SRC)/Action.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/ActiveStatus.java $(SRC)/Player.java $(SRC)/GamerStatus.java $(SRC)/ToWhere.java $(SRC)/WhatHappen.java $(SRC)/DestinyStatus.java $(SRC)/InnerMap.java $(SRC)/AIPlayer.java

	javac -d $(MAIN) -classpath $(MAIN) $(SRC)/*.java

clean: 
	rm -rf env/*.class env/*.java~
	rm -rf GUI/*.class GUI/*.java~
	rm -rf main/*.class main/*.java~
	rm -rf main/sample/*.class

run:
	@echo "Cheap start to run... "
	java -cp $(MAIN) Main
run2:
	@echo "Try to run... "
	java -cp $(MAIN) sample.Main


