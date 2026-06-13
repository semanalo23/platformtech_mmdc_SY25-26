# Project Finer FinMark (Prototype)

Welcome to the repository for **Project Finer FinMark**. This prototype is a scalable and secure online order placement system designed to address FinMark Corporation's growth targets. It transitions the legacy system into a robust architecture capable of handling increased order volumes seamlessly.

## 🚀 Tech Stack

- **Backend:** Java / Spring Boot (REST API, Security)
- **Frontend:** React.js (User Interface)
- **Build Tools:** Maven (Backend) & npm (Frontend)

---

## 🔐 Testing Credentials

For grading and testing purposes, you can use the following pre-configured accounts to explore the different user roles and access controls built into the application:

| Role | Username | Password | Access Level |
| :--- | :--- | :--- | :--- |
| **Regular User** | `testuser` | `password123` | Can place orders and view history |
| **Administrator** | `admin` | `admin123` | Full access to system dashboards |

---

## 🛠️ Getting Started

Follow these steps to clone, set up, and run the prototype locally on your machine.

### Prerequisites
Make sure you have the following installed:
- [Java JDK 17 or higher](https://www.oracle.com/java/technologies/downloads/)
- [Node.js (v18 or higher)](https://nodejs.org/)
- [Git](https://git-scm.com/)

---

### 1. Backend Setup (Spring Boot)

1. Open your terminal and navigate to the backend directory:
   ```bash
   cd backend
Build the project and download the required Maven dependencies:

Bash
./mvnw clean install
(On Windows, use mvnw clean install)

Run the Spring Boot application:

Bash
./mvnw spring-boot:run
The backend server should now be running locally (typically on http://localhost:8080).

2. Frontend Setup (React)
Open a new terminal window and navigate to the frontend directory:

Bash
cd frontend
Install the necessary project dependencies:

Bash
npm install
Start the local development server:

Bash
npm start
The frontend application will automatically open in your default browser at http://localhost:3000.

👥 Group Members (Group 2 - H3101)
Sire Edmond Manalo
Vladimir Bernardo
Chelsea Jin Collado
Jason Bryan Tan
Ron Carlo Dioso

Developed for IT151 - Platform Technologies (Milestone 2 Project Prototype).
