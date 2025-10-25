---
layout: page
title: User Guide
---




# ComClubConnect User Guide


ComClubConnect is a desktop app for <b>NUS CCA leaders</b> to <b>manage members and events</b>, optimised for use via a Command Line Interface (CLI) while still having an intuitive Graphical User Interface (GUI). If you are a fast typer, ComClubConnect helps you track members, plan events, and communicate changes faster than traditional spreadsheets or GUI-only apps.




* Table of Contents
  {:toc}




<div style="float:right; width: 280px; margin: 0 0 1rem 1.25rem;" markdown="1">
<strong>Quick Links</strong>


- [Quick Start](#quick-start)
- [About This Guide](#about-this-guide)
- [Features](#features)
- [Command Summary](#command-summary)
- [FAQ](#faq)
- [Troubleshooting](#troubleshooting)
</div>




--------------------------------------------------------------------------------------------------------------------




## About This Guide


This guide is written for NUS CCA leaders who need to maintain member records, plan events, and perform routine admin tasks quickly and accurately using a keyboard-first workflow.


Assumed prior knowledge:
- Comfortable using a command terminal on Windows / macOS / Linux.
- Basic familiarity with copying/pasting commands and editing plain text.
- Understands CCA context (roles, member lists, event details like date/time and venue).


How to use this guide:
- Quick start: run the app and try a few example commands.
- Features: step-by-step usage, command syntax, examples, expected output, tips and cautions.
- Troubleshooting and FAQ: common problems, reasons, and solutions.


[Back to top](#comclubconnect-user-guide)




--------------------------------------------------------------------------------------------------------------------




## Quick Start


1. Ensure you have Java 17 or above installed on your computer.<br>
> Checking your Java version:
> - Open a command terminal
> - Type `java -version` and press Enter
> - If Java is installed, you'll see the version number (e.g., `java version "17.0.1"`)
> - The first number should be 17
>
> If Java is not installed or the version is below 17:
> - Download and install Java 17 by following the guide:
    >   - [for Windows users](https://se-education.org/guides/tutorials/javaInstallationWindows.html)
>   - [for Mac users](https://se-education.org/guides/tutorials/javaInstallationMac.html)
>   - [for Linux users](https://se-education.org/guides/tutorials/javaInstallationLinux.html)
> - After installation, restart your terminal and verify the version again


2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T09-3/tp/releases).


3. Copy the `.jar` file to the folder you want to use as the home folder for your ComClubConnect.


4. Open a command terminal, `cd` into the folder you put the `.jar` file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note that the app contains some sample data and the layout is explained in coloured boxes.<br>
   ![Ui](images/Ui.png)


### Quick CLI Tutorial
- Click the command input box (bottom of the UI), type a command exactly as shown (prefixes like `n/`, `p/`, `e/`, `y/`, `r/`, `d/`, `l/` are required), then press Enter.
- Typical response types:
- Success message with brief summary (e.g., “New event added: …”).
- List output after `list` or `find` showing matching member/event entries.
- Error message beginning with “Invalid command format!” or a validation message — read it carefully and retry.
- Example sequence:
1. `add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President`
    - Expected output: “New person added: John Doe …”
2. `list member`
    - Expected output: member list including “John Doe”.
3. `find event n/Welcome`
    - Expected output: a filtered event list containing events with “Welcome” in the name.


5. Type your command in the command box and press Enter to execute it.
   Some example commands you can try:


- `help` : Shows the help window that explains the command usage.
- `list member` : Lists all members.
- `list event` : Lists all events.
- `add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President` : Adds a member named John Doe.
- `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02` : Adds an event.
- `delete member 3` : Deletes the 3rd member shown in the current list.
- `clear event` : Deletes all events.
- `exit` : Exits the app.




[Back to top](#comclubconnect-user-guide)


--------------------------------------------------------------------------------------------------------------------




## Features


<div markdown="block" class="alert alert-info">


**Notes on command format**
- Some commands require a `TYPE` immediately after the command word: `member` or `event` (e.g., `add member`, `list event`).
- Words in `UPPER_CASE` are parameters you supply. For example, in `add member n/NAME`, `NAME` can be `John Doe`.
- Items in square brackets are optional. Items marked with `…` can repeat, including zero times.
- Parameters can be in any order for a command.
- Extraneous parameters for commands that do not take parameters (such as `help` and `exit`) are ignored.
- If you are using a PDF version, commands that wrap across lines may lose spaces when copied — retype if needed.

Prefix reference used in commands:
- `n/` name
- `p/` phone (8 digits, starts with 8 or 9)
- `e/` email (must end with `@u.nus.edu`)
- `y/` year of study (`1`–`4`)
- `r/` role(s) — alphanumeric, can appear multiple times
- `d/` date-time in ISO format `YYYY-MM-DDTHH:MM`
- `l/` location
</div>




### Viewing Help — `help`


Opens the Help window.


Format: `help`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Press <code>F1</code> or use the Help menu to open the Help window quickly.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> If the Help window is minimized, running <code>help</code> again will not open a new window. Restore the minimized window instead.</div>


![help message](images/helpMessage.png)




### Adding Entries — `add`


Adds a member or an event.


Format (member): `add member n/NAME p/PHONE e/EMAIL y/YEAR r/ROLE…`


- `PHONE` must be 8 digits and start with `8` or `9`.
- `EMAIL` must be a valid NUS email ending with `@u.nus.edu`.
- `YEAR` must be one of `1`, `2`, `3`, or `4`.
- At least one `r/ROLE` must be provided; you can specify multiple roles.
- Duplicate check: member names are matched case-insensitively. Two members with the same name are considered duplicates.


Examples:
- `add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President`
- `add member n/Jane Tan p/91234567 e/janetan@u.nus.edu y/3 r/Treasurer r/Logistics`


Format (event): `add event n/NAME d/DATE_TIME l/LOCATION`

- `DATE_TIME` must be ISO local date-time: `YYYY-MM-DDTHH:MM` (e.g., `2025-09-01T18:00`).


Examples:
- `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02`
- `add event n/CS Workshop d/2025-12-30T14:30 l/NUS COM2`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Use multiple <code>r/</code> prefixes to add several roles at once, e.g., <code>r/President r/TechLead</code>.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> Dates must include the <code>T</code> separator (e.g., <code>2025-09-01T18:00</code>). Emails must end with <code>@u.nus.edu</code>.</div>




### Listing Entries — `list`


Shows members or events and switches the main list view accordingly.


Format: `list TYPE`


- `TYPE` is either `member` or `event`.


Examples:
- `list member`
- `list event`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> After a successful <code>add</code> or <code>edit</code>, run <code>list</code> to refresh the view you care about.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> Extra words after <code>list TYPE</code> are not allowed (e.g., <code>list member now</code> is invalid).</div>




### Editing Entries — `edit`


Edits an existing member or event.


Format (member): `edit member INDEX [n/NAME] [p/PHONE] [e/EMAIL] [y/YEAR] [r/ROLE]…`


- Edits the member at `INDEX` (1-based) in the displayed members list.
- At least one optional field must be provided.
- Providing one or more `r/ROLE` values replaces all existing roles.
- To clear all roles, use `r/` with no value.
- Changing a member’s name to one that matches an existing member (case-insensitive) is not allowed.


Examples:
- `edit member 1 p/91234567 e/johndoe@u.nus.edu`
- `edit member 2 n/Betsy Crower r/` (clears all roles)


Format (event): `edit event INDEX [n/NAME] [d/DATE_TIME] [l/LOCATION]`


Examples:
- `edit event 1 n/Welcome and Games Night`
- `edit event 2 d/2025-10-05T19:00 l/COM3-01-12`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> You can edit multiple fields in one command, e.g., <code>edit member 3 n/New Name p/91234567</code>.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> Ensure the <code>INDEX</code> refers to the currently displayed list (members vs. events).</div>




### Finding Entries — `find`


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


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Combine multiple keywords to broaden the match, e.g., <code>find event n/Welcome Games</code>.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> Searching by roles is not supported; use member name or year instead.</div>




### Deleting Entries — `delete`
