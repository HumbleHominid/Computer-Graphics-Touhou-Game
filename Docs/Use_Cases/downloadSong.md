<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>A player wants to download one of the songs in the game.</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The application is running.</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>A song has been downloaded.</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to download a song.</li>
                    <li>System prompts user for a download location.</li>
                    <li>User enters a download location.</li>
                    <li>System saves a copy of the song at the desired location.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Exceptions:</td>
            <td>
                <ol>
                    <li><b>IOException</b> (Branch during 4)
                        <ol>
                            <li>System displays error message.</li>
                            <li>Exit.</li>
                        </ol>
                    </li>
                </ol>
            </td>
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
            <td>About 1% of users total.</td>
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
