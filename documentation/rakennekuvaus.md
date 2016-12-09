The game Gwent is run on the engine in the fi.riissanen.gwent.engine package. The engine contains classes to handle the OpenGL rendering pipeline, networking module and file utilities.

The high level systems can be accesses from Gwent, while most of the game logic can be found under the subsystem GameSystem. The game events are logged to EventSystem that then forwards events to any listeners.

The GameSystem contains the players, the GameBoard, but also a finite state machine that controls gui elements. Each player have their own hand and deck of cards. In the game the players also choose a Faction.

There are two types of cards in this clone, the UnitCard and the WeatherCard. When played the UnitCard spawns a unit on the board and activates any abilities it has. UnitCards can also have attributes that modify the card's properties like not being affected by other's abilities. The weather card has a special WeatherAbility that affects the boards CombatRows.
