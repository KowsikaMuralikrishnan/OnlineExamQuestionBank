# 📘 Online Exam Question Bank & Test Paper Assembly System (JDBC)

---

## 📌 Overview

The **Online Exam Question Bank & Test Paper Assembly System** is a console-based Java application developed using **Core Java, JDBC, and Oracle Database**.

This system allows an examination cell to:

- Maintain a reusable **question bank**
- Assemble test papers using **difficulty-based blueprints**
- Publish and archive **test papers**
- Protect the integrity of **published exams**
- Manage questions using **database transactions**

The project follows a clean **Layered MVC Architecture**:

**Bean → DAO → Service → Controller**

---

## 🚀 Features

### 🔹 Question Management

- **Add** new question  
- **View** question details  
- **View all** questions  
- **Remove** question (with validation)

### 🔹 Test Paper Management

- **Create Test Paper** (Transactional Operation)  
- **Publish Test Paper** (Transactional Operation)  
- **Archive Test Paper**

### 🔹 Validation & Data Integrity

- Prevent deletion of questions used in **PUBLISHED** papers  
- Prevent publishing invalid test papers  
- Validate **blueprint difficulty distribution**  
- Ensure sufficient **ACTIVE** questions before paper creation  

---

## 🛠 Technologies Used

- **Java (Core Java)**
- **JDBC**
- **Oracle Database**
- **SQL**
- **Console-Based UI**
- **MVC Architecture**

---

## 📂 Project Structure

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


---

## 🧠 System Architecture

The application follows a **Layered Architecture Pattern**:

| **Layer**             | **Responsibility**                      |
|-----------------------|-----------------------------------------|
| **Bean Layer**        | Represents database entities            |
| **DAO Layer**         | Handles database CRUD operations        |
| **Service Layer**     | Contains business logic and validations |
| **Controller Layer**  | Manages console interaction             |

---

## 🔄 Transactional Operations

The following operations are executed inside **database transactions**:

- **Create Test Paper**
- **Publish Test Paper**

Auto-commit is disabled to ensure:

- **Data Consistency**
- **Atomic Operations**
- **Rollback on Failure**

---

## 📋 Business Rules Enforced

- Question ID must be **unique**
- Difficulty must be **EASY / MEDIUM / HARD**
- Marks must be **positive**
- Cannot publish a **non-DRAFT** paper
- Cannot delete a question used in a **PUBLISHED** paper
- Must have enough **ACTIVE** questions to create paper
- Blueprint total must match **required total marks**

---

## 📊 Status Lifecycle

### Question Status
- **ACTIVE**
- **INACTIVE**

### Test Paper Status
**DRAFT → PUBLISHED → ARCHIVED**

---

## 🧪 Sample Use Case

**Example Blueprint**

- **Paper Title:** DBMS Practice Test  
- **Subject:** DBMS  
- **Total Marks:** 10  
- **Difficulty Mix:** EASY = 4, MEDIUM = 6, HARD = 0  

**System Workflow**

1. Validate inputs  
2. Check question availability  
3. Select questions  
4. Store paper as **DRAFT**  
5. Allow publishing after validation  

---

## ▶️ How to Run

1. Configure database credentials in **DBUtil.java**  
2. Create required database tables  
3. Compile the project  
4. Run **ExamMain**  
5. Perform operations via console  

---

## 🖥 Sample Output


<img width="1617" height="264" alt="image" src="https://github.com/user-attachments/assets/49dc34da-985c-4e0c-9faa-266a3e391f90" /> 

---

## 🎯 Key Concepts Demonstrated

- **JDBC Connection Handling**
- **Transaction Management**
- **DAO Pattern**
- **Custom Exception Handling**
- **Blueprint-Based Paper Assembly**
- **Data Integrity Enforcement**
- **Layered System Design**
