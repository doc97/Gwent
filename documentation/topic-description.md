## Design document ##

Description
---
A clone of the card game Gwent from The Witcher 3. The game is played between
two players both starting with 10 cards in their hands and is turn-based. The
game is simulating a battle and has both infantry, ranged and siege units.
Each turn a player either plays a unit card, or passes their turn. If the
player passes their turn, he will not participate further in that round. Each
unit card has a power score and might have some secondary ability. After both
players have passed their turns or the round is over, the units' power scores
are summed to form a total battle score. The player with the greater score wins
the round. The battle score is reset every round. A game is won with 2 round
wins. No cards are drawn between rounds, unless an ability or effect orders
so.

Users
---
Two players play against each other with their decks

Actions:
---
    Before match both players:
    - Select a faction
    - Select a faction leader
    - Discard and redraw up to 2 cards in your hand
    
    Player in turn:
    - Play a Unit card
    - Play a Weather card
    - Pass turn
    - Use faction leader ability
    