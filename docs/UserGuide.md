Deletes a member or event by its displayed index.


Format: `delete TYPE INDEX`


- `TYPE` is either `member` or `event`.
- `INDEX` refers to the index number shown in the current list and must be a positive integer.


Examples:
- `delete member 2`
- `delete event 1`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Use <code>list member</code> or <code>list event</code> just before deleting to avoid index confusion.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> Deletions cannot be undone. Double-check the index before confirming.</div>




### Clearing Entries — `clear`


Clears all members or all events.


Format: `clear TYPE`


- `TYPE` is either `member` or `event`.


Examples:
- `clear member`
- `clear event`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Export a backup (copy the <code>data/addressbook.json</code> file) before bulk-clearing.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> This action deletes all entries of the chosen type. It cannot be undone.</div>




### Aliasing Commands — `alias`


Creates a custom alias for a command word. Aliases apply for the current app session.


Format: `alias COMMAND_WORD ALIAS`


- Supported `COMMAND_WORD`s: `add`, `edit`, `delete`, `clear`, `find`, `list`, `help`, `exit`, `alias`.
- `ALIAS` must be a single word and not a built-in command word.


Examples:
- `alias delete rm` — After this, `rm member 1` works like `delete member 1`.
- `alias list ls`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Choose aliases that mirror your team’s habits, e.g., <code>rm</code> for <code>delete</code>.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> Aliases are not saved between app runs.</div>




### Exiting — `exit`


Exits the program.


Format: `exit`


<div markdown="span" class="alert alert-success">✅ <strong>Tip:</strong> Data saves automatically — you don’t need to save before exiting.</div>
<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> If you see a file permission error while exiting, move the <code>.jar</code> to a folder you can write to and run again.</div>




### Saving Your Data


Data is saved to disk automatically after any command that changes data. No manual save is needed.




### Editing the Data File (Advanced)


Data is saved as a JSON file at `[JAR file location]/data/addressbook.json`.


<div markdown="span" class="alert alert-warning">⚠️ <strong>Caution:</strong> If the JSON format becomes invalid, the app will start with an empty data file on the next run. Make a backup first. Invalid values (e.g., non-<code>@u.nus.edu</code> emails or malformed dates) may cause unexpected behavior.</div>




[Back to top](#comclubconnect-user-guide)


--------------------------------------------------------------------------------------------------------------------




## FAQ
