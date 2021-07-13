package gui.guiLogic;

/**
 * To be implemented by ScreenLogic classes that can change their state
 * according the change of state of the application.
 * <p>
 * Updateable classes are meant to be notified for changes by Updater Classes
 * 
 * @author Hugo Phibbs
 * @version 13/7/21
 * @since 12/7/21
 */
public interface Updateable {

    /**
     * Handles recieving an update from an Updater class
     */
    public abstract void receiveUpdate();
}
