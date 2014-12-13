package me.dags.daflight.api;

/**
 * @author dags_ <dags@dags.me>
 */

public interface DaFlightUI
{

    /**
     * Adds a mod's status message to DaFlight's render queue
     * @param message Status message to display
     * @return ID of the newly added message
     */
    public int addModStatus(String message);

    /**
     * Removes the given ID from DaFlight's render queue
     * @param id ID of the message to remove
     */
    public void removeModStatus(int id);

    /**
     * Sets the visibility of the given status
     * @param id ID of the status to set the visibility of
     * @param visible true = visible, false = not visible
     */
    public void setStatusVisibility(int id, boolean visible);

    /**
     * Update an existing status
     * @param id ID of the status to set
     * @param status New status message to apply
     */
    public void setStatus(int id, String status);

}
