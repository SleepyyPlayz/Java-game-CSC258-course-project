import jdk.jfr.Event;

import java.util.Scanner;

public class StorylineInteractor {


    private static final ViewController View;
    private static final SoundInteractor Sound;
    private static final SaveInteractor Save;
    private static final LoadInteractor Load;
    private static final PlayerInteractor PlayerAction;
    private static final EventManager Manager;
    private static final CombatInteractor Combat;



    public StorylineInteractor(ViewController viewController, SoundInteractor soundInteractor,
                               SaveInteractor saveInteractor, LoadInteractor loadInteractor,
                               PlayerInteractor playerInteractor, EventManager eventManager,
                               CombatInteractor combatInteractor) {
        View = viewController;
        Sound = soundInteractor;
        Load = loadInteractor;
        Save = saveInteractor;
        PlayerAction = playerInteractor;
        Manager = eventManager;
        Combat = combatInteractor;

    }

    /**Create and return a Player
     */
    public PlayerData createPlayer(String username, int eventID) {
        player = PlayerData(username, eventID, 100, 10);
        return player;
    }

    /** Set Player on their first Event. The method
     * takes in the username set by LoginInteractor.
     */
    public void startGame(PlayerData player) {
        StorylineInteractor.playEvent(player);
    }

    public static void finishGame() {
        //dunno ending cutscene event id
        View.display_event(1000);
    }

    /** If the Player loses the game, bring the Player back to the last save
     */
    public static void lose(PlayerData player) {
        View.display_lose();

        //Choice options
        Scanner choice_reader = new Scanner(System.in);
        View.display_exit_options();
        String choice = choice_reader.nextLine();

        Load.readFromFile();
        if (choice.equals("Quit")) {
            View.returnHomeScreen();
        }

        else {
            StorylineInteractor.playEvent(player);
        }
    }

    /** Exit the game
     */
    public static void exitGame() {
        View.display_exit();

        //Choice options
        Scanner choice_reader = new Scanner(View.display_exit_options());
        String choice = choice_reader.nextLine();

        Load.readFromFile();
        if (choice.equals("Quit")) {
            View.returnHomeScreen();
        }

        else {
            StorylineInteractor.playEvent(player);
        }
    }

    /** Grab an event based on the inputted UUID and output its sound file and narration.
     * At the end of the narration, let the Player make a choice based on the Event's
     * choicesNarration. If Event is a CombatEvent, make the Player fight the Enemy after
     * the narration. The method should also Save the game if the Event has an Auto Save
     */
    public static void playEvent(PlayerData player) {
        event = Manager.getEvent(player.eventID);
        Sound.playSound(event.getSoundFile);

        if (event.doesAutosave == True) {
            Save.saveToFile();
        }

        if (event instanceof CombatEvent) {

            View.display_event(event.getNarration());

            if (Combat.initiateCombat(event) == True) {
                PlayerAction.updateEvent(event.getChoicesNextUUIDs()[0]);
            }
            else {
                ///Player loses
                StorylineInteractor.lose(player);
            }
        }

        else {

            View.display_event(event.getNarration());

            //User input system
            Scanner choice_reader = new
                    Scanner(View.display_event_choices(event.getChoicesNarrations()));

            //idk if choice buttons are integers
            int choice = choice_reader.nextInt();

            for (int next_event : event.getChoicesNextUUIDs()) {
                if (choice == next_event) {
                    PlayerAction.updateEvent(choice);
                    break;
                }
            }

        }

        Sound.stopSound(event.getSoundFile);

        //finish the game, dunno final event id
        if (player.eventID == 1000) {
            StorylineInteractor.finishGame();
        }

        else {
            StorylineInteractor.playEvent(player);
        }
    }

}
