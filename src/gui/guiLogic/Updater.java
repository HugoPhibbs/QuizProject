package gui.guiLogic;

/**
 * Interface implemented by ScreenLogic classes that have dependent ScreenLogic
 * classes whose state depends on them.
 * <p>
 * Acts like a subject in observer pattern
 * 
 * @author Hugo Phibbs
 * @version 13/7/21
 * @since 12/7/21
 */
public interface Updater {

    /**
     * Updates any dependents for this Object
     */
    public abstract void update();

}
