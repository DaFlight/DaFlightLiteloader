/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 *  Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 *  granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING
 *  ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL,
 *  DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
 *  WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE
 *  USE OR PERFORMANCE OF THIS SOFTWARE.
 */

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
