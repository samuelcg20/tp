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
