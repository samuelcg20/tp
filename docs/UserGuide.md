1. Refer to the [Features](#features) below for details of each command.


--------------------------------------------------------------------------------------------------------------------


## Features


<div markdown="block" class="alert alert-info">


**:information_source: Notes about the command format:**<br>


- Some commands require a `TYPE` immediately after the command word: `member` or `event` (e.g., `add member`, `list event`).
- Words in `UPPER_CASE` are parameters supplied by the user.<br>
  For example, in `add member n/NAME`, `NAME` can be `John Doe`.
- Items in square brackets are optional.<br>
  For example, `[r/ROLE]` can be used 0 or more times.
- Items with `…` can be repeated including zero times.<br>
  For example, `[r/ROLE]…` can be `r/President r/Treasurer` or omitted.
- Parameters can be in any order for a command.
- Extraneous parameters for commands that do not take parameters (such as `help` and `exit`) are ignored.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as spaces around line breaks may be lost.


Prefix reference used in commands:
- `n/` name
- `p/` phone (8 digits, starts with 8 or 9)
- `e/` email (must end with `@u.nus.edu`)
- `y/` year of study (`1`–`4`)
- `r/` role(s) — can appear multiple times
- `d/` date-time in ISO format `YYYY-MM-DDTHH:MM`
- `l/` location
</div>


### Viewing help: `help`


Opens the Help window.


From the Help window, you can:
- Open the online User Guide or copy its URL.
- Launch the in-app Guided Tour to learn the UI layout step-by-step (menu bar, command box, result display, member/event list, and status bar).


![help message](images/helpMessage.png)


Format: `help`
