# Mini Core Banking System (CBS) – Spring Boot

A **Spring Boot–based Core Banking System (CBS)** backend project implementing role-based access for **Admin, Branch Manager, Staff, and Customers**, providing core features such as branch creation, account management, and banking transactions.

## 🚀 Features

### 👨‍💼 Roles
- **Admin**
  - Registers himself
  - Creates banks and their branches
  - Creates managers and staff accounts
- **Branch Manager / Staff**
  - Created by Admin
  - Manage branch operations
  - Onboard customers
  - Handle customer accounts
- **Customer**
  - Created by Manager/Staff
  - Perform deposits, withdrawals, balance inquiries
  - View transaction history

### 🏦 Core Banking Operations
- Bank & branch management
- User and role management
- Customer onboarding
- Account creation (savings, current, etc.)
- Deposits, withdrawals, and transfers
- Transaction history

## 🛠️ Tech Stack
- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Database:** Oracle SQL
- **Authentication:** JWT-based authentication and authorization
- **Build Tool:** Maven
- **Logging:** ELK Stack

## 📂 Project Structure
```
├── MiniCBS
      ├── action-service                  # All data manipulation APIs
      ├── query-service                   # All read APIs
      ├── eureka-server                   # Service Regisrty System
      ├── security-service                # JWT Authentication
      └── README.md                       # Documentation

````

## ⚙️ Installation & Setup
1. Clone the repository  
   ```bash
   git clone https://github.com/asharkhan11/MiniCBS.git
   cd MiniCBS
````

2.1 Configure database in `application.yml`

```yml

spring:
  config:
    import: "classpath:variables.yml"
  datasource:
    url: jdbc:oracle:thin:@//localhost:1521/xe
    username: ${db.username}
    password: ${db.password}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

   ````

2.2 `variables.yml` for credentials

```yml
db:
  username: usernameOfOracleDB
  password: passwordOfOracleDB

````

3. Build and run the project

   ```bash
   mvn spring-boot:run
   ````

## ✅ API Endpoints (Sample)

* **Auth & Roles**

  * `POST /api/auth/register-admin` – Register an Admin
  * `POST /api/auth/login` – Login (JWT token)
* **Admin Operations**

  * `POST /api/bank` – Create Bank
  * `POST /api/branch` – Create Branch
  * `POST /api/staff` – Create Staff/Manager
* **Customer Operations**

  * `POST /api/customer` – Register Customer
  * `POST /api/account` – Create Account
  * `POST /api/transaction/deposit` – Deposit Money
  * `POST /api/transaction/withdraw` – Withdraw Money
  * `GET /api/transaction/history/{accountId}` – View Transactions

## 📌 Future Enhancements

* Loan and interest calculations
* Multi-currency support
* Integration with external payment gateways
* Frontend (Angular/React) for UI

## 🧑‍💻 Contributors

* Abhay Badwaik
* Shreyash Choudhari
* Ashar Khan

## 📄 Summary

This **Mini Core Banking System (CBS)** backend, built with **Spring Boot**, demonstrates a **role-based banking application** where **Admins** create banks, branches, and staff, while **Managers/Staff** manage customer accounts and transactions. Customers can deposit, withdraw, and track balances, making it a complete simulation of core banking functionalities.
````
