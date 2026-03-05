# SonarCloud Demo — Spring Boot Student API

A Spring Boot Student Management REST API integrated with **SonarCloud** static code analysis via **GitHub Actions**.

---

## 🛠️ Tech Stack

Java 17 · Spring Boot 3.2.5 · H2 In-Memory DB · JUnit 5 · Mockito · JaCoCo · SonarCloud · GitHub Actions

---

## 🚀 Run Locally

**Prerequisites:** Java 17+, Maven 3.8+

```bash
git clone https://github.com/ArjunLatiwala/SonarCloud-test.git
cd SonarCloud-test
mvn spring-boot:run
```

App starts at `http://localhost:8080` · H2 Console at `http://localhost:8080/h2-console`

---

## 🧪 Test the API

> ⚠️ Opening `http://localhost:8080/api/` in the browser shows a **Whitelabel Error** — that's normal. Use the full endpoint URLs below.

**In your browser (GET requests):**
```
http://localhost:8080/api/students
```

**To add a student, use [Postman](https://postman.com):**
1. Open Postman → New Request → **POST**
2. URL: `http://localhost:8080/api/students`
3. Click **Body** → **raw** → **JSON**, paste:
```json
{
  "name": "Arjun",
  "email": "arjun@example.com",
  "age": 21,
  "course": "Computer Science"
}
```
4. Click **Send** → then visit `http://localhost:8080/api/students` to see the result

**All available endpoints:**

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create a student |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |
| GET | `/api/students/grade/{score}` | Get grade for a score |

---

## ☁️ Setup SonarCloud with GitHub Actions

### Step 1: Fork this repo
Click **Fork** on the top right.

### Step 2: Create a SonarCloud account
Go to [sonarcloud.io](https://sonarcloud.io) → **Login with GitHub** → **"Analyze new project"** → select your fork → choose **GitHub Actions** → copy the `SONAR_TOKEN`.

### Step 3: Add secret to GitHub
Your repo → **Settings** → **Secrets and variables** → **Actions** → **New secret**
- Name: `SONAR_TOKEN` · Value: *(paste token from SonarCloud)*

### Step 4: Update `pom.xml`
```xml
<sonar.organization>YOUR_GITHUB_USERNAME</sonar.organization>
<sonar.projectKey>YOUR_GITHUB_USERNAME_SonarCloud-test</sonar.projectKey>
```
> 💡 SonarCloud shows you the exact values during project setup.

### Step 5: Update `.github/workflows/build.yml`
```yaml
run: mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=YOUR_GITHUB_USERNAME_SonarCloud-test
```

### Step 6: Push and view results
```bash
git add .
git commit -m "Configure SonarCloud"
git push origin main
```
Go to the **Actions** tab on GitHub to watch it run, then view results on [sonarcloud.io](https://sonarcloud.io) 🎉

---

## 📊 What SonarCloud Reports

- 🐛 Bugs · 🔒 Vulnerabilities · 🔧 Code Smells · 📊 Coverage · 🔁 Duplications
