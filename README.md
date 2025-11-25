# Le Goata

LE GOATA: LEt's Go On A Text Adventure

Le Goata is a framework for building text adventures.

It consists of a game cycle, controllers, actions, event handlers, input utilities, and other game objects.

The Game Cycle organizes the game into repeating rounds.  Each round contains turns, and each turn contains one or more actions.

Controllers control what is done during a turn.  Whether the turn taker is a user or automated, the controller determines possible choices and makes a decision (or gets one from the user) for which action to take.  Controllers can also route to other controllers.  When each turn starts, the Default Controller runs first, and its job is to route to the appropriate controller for the turn.  Different players, different scenarios, different states, etc., require different controllers.  For example, if you are building a JRPG, it is likely you'll have multiple controllers for a battle scenario - one for users, and one (or more) for automated combatants; and you'll also have controllers for when you're navigating around the map, looking at items in a shop, etc.

Actions represent things done by players during a turn, such as attacking an enemy, moving to a different space, speaking to an NPC, etc.

Event handlers can be utilized to hook into different events.  These include game cycle events, action events, and time-change events (when the game's time is updated).

Controllers, Actions, and Event Handlers all get injected with a Control Set (not to be confused with controllers).  The Control Set contains Clock Controls, Scheduling Controls, Map Controls, Round Controls, Turn Controls, and Game Controls.  Each lets you manipulate a different part of the game.  This gives you a lot of freedom to change things at anytime.  However there are some rules, e.g. you cannot change which player's turn it is during a turn!

Players and other game objects derive from LGObject.  This object does not have much in it except a unique ID, which Le Goata uses to track objects.  The LGObject is meant to be extended so that you can make your players and other objects however you want to.

Once you've made your controllers, actions, event handlers, and LGObjects, you run the game by instantiating a Game Runner and configuring it to use your controllers/actions/handlers/objects.  Then, call the run method to start the game!

The following packages will be deleted after I check them for salvage-able code, so you can ignore them:

* org.legoata.equipment
* org.legoata.gamecharacter

Coming soon:

* map controls
* input controls
* more sample projects
* jar artifact support
* built-ins - awareness (visual, sound, and scent)
