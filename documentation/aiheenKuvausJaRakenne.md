## Design document ##

### Description ###
A clone of the card game Gwent from The Witcher 3. The game is played between
two players both starting with 10 cards in their hands and is turn-based. The
game is simulating a battle and has both infantry, ranged and siege units.
Each turn a player either plays a unit card, or passes their turn. If the
player passes their turn, he will not participate further in that round. Each
unit card has a power score and might have some secondary ability. After both
players have passed their turns or the round is over, the units' power scores
are summed to form a total battle score. The player with the greater score wins
the round. At the start of every round the battle score is reset and the board
is cleared, if no ability says otherwise. A game is won with 2 round wins. No
cards are drawn between rounds, unless an ability or effect says so.

### Users ###
Two players play against each other with their decks on different computers

### Actions ###
Before match both players:
- Select a **_faction_**
- Select a **_faction leader_**
- Discard and redraw up to **2** cards in your **_hand_**

Player in turn:
- Play a **_Unit_** card
- Play a **_Weather_** card
- Pass **_turn_**
- Use **_faction leader_** ability

---
### Development ###

#### Class Diagram ####
![Class Diagram](/documentation/Class Diagram.png "Class Diagram")

#### Sequence Diagrams ####
![Sequence Diagram 1](/documentation/Sequence Diagram 1.png "Sequence Diagram")
![Sequence Diagram 2](/documentation/Sequence Diagram 2.png "Sequence Diagram")

#### Features ####
Sources:

http://www.ign.com/wikis/the-witcher-3-wild-hunt/Gwent_Card_Game

http://witcher.wikia.com/wiki/Gwent

- Card types
    - Unit card
        - Melee
        - Ranged
        - Siege
    - Special card
        - Weather
        - Other
- Factions and faction ability
    - Northern Realms
    - Nilfgaardian Empire
    - Monsters
    - Scoia'tael
- Unit card attributes
    - Agile: Is both melee and ranged
    - Hero: Immune against abilities and special cards
    - Spy: Is played on the enemy battlefield, adds to enemy strength, but you draw 2 cards
- Unit card abilities
    - Medic: Choose a unit card from the discard pile and play it instantly
    - Morale boost: Increase strength of all units in a row by one (exclude themselves)
    - Muster: Play all cards with the same name from the deck
    - Tight bond: Doubles the strength of units in the same row with the same name
- Game board (Combat rows)
- Deck (minimum 22 unit cards, max 10 special cards)
- Hand (starting with 10 cards)
- Turns
- Networking module
	- Client
	- Server

#### 1st Iteration (Deadline 11.11.2016) ####
- [x] Game board (Combat rows)
- [x] Deck
- [x] Hand
- [x] Card

#### 2nd Iteration (Deadline 18.11.2016) ####
- [x] Card types
- [x] Card abilities
- [x] Card attributes
- [x] Neutral cards (Weather cards)
- [x] Game system (Playable for 1)

#### 3rd Iteration (Deadline 25.11.2016) ####
- [x] Factions
- [x] Faction ability
- [ ] Faction leader cards

#### 4th Iteration (Deadline 2.12.2016) ####
- [ ] Networking
- [ ] Turns

#### 5th Iteration (Deadline 9.12.2016) ####
- [ ] Graphics engine done
- [ ] Integrate game logic with graphics engine

#### 6th Iteration (Deadline 16.12.2016) ####
- [ ] Bug fixing
- [ ] Polishing
- [ ] Finish documentation
- [ ] Wiki?
