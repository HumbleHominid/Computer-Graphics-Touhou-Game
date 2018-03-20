# Touhou Fan Game SRS

## Actors
| Actor | Description |
| ---: | --- |
| Player | The person that is playing the game. |

## Use Cases

### Start Run
<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>A player wants to start a run of the game</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The application is running</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>A specified difficulty of the game is started</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to start a game run.</li>
                    <li>System displays available difficulties.</li>
                    <li>Player selects difficulty they want to play.</li>
                    <li>System begins a game run with the selected difficulty.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>
                <ol>
                    <li>
                    <b>Player Cancels Section</b> (Branch during 3)
                    <ol>
                    <li>System returns to menu</li>
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
            <td>About 99% of application runs.</td>
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
            <td>All difficulties can be played at anytime</td>
        </tr>
    </tbody>
</table>

### Download Song
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

### View Credits
<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>A player wants to view the game's credits.</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The application is running.</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to view the credits.</li>
                    <li>System displays a credits scroll.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>
                <ol>
                    <li>
                        <b>Cancel Scroll</b> (Branch during 2)
                        <ol>
                            <li>Player indicates they want to exit credits scroll.</li>
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
            <td>Will have to display credits for artists, developers, and musicians</td>
        </tr>
    </tbody>
</table>

### Player Shoot
<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>The player wants to shoot a bullet</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The game is being played</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>A bullet has been fired</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to shoot a bullet</li>
                    <li>System fires a bullet and enhancements according to the player's current power</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>None</td>
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
            <td>Medium</td>
        </tr>
        <tr>
            <td align="right">Frequency of Use:</td>
            <td>100% of the game runs</td>
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
            <td>This does not specify if the player can hold a shoot button for continuous fire. This also does not describe the enhanced effects that will take place when a bullet is fired</td>
        </tr>
    </tbody>
</table>

### View High Scores
<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Players</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>A player wants to view the local high scores.</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The application is running.</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>None</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to view the high scores.</li>
                    <li>System displays list of difficulties.</li>
                    <li>Player selects difficulty they want to see.</li>
                    <li>System displays high scores of the difficulty the player selected.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>None</td>
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
            <td>None</td>
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

### Restart Run
<table>
    <tbody>
        <tr>
            <td align="right">Actors:</td>
            <td>Player</td>
        </tr>
        <tr>
            <td align="right">Description:</td>
            <td>A player wants to restart a game run.</td>
        </tr>
        <tr>
            <td align="right">Preconditions:</td>
            <td>The application is running and paused.</td>
        </tr>
        <tr>
            <td align="right">Postconditions:</td>
            <td>The game has been restarted.</td>
        </tr>
        <tr>
            <td align="right">Normal Flow:</td>
            <td>
                <ol>
                    <li>Player indicates they want to restart a game run.</li>
                    <li>System resets lives, power, and score to default. System starts the game from Stage 0 of the current difficulty.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Alternative Flows:</td>
            <td>None</td>
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
            <td>Medium</td>
        </tr>
        <tr>
            <td align="right">Frequency of Use:</td>
            <td>About 100% of times the applicaiton is run..</td>
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

