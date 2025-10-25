- Can I import my existing member list from Excel/Google Sheets?
 - Not directly. You can copy key details and add members using `add member ...`. Power users can transform CSV to match `addressbook.json`, but be careful with format.


- Do aliases persist across restarts?
 - No. Aliases last for the current session only.


- Why does my email keep getting rejected?
 - Emails must end with `@u.nus.edu` and have a valid local-part (alphanumeric plus `+ _ . -`, not starting/ending with a special character).


- What phone numbers are allowed?
 - Exactly 8 digits, starting with `8` or `9`, and no spaces.


- How do I record multiple roles for a member?
 - Repeat the `r/` prefix, e.g., `r/President r/TechLead`. Roles are alphanumeric and cannot contain spaces.


- Can I search by role or by event date range?
 - Role-based search and date-range filters are not supported currently. You can search members by name or year, and events by name or location.


- I entered `2025-09-01 18:00` but got an invalid date. Why?
 - Use ISO format with `T` between date and time: `2025-09-01T18:00`.


- I deleted the wrong person/event. Can I undo?
 - Undo is not available. Consider exporting a backup (`data/addressbook.json`) periodically.


- How do I move my data to another computer?
 - Copy your `addressbook.json` from the `data` folder next to your `.jar` into the same location on the new machine before running the app there.


- The app says it can’t save due to permissions. What should I do?
 - Move the `.jar` file to a folder you have write access to (e.g., your home folder or desktop) and run it again.


- Can multiple EXCO members use the same data file?
 - Yes. Share the `addressbook.json` file via a cloud drive, but ensure only one person runs the app and edits the file at a time to avoid conflicts.


- Will names be treated as duplicates if capitalization differs?
 - Yes. Member name matching is case-insensitive for uniqueness (e.g., `John Doe` and `john doe` are considered the same).




[Back to top](#comclubconnect-user-guide)


--------------------------------------------------------------------------------------------------------------------




## Troubleshooting


Problem: “Invalid command format!”
- Reason: Missing required parts or extra unexpected text.
- Solution: Compare with the command’s “Format” in this guide; remove extras and include all required prefixes.


Problem: “Phone number must be exactly 8 digits and start with 8 or 9.”
- Reason: The number contains spaces, wrong length, or starts with other digits.
- Solution: Enter an 8-digit number starting with 8 or 9, with no spaces.


Problem: “Emails should be of the format local-part@u.nus.edu …”
- Reason: Email is not an NUS email or local-part is invalid.
- Solution: Use a valid NUS email like `alexlee@u.nus.edu`.


Problem: “Dates should be in ISO format YYYY-MM-DDTHH:MM”
- Reason: Missing the `T` separator or wrong format.
- Solution: Enter `YYYY-MM-DDTHH:MM` (e.g., `2025-10-05T19:00`).


Problem: “Unknown command”
- Reason: Typos or using a command that does not exist.
- Solution: Run `help` to see supported commands. Consider creating an alias for frequently used commands.


Problem: “Could not save data due to insufficient permissions …”
- Reason: The current folder is read-only.
- Solution: Move the `.jar` to a writable folder and run it again.




[Back to top](#comclubconnect-user-guide)


--------------------------------------------------------------------------------------------------------------------




## Known Issues


1. When using multiple screens, if you move the application to a secondary screen, and later switch to only the primary screen, the GUI may open off-screen. Remedy: delete `preferences.json` in the app folder and start again.
2. If you minimize the Help window and run `help` again, the original Help window remains minimized and no new Help window appears. Remedy: restore the minimized Help window.




[Back to top](#comclubconnect-user-guide)


--------------------------------------------------------------------------------------------------------------------




## Command Summary


Action | Member Example | Event Example
-------|-----------------|--------------
add | `add member n/Alex Tan p/91234567 e/alextan@u.nus.edu y/2 r/Logistics` | `add event n/Welcome Tea d/2025-09-01T18:00 l/COM1-01-02`
edit | `edit member 2 e/alextan@u.nus.edu p/98765432` | `edit event 1 d/2025-10-05T19:00 l/COM3-01-12`
delete | `delete member 3` | `delete event 1`
list | `list member` | `list event`
find | `find member n/Alex` or `find member y/1` | `find event n/Welcome` or `find event l/COM1`
clear | `clear member` | `clear event`
alias | `alias delete rm` | `alias list ls`
help | `help` | `help`
exit | `exit` | `exit`


[Back to top](#comclubconnect-user-guide)
