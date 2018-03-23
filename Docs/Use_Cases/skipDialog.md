<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>The player wants to skip dialog.</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The player is playing the game and is in a dialog scene.</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>Part of the dialog has been skipped.</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to skip dialog.</li>
                    <li>System progress to next dialog block.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>
                <ol>
                    <li>
                        <b>No next dialog block</b> (Branch during 2)
                        <ol>
                            <li>System exits dialog scene.</li>
                            <li>Exit</li>
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
            <td>Low</td>
        </tr>
        <tr>
            <td align="right">Frequency of Use:</td>
            <td>About 100% of time a dialog scene is reached.</td>
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
            <td>Note this skips a part of he dialog scene, not the whole scene.</td>
        </tr>
    </tbody>
</table>
