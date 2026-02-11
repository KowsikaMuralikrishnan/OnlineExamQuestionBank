# 📘 Online Exam Question Bank & Test Paper Assembly System (JDBC)

## 📌 Overview

The **Online Exam Question Bank & Test Paper Assembly System** is a console-based Java application developed using **Core Java, JDBC, and Oracle Database**.

This system allows an examination cell to:

- Maintain a reusable question bank
- Assemble test papers using difficulty-based blueprints
- Publish and archive test papers
- Protect the integrity of published exams
- Manage questions transactionally

The application follows a clean **Layered MVC Architecture (Bean → DAO → Service → Controller)**.

---

## 🚀 Features

### 🔹 Question Management
- Add New Question
- View Question Details
- View All Questions
- Remove Question (with validation)

### 🔹 Test Paper Management
- Create Test Paper (Transactional)
- Publish Test Paper (Transactional)
- Archive Test Paper

### 🔹 Validation & Integrity
- Prevent deletion of questions used in published papers
- Prevent publishing invalid test papers
- Validate blueprint difficulty mix
- Ensure enough ACTIVE questions exist before paper creation

---

## 🛠️ Technologies Used

- Java (Core Java)
- JDBC
- Oracle Database
- SQL
- Console-based UI
- MVC Architecture

---

## 🗄️ Database Setup (Oracle)

### Step 1: Create a New User

```sql
sqlplus / as sysdba

CREATE USER exam_user IDENTIFIED BY exam123;
GRANT CONNECT, RESOURCE TO exam_user;
COMMIT;
EXIT;
⚠️ Do NOT use the default scott/tiger account.

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
Example difficulty mix:

Paper Title: DBMS Practice Test
Subject: DBMS
Total Marks: 10
Difficulty Mix: EASY=4,MEDIUM=6,HARD=0
System will:

Validate inputs

Check question availability

Select questions

Store paper as DRAFT

Allow publishing after validation

▶️ How to Run
Configure Oracle DB credentials in DBUtil.java

Create database tables

Compile the project

Run ExamMain

Perform operations via console

🎯 Key Concepts Demonstrated
JDBC Connection Handling

Transaction Management

DAO Pattern

Exception Handling

Blueprint-based Paper Assembly

Data Integrity Enforcement

Layered System Design
