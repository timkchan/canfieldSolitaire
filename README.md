# Canfield Solitaire
![](img/gp.gif?raw=true)

### 1. Rules of Canfield

Canfield (or Demon in England) was apparently introduced in the Canfield Casino in Saratoga Springs, New York. It's played with an ordinary deck of 52~playing cards. Initially, the player deals the cards as follows:

- 13 cards are dealt to a pile called the reserve, with only the top card face up.
- One card is dealt face up to the first of four foundation piles (the other three are initially empty).
- Four cards are dealt face up below the foundation to start four tableau piles.
- The remaining 34 cards (face down) form the stock.
- Next to the stock is an (initially empty) waste pile.

The result looks like this:

![](https://github.com/timkchan/solitaire/blob/master/img/canfield1.png?raw=true)

The foundation piles are built up (i.e., by increasing rank) in suit starting from the four cards whose rank is the same as that of the base card: the card that is initially dealt to the first foundation pile (this is four in the example), and wrapping around from King to Ace, if needed (so that last card to go on a foundation pile in the example above is a three). The tableau piles are built down in alternating colours (red on black on red), again wrapping around from Ace to King, if needed. You may not build on the base card (for example, in Figure 1, you may not play the 3♠ from the top of the reserve to the 4♦ in the tableau. Figure 2 shows an example from the middle of a possible game.

![](https://github.com/timkchan/solitaire/blob/master/img/canfield2.png?raw=true)

You may turn over cards from the stock to the waste in groups of three, or, if there are fewer than three cards in the stock, may turn over the rest of the stock onto the waste. Only the last card turned over (i.e., usually the third) is then visible on the waste pile. The top of the waste pile may be played to the tableau or foundation, if legal. When the stock is exhausted, the waste can be turned over (face down) to make a new stock.

You may move the top card of the reserve (if one is left) to a foundation pile, if legal, or to a tableau pile, if legal.

You may move the top card of a tableau pile to a foundation pile, if legal. Also, you may move an entire tableau pile to the top of another tableau pile if this results in a proper tableau pile (alternating colours built down). Whenever a tableau pile becomes empty (because its cards are moved to the foundation or to another tableau pile), it is automatically filled with the top card from the reserve. If the reserve is empty, you may move the top card of the waste to an empty tableau pile.

Finally, you may move a card from the foundation back to a tableau pile, if legal. This is sometimes useful for making it possible to move another card or cards to the tableau.

The goal is to move as many cards as possible to the foundation piles. In the original casino game, a player would pay $50 for a deck of cards and then get $5 back for each card played to the foundation (so that one would always get back at least $5, since there is always at least one card on the foundation). Hence, the maximum profit in a game is $210 (52 × 5 - 50).

### 2. Files
- `gui`: a directory holds the demo of the UCB GUI
- `canfield`: a directory holds the game Canfield Solitaire
- `testing`: a directory holds integration tests for the game Canfield Solitaire
- `make` and `*/make`: make files that help compiling and cleaning java files. 

### 3. Quick Overview of Project Structure
The skeleton is a form of the [Model-View-Controller (MVC) architecture]. One class, `canfield.Game` is the _model_: it embodies the current state of the game and contains all the game logic: mostly, what moves are legal at any given time. A second class, `canfield.TextPlayer`, serves both as a _view_, which consults the model and displays it, and a `controller`, which directs changes (for us, makes moves) in the model. For the GUI version, there are two skeletal classes, `canfield.CanfieldGUI`, intended as a controller, and `canfield.GameDisplay`, which should display a view of the game.

The class `Player` is an abstract class that describes the common characteristics of both `TextPlayers` and `GUIPlayer`s (the latter class simply creates a `CanfieldGUI`, which does all the work.)

Finally, `Main` chooses what kind of `Player` to create — `TextPlayer` or `GUIPlayer` — depending on command-line options, and then calls on it to do everything else.

### 4. Running Canfield Solitaire
Go into project root directory (you will see folders `canfield`, `gui`, `testing` ), remove all old classes:
```sh
$ make clean
```

Compile all java files (A `make` file has been made to achieve this, go inside `make` to see more options):
```sh
$ make
```

Test Canfield Solitaire integrity (Not exhaustive) (Optional) (The game will be run and the output will be compared against expected output):
```sh
$ make check
```

Now the game is ready to run. Like always, we have text-based and GUI-based:

1. Textual interface:
    ```sh
    $ java canfield.Main --text
    ```
    
2. GUI:
    ```sh
    $ java canfield.Main
    ```

\*To see the mini UCB GUI demo:
```sh
$ java gui.Main
```

### 5. Class Project Site
[here]

[here]: <https://inst.eecs.berkeley.edu/~cs61b/fa15/hw/proj0/>
[Model-View-Controller (MVC) architecture]: <https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller>