TARGET=Saboteur.class FunctionCard.class RoadCard.class Card.class Map.class Player.class MapTest.class 
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

RoadCard.class: $(ENV)/RoadCard.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/RoadCard.java

FunctionCard.class: $(ENV)/FunctionCard.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/FunctionCard.java

Player.class: $(ENV)/Player.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/Player.java

Map.class: $(ENV)/Map.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/Map.java $(ENV)/RoadCard.java
MapTest.class: $(ENV)/MapTest.java
	@echo "making: " $@
	javac -d $(MAIN) -classpath $(MAIN) $(ENV)/Map.java $(ENV)/RoadCard.java $(ENV)/MapTest.java

clean: 
	rm -rf env/*.class
	rm -rf GUI/*.class
	rm -rf main/*.class
run:
	@echo "Games start to run... "
	java -cp $(MAIN) Saboteur

