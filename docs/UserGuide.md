### Finding entries: `find`


Finds members or events matching the given criteria. Matching is case-insensitive and by whole words.


Format (members):
- `find member n/KEYWORDS…` — Find by member name.
- `find member y/KEYWORDS…` — Find by year of study.


Examples:
- `find member n/Alex David`
- `find member y/1 2`


Format (events):
- `find event n/KEYWORDS…` — Find by event name.
- `find event l/KEYWORDS…` — Find by event location.


Examples:
- `find event n/Welcome`
- `find event l/UTown COM1`


### Deleting entries: `delete`


Deletes a member or event by its displayed index.


Format: `delete TYPE INDEX`


- `TYPE` is either `member` or `event`.
- `INDEX` refers to the index number shown in the current list and must be a positive integer.


Examples:
- `delete member 2`
- `delete event 1`


### Clearing entries: `clear`


Clears all members or all events.


Format: `clear TYPE`


- `TYPE` is either `member` or `event`.


Examples:
- `clear member`
- `clear event`


### Aliasing commands: `alias`


Creates a custom alias for a command word. Aliases apply for the current app session.


Format: `alias COMMAND_WORD ALIAS`


- Supported `COMMAND_WORD`s: `add`, `edit`, `delete`, `clear`, `find`, `list`, `help`, `exit`, `alias`.
- `ALIAS` must be a single word and not a built-in command word.


Examples:
- `alias delete rm` — After this, `rm member 1` works like `delete member 1`.
- `alias list ls`


### Exiting the program: `exit`


Exits the program.


Format: `exit`


### Saving the data


Data is saved to disk automatically after any command that changes data. There is no need to save manually.


### Editing the data file


Data is saved automatically as a JSON file at `[JAR file location]/data/addressbook.json`. Advanced users may edit this file directly.


<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file make its format invalid, the app will discard all data and start with an empty data file at the next run. Take a backup before editing.<br>
Furthermore, certain edits can cause the app to behave in unexpected ways (e.g., invalid dates or emails). Edit the data file only if you are confident you can update it correctly.
</div>


--------------------------------------------------------------------------------------------------------------------


## FAQ


**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous app home folder.


--------------------------------------------------------------------------------------------------------------------


## Known issues


1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.


--------------------------------------------------------------------------------------------------------------------


## Command summary


Action | Format, Examples
--------|------------------
**Add member** | `add member n/NAME p/PHONE e/EMAIL y/YEAR r/ROLE…`<br> e.g., `add member n/James Ho p/91234567 e/jamesho@u.nus.edu y/2 r/friend r/colleague`
**Add event** | `add event n/NAME d/YYYY-MM-DDTHH:MM l/LOCATION`<br> e.g., `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02`
**List** | `list TYPE` (TYPE: `member`|`event`)
**Find member** | `find member n/KEYWORDS…` or `find member y/KEYWORDS…`<br> e.g., `find member n/Alex David`, `find member y/1`
**Find event** | `find event n/KEYWORDS…` or `find event l/KEYWORDS…`<br> e.g., `find event n/Workshop`, `find event l/COM1`
**Edit member** | `edit member INDEX [n/NAME] [p/PHONE] [e/EMAIL] [y/YEAR] [r/ROLE]…`<br> e.g., `edit member 2 n/James Lee e/jameslee@u.nus.edu`
**Edit event** | `edit event INDEX [n/NAME] [d/YYYY-MM-DDTHH:MM] [l/LOCATION]`<br> e.g., `edit event 1 d/2025-10-05T19:00`
**Delete** | `delete TYPE INDEX` (TYPE: `member`|`event`)
**Clear** | `clear TYPE` (TYPE: `member`|`event`)
**Alias** | `alias COMMAND_WORD ALIAS`<br> e.g., `alias delete rm`
**Help** | `help`
**Exit** | `exit`
