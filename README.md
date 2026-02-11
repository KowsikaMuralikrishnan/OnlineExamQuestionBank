📘 Online Exam Question Bank & Test Paper Assembly System (JDBC)
📌 Project Overview

The Online Exam Question Bank & Test Paper Assembly System is a console-based Java application developed using Core Java, JDBC, and Oracle Database.

This system allows an examination cell to:

Maintain a reusable question bank

Assemble test papers using difficulty-based blueprints

Publish and archive test papers

Protect the integrity of published exams

Manage questions transactionally

The application follows a clean MVC architecture with layered design (Bean, DAO, Service, Controller).

🚀 Features
🔹 Question Management

Add New Question

View Question Details

View All Questions

Remove Question (with integrity validation)

🔹 Test Paper Management

Create Test Paper (Transactional)

Publish Test Paper (Transactional)

Archive Test Paper

🔹 Validation & Integrity

Prevent deletion of questions used in published papers

Prevent publishing invalid papers

Validate blueprint and difficulty mix

Ensure enough ACTIVE questions exist before paper creation

🛠️ Technologies Used

Java (Core Java)

JDBC

Oracle Database

SQL

Console-based UI

MVC Architecture

🗄️ Database Setup (Oracle)
Step 1: Create New User
sqlplus / as sysdba

CREATE USER exam_user IDENTIFIED BY exam123;
GRANT CONNECT, RESOURCE TO exam_user;
COMMIT;
EXIT;


⚠️ Do NOT use scott/tiger account.

Step 2: Create Tables (Login as new user)
📌 QUESTION_TBL
CREATE TABLE QUESTION_TBL (
    QUESTION_ID VARCHAR2(12) PRIMARY KEY,
    SUBJECT VARCHAR2(100),
    TOPIC VARCHAR2(150),
    DIFFICULTY VARCHAR2(20),
    MARKS NUMBER(5,2),
    STATUS VARCHAR2(20)
);

📌 TEST_PAPER_TBL
CREATE TABLE TEST_PAPER_TBL (
    PAPER_ID NUMBER(10) PRIMARY KEY,
    PAPER_TITLE VARCHAR2(200),
    SUBJECT VARCHAR2(100),
    TOTAL_MARKS NUMBER(6,2),
    QUESTION_ID_LIST VARCHAR2(1000),
    STATUS VARCHAR2(20)
);

📂 Project Structure
com.exam.app
    └── ExamMain (Entry Point)

com.exam.service
    └── ExamService (Business Logic Layer)

com.exam.bean
    ├── Question
    └── TestPaper

com.exam.dao
    ├── QuestionDAO
    └── TestPaperDAO

com.exam.util
    ├── DBUtil
    ├── ValidationException
    ├── QuestionPoolInsufficientException
    └── QuestionInPublishedPaperException

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

🧪 Sample Test Flow (Console)

Add a Question

Create Test Paper with difficulty mix

Publish Test Paper

Attempt question removal

Archive Test Paper

📊 Status Lifecycle
Question Status

ACTIVE

INACTIVE

Test Paper Status

DRAFT → PUBLISHED → ARCHIVED

⚠️ Custom Exceptions Used

ValidationException

QuestionPoolInsufficientException

QuestionInPublishedPaperException

These ensure strict validation and exam integrity.

🎯 Key Concepts Demonstrated

JDBC Connection Handling

Transaction Management

DAO Pattern

Exception Handling

Blueprint-based Paper Assembly

Data Integrity Enforcement

Random Question Selection Logic

Layered System Design

▶️ How to Run

Configure Oracle DB credentials inside DBUtil

Create tables using SQL

Compile project

Run ExamMain

Test operations via console

📌 Sample Use Case

Create a test paper with difficulty mix:

Paper Title: DBMS Practice Test
Subject: DBMS
Total Marks: 10
Difficulty Mix: EASY=4,MEDIUM=6,HARD=0


System validates:

Enough ACTIVE questions exist

Marks match blueprint

Paper stored in DRAFT state
