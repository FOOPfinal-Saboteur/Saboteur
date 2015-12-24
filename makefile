TARGET=Saboteur.class RoadCard.class Map.class
ENV=env
GUI=gui
MAIN=main

all: $(TARGET)

Saboteur.class: $(MAIN)/Saboteur.java
	@echo "making: " $@
	javac $(MAIN)/Saboteur.java $(ENV)/RoadCard.java $(ENV)/Map.java

RoadCard.class: $(ENV)/RoadCard.java
	@echo "making: " $@
	javac -d $(MAIN) $(ENV)/RoadCard.java

Map.class: $(ENV)/Map.java
	@echo "making: " $@
	javac -d $(MAIN) $(ENV)/Map.java $(ENV)/RoadCard.java

clean: 
	rm -rf env/*.class
	rm -rf GUI/*.class
	rm -rf main/*.class
run:
	@echo "Games start to run... "
	java -cp $(MAIN) Saboteur

