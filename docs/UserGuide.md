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
