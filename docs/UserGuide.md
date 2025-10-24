---
layout: page
title: User Guide
---


ComClubConnect is a **desktop app for managing members and events**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ComClubConnect helps you complete member and event management tasks faster than traditional GUI apps.


* Table of Contents
{:toc}


--------------------------------------------------------------------------------------------------------------------


## Quick start


1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).


1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T09-2/tp/releases).


1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.


1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)


1. Type a command in the command box and press Enter to execute it. For example, typing **`help`** and pressing Enter opens the help window.<br>
   Some example commands you can try:


   * `list member` — Shows all members.
   * `list event` — Shows all events.
   * `add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President r/TechLead` — Adds a member.
   * `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02` — Adds an event.
   * `find member n/John` — Finds members with name containing “John”.
   * `find event l/COM1` — Finds events held at locations containing “COM1”.
   * `delete member 3` — Deletes the 3rd member shown in the current list.
   * `clear event` — Deletes all events.
   * `alias delete rm` — Creates `rm` as an alias for `delete`.
   * `exit` — Exits the app.

### Adding entries: `add`


Adds a member or an event.


Format (member): `add member n/NAME p/PHONE e/EMAIL y/YEAR r/ROLE…`


- `PHONE` must be 8 digits and start with `8` or `9`.
- `EMAIL` must be a valid NUS email ending with `@u.nus.edu`.
- `YEAR` must be one of `1`, `2`, `3`, or `4`.
- At least one `r/ROLE` must be provided; you can specify multiple roles.


Examples:
- `add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President`
- `add member n/Jane Tan p/91234567 e/janetan@u.nus.edu y/3 r/Treasurer r/Logistics`


Format (event): `add event n/NAME d/DATE_TIME l/LOCATION`


- `DATE_TIME` must be ISO local date-time: `YYYY-MM-DDTHH:MM` (e.g., `2025-09-01T18:00`).


Examples:
- `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02`
- `add event n/CS Workshop d/2025-12-30T14:30 l/NUS COM2`


### Listing entries: `list`


Shows members or events and switches the main list view accordingly.


Format: `list TYPE`


- `TYPE` is either `member` or `event`.


Examples:
- `list member`
- `list event`


### Editing entries: `edit`


Edits an existing member or event.


Format (member): `edit member INDEX [n/NAME] [p/PHONE] [e/EMAIL] [y/YEAR] [r/ROLE]…`


- Edits the member at `INDEX` (1-based) in the displayed members list.
- At least one optional field must be provided.
- Providing one or more `r/ROLE` values replaces all existing roles.
- To clear all roles, use `r/` with no value.


Examples:
- `edit member 1 p/91234567 e/johndoe@u.nus.edu`
- `edit member 2 n/Betsy Crower r/` (clears all roles)


Format (event): `edit event INDEX [n/NAME] [d/DATE_TIME] [l/LOCATION]`


Examples:
- `edit event 1 n/Welcome and Games Night`
- `edit event 2 d/2025-10-05T19:00 l/COM3-01-12`

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
