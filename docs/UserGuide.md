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
