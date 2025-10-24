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
**add member** | `add member n/NAME p/PHONE e/EMAIL y/YEAR r/ROLE…`<br> e.g., `add member n/James Ho p/91234567 e/jamesho@u.nus.edu y/2 r/friend r/colleague`
**add event** | `add event n/NAME d/YYYY-MM-DDTHH:MM l/LOCATION`<br> e.g., `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02`
**list** | `list TYPE` (TYPE: `member`|`event`)
**find member** | `find member n/KEYWORDS…` or `find member y/KEYWORDS…`<br> e.g., `find member n/Alex David`, `find member y/1`
**find event** | `find event n/KEYWORDS…` or `find event l/KEYWORDS…`<br> e.g., `find event n/Workshop`, `find event l/COM1`
**edit member** | `edit member INDEX [n/NAME] [p/PHONE] [e/EMAIL] [y/YEAR] [r/ROLE]…`<br> e.g., `edit member 2 n/James Lee e/jameslee@u.nus.edu`
**edit event** | `edit event INDEX [n/NAME] [d/YYYY-MM-DDTHH:MM] [l/LOCATION]`<br> e.g., `edit event 1 d/2025-10-05T19:00`
**delete** | `delete TYPE INDEX` (TYPE: `member`|`event`)
**clear** | `clear TYPE` (TYPE: `member`|`event`)
**alias** | `alias COMMAND_WORD ALIAS`<br> e.g., `alias delete rm`
**help** | `help`
**exit** | `exit`
