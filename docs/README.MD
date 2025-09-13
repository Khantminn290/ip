# Ronaldo Chatbot 

Ronaldo is a command-line chatbot inspired by task management applications. It helps you manage your **ToDo**, **Deadline**, and **Event** tasks efficiently with priority levels. The chatbot supports marking tasks as done, unmarking them, listing tasks, and provides user-friendly feedback for all operations.

---

## Table of Contents

* [Features](#features)
* [Commands](#commands)
* [Getting Started](#getting-started)
* [Example Usage](#example-usage)
* [File Structure](#file-structure)
* [Contributing](#contributing)
* [License](#license)

---

## Features

* Add, delete, and list tasks.
* Support for three types of tasks:

    * **ToDo**: simple tasks with a description.
    * **Deadline**: tasks with a due date/time.
    * **Event**: tasks with a start and end time.
* Set task priority: **Low**, **Medium**, or **High**.
* Mark tasks as done or unmark them.
* Clean and readable output with clear formatting.

---

## Commands

### Add a ToDo

```
todo <description> /p <priority>
```

Example:

```
todo Read book /p low
```

### Add a Deadline

```
deadline <description> /by <yyyy-mm-dd HHmm> /p <priority>
```

Example:

```
deadline Submit report /by 2025-09-14 0900 /p high
```

### Add an Event

```
event <description> /from <start time> /to <end time> /p <priority>
```

Example:

```
event Concert /from 2025-09-13 1400 /to 2025-09-13 1600 /p medium
```

### Mark a Task

```
mark <task index>
```

### Unmark a Task

```
unmark <task index>
```

### List all Tasks

```
list
```

## Getting Started

### Quick Start

1. Download the latest `.jar` version from the GitHub repository.
2. Open a command terminal and navigate to the directory containing the `.jar` file.
3. Run:

```bash
java -jar ronaldo.jar
```

---

## Example Usage

```
____________________________________________________________
 Hello! I'm Ronaldo
 What can I do for you? SIUU
____________________________________________________________

todo Help me ChatGPT /p high
____________________________________________________________
Got it. I've added this task:
  [T][ ] Help me ChatGPT (priority: High)
Now you have 1 task in the list.
____________________________________________________________

deadline Homework /by 2025-09-14 0900 /p high
____________________________________________________________
Got it. I've added this task:
  [D][ ] Homework (priority: High) (by: 14 September 2025 9:00 am)
Now you have 2 tasks in the list.
____________________________________________________________

event Concert /from 2025-09-13 1400 /to 2025-09-13 1600 /p low
____________________________________________________________
Got it. I've added this task:
  [E][ ] C
```

