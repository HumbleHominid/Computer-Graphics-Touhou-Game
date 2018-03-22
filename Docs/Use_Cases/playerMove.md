<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>The Player wants move.</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The player is playing the game.</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>The player has been moved.</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to move in one of the cardinal directions.</li>
                    <li>System updates the Player's movement vector.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>
                <ol>
                    <li>
                        <b>Slow Move</b> (Branch during 1)
                        <ol>
                            <li>Player indicates they want to move in one of the cardinal directions while indicating they want to slow move.</li>
                            <li>Return to 2</li>
                        </ol>
                    </li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Exceptions:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Includes:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Extends:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Priority:</td>
            <td>High</td>
        </tr>
        <tr>
            <td align="right">Frequency of Use:</td>
            <td>Around 100% of the game runs.</td>
        </tr>
        <tr>
            <td align="right">Business Rules:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Special Requirements:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Assumptions:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Notes and Issues:</td>
            <td>None</td>
        </tr>
    </tbody>
</table>
