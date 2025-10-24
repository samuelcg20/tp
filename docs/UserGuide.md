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
