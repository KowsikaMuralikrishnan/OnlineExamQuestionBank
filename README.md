📘 Online Exam Question Bank & Test Paper Assembly System (JDBC)
📌 Overview

The Online Exam Question Bank & Test Paper Assembly System is a console-based Java application developed using Core Java, JDBC, and Oracle Database.

This system allows an examination cell to:

Maintain a reusable question bank

Assemble test papers using difficulty-based blueprints

Publish and archive test papers

Protect the integrity of published exams

Manage questions transactionally

The application follows a clean Layered MVC Architecture (Bean → DAO → Service → Controller).

🚀 Features
🔹 Question Management

Add New Question

View Question Details

View All Questions

Remove Question (with validation)

🔹 Test Paper Management

Create Test Paper (Transactional)

Publish Test Paper (Transactional)

Archive Test Paper

🔹 Validation & Integrity

Prevent deletion of questions used in published papers

Prevent publishing invalid test papers

Validate blueprint difficulty mix

Ensure enough ACTIVE questions exist before paper creation

🛠️ Technologies Used

Java (Core Java)

JDBC

Oracle Database

SQL

Console-based UI

MVC Architecture

📂 Project Structure
src/
└── com.exam
    ├── app
    │   └── ExamMain.java
    │
    ├── service
    │   └── ExamService.java
    │
    ├── bean
    │   ├── Question.java
    │   └── TestPaper.java
    │
    ├── dao
    │   ├── QuestionDAO.java
    │   └── TestPaperDAO.java
    │
    └── util
        ├── DBUtil.java
        ├── ValidationException.java
        ├── QuestionPoolInsufficientException.java
        └── QuestionInPublishedPaperException.java

🧠 System Architecture

The project follows a Layered Architecture:

Bean Layer → Represents database entities

DAO Layer → Handles database operations

Service Layer → Contains business logic & validations

Controller Layer (Main) → Handles console interaction

🔄 Transactional Operations

The following operations are performed inside database transactions:

Create Test Paper

Publish Test Paper

Auto-commit is disabled during multi-step operations to ensure:

Data consistency

Atomic updates

Rollback on failure

📋 Business Rules Enforced

✔ Question ID must be unique
✔ Difficulty must be EASY / MEDIUM / HARD
✔ Marks must be positive
✔ Cannot publish non-DRAFT paper
✔ Cannot delete question used in PUBLISHED paper
✔ Must have enough ACTIVE questions to create paper
✔ Blueprint total must match required marks

📊 Status Lifecycle
Question Status

ACTIVE

INACTIVE

Test Paper Status

DRAFT → PUBLISHED → ARCHIVED

🧪 Sample Use Case

Example Blueprint:

Paper Title: DBMS Practice Test

Subject: DBMS

Total Marks: 10

Difficulty Mix: EASY=4, MEDIUM=6, HARD=0

System Workflow:

Validate inputs

Check question availability

Select appropriate questions

Store paper as DRAFT

Allow publishing after validation

▶️ How to Run

Configure Oracle DB credentials in DBUtil.java

Create required database tables

Compile the project

Run ExamMain

Perform operations via console

🖥️ Output 
===================================
 ONLINE EXAM SYSTEM - MAIN MENU
===================================
1. Add Question
2. View Question
3. Create Test Paper
4. Publish Test Paper
5. Archive Test Paper
6. Exit
===================================

<img width="1617" height="264" alt="image" src="https://github.com/user-attachments/assets/49dc34da-985c-4e0c-9faa-266a3e391f90" />


🎯 Key Concepts Demonstrated

JDBC Connection Handling

Transaction Management

DAO Pattern

Exception Handling

Blueprint-based Paper Assembly

Data Integrity Enforcement

Layered System Design
