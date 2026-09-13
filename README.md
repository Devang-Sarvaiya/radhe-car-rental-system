# RADHE — Multi-App Car Rental Platform

RADHE is a **four-application Android car-rental ecosystem** built with **Java and Firebase**, connecting customers, vehicle renters, administrators, and drivers through a shared backend.

The platform supports vehicle discovery, booking, renter-managed listings, account verification, payments, live location, driver coordination, notifications, and administrative management.

The system consists of four independently developed Android applications:

- **User App**
- **Renter App**
- **Admin App**
- **Driver App**

All four applications communicate indirectly through a shared Firebase backend.

---

## 👨‍💻 My Contribution

I worked as the **lead developer within a six-member team**, with particular responsibility for shared application data and Firebase integration across the connected applications.

My work included:

- Firebase Realtime Database integration
- Firebase Cloud Storage integration
- Shared CRUD and data operations across applications
- Booking and vehicle-management workflows
- Live-location functionality
- Razorpay payment integration
- Debugging and integration across the connected applications
- Supporting coordination between multiple application modules

This repository represents the team project as a whole, while the responsibilities listed above describe the areas I personally worked on.

---

## 🏗️ System Architecture

RADHE is not a single Android application with multiple role-based views.

Instead, it consists of **four independent Android Studio applications**, each with its own package, Gradle configuration, manifest, resources, and application logic.

```text
                    ┌─────────────────────┐
                    │   Shared Firebase   │
                    │       Backend       │
                    │                     │
                    │ Authentication      │
                    │ Realtime Database   │
                    │ Cloud Storage       │
                    │ Cloud Messaging     │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
      ┌───────────────┐ ┌──────────────┐ ┌──────────────┐
      │   User App    │ │  Renter App  │ │  Admin App   │
      └───────────────┘ └──────────────┘ └──────────────┘
                               │
                               ▼
                        ┌──────────────┐
                        │  Driver App  │
                        └──────────────┘
```

The applications communicate through shared Firebase data and Cloud Messaging rather than through direct application-to-application connections.

---

## 🚀 Main Features

### User App

- OTP-based signup and login
- Profile management
- Browse, search, and filter available vehicles
- View detailed vehicle listings
- Save favorite vehicles
- Book vehicles for selected date ranges
- Automatic rental-price calculation
- Confirm, extend, or cancel bookings
- Live-location sharing
- Booking history
- Feedback submission
- Razorpay payment integration
- PDF receipt/invoice generation
- In-app support chatbot
- Safety-information section
- Tourism-information section

---

### Renter App

- OTP-based signup and login
- Profile management
- Add and manage vehicle listings
- Upload vehicle details and images
- Manage pricing and availability
- Manage rentable gadget/add-on options
- Review booking requests
- Confirm, extend, or cancel bookings
- Driver management
- Booking history
- Payment-related functionality
- Email-based OTP delivery
- Driver credential delivery through email

---

### Admin App

- OTP-based login
- Verify and manage renter accounts
- Verify and manage user accounts
- Manage vehicle catalog
- Manage drivers
- Manage bookings
- Monitor payment-related information
- View live-location data
- SMS-based OTP functionality
- Login notification functionality
- Firebase Cloud Messaging notifications

---

### Driver App

- OTP-based login
- Pickup-point verification
- Live-location reporting
- Trip-status updates
- Availability-status updates
- Booking history

---

## 🛠️ Technology Stack

### Core

- **Language:** Java
- **Platform:** Android
- **Build System:** Gradle / Android Gradle Plugin

### Backend

- Firebase Authentication
- Firebase Realtime Database
- Firebase Cloud Storage
- Firebase Cloud Messaging

### Payments

- Razorpay Android SDK

### Maps & Location

- Google Maps
- Google Play Services Location
- OSMDroid

### Networking

- Retrofit
- OkHttp

### Documents

- iText7 for PDF generation

### Email

- JavaMail / Android Mail
- SMTP-based email delivery

### Images & UI

- Glide
- Picasso
- CircleImageView
- Material Components
- Facebook Shimmer

---

## 📂 Repository Structure

```text
.
├── README.md
├── .gitignore
├── documentation/
│   ├── project-report.pdf
│   ├── project-presentation.pdf
│   └── original-commit-history.md
├── screenshots/
├── admin-app/
├── renter-app/
├── user-app/
└── driver-app/
```

Each application follows a standard Android Studio structure:

```text
app/src/main/java/
app/src/main/res/
app/src/main/AndroidManifest.xml
build.gradle.kts
settings.gradle.kts
gradlew
gradlew.bat
```

The four Android projects are:

```text
admin-app/     Admin dashboard application
renter-app/    Vehicle renter application
user-app/      Customer / user application
driver-app/    Driver / pickup application
```

The original package names are:

```text
Admin App:
com.example.car_admin

Renter App:
com.example.caronrentrenter

User App:
com.example.caronrent

Driver App:
com.example.driver_module
```

---

## 📱 Screenshots

Application screenshots are available in the [`screenshots/`](screenshots/) directory.

The screenshots document representative parts of the:

- User application
- Renter application
- Admin application
- Driver application

They are included to provide a visual overview of the original application interfaces and workflows.

---

## ⚙️ Setup / Configuration

This repository intentionally does **not** contain real credentials, private configuration, production API keys, passwords, or active secrets.

To attempt to run the applications, you will need to configure your own external services.

### 1. Firebase

Create your own Firebase project and enable the required services:

- Authentication
- Realtime Database
- Cloud Storage
- Cloud Messaging

Download your own `google-services.json` files and place them in the corresponding Android application modules.

Example location:

```text
<app-name>/app/google-services.json
```

The placeholder Firebase configuration included for repository structure should not be treated as an active backend configuration.

---

### 2. Razorpay

Create your own Razorpay account and replace the placeholder API key values in the relevant payment classes.

Relevant files include classes such as:

```text
Main_Payment.java
Extend_new_payment.java
```

Replace placeholder values such as:

```text
YOUR_RAZORPAY_KEY_ID
```

with your own valid Razorpay Key ID.

---

### 3. Google Maps

Create and configure your own Google Maps API key.

Replace the placeholder Maps configuration inside the relevant Android manifest files.

Do not commit a real unrestricted API key to a public repository.

---

### 4. Email / SMTP

The project contains email functionality for flows such as:

- OTP delivery
- Driver credential delivery

Relevant classes include files such as:

```text
DriverEmail.java
EmailSender.java
```

Replace placeholders such as:

```text
YOUR_EMAIL@gmail.com
YOUR_GMAIL_APP_PASSWORD
```

with your own supported configuration.

Use environment-specific or secure configuration where possible.

---

### 5. OpenAI Chatbot

The user and renter applications contain chatbot-related functionality.

Relevant code includes classes such as:

```text
ChatMain.java
```

Replace the placeholder:

```text
YOUR_OPENAI_API_KEY
```

with your own valid configuration if you want to test that functionality.

No private OpenAI API key is included in this repository.

---

### 6. SMS

The Admin and Driver applications contain SMS-based OTP functionality using Android's SMS capabilities.

Relevant classes include:

```text
SMS.java
Pick_upOTP.java
```

The original implementation used placeholder or project-specific phone-number handling.

Replace those values with your own configuration before attempting to test SMS-related workflows.

---

## 🔐 Security Notes

This public repository was cleaned so that private credentials and secrets are not intentionally included.

Before using or deploying the project:

- use your own Firebase project;
- use your own API keys;
- use your own payment configuration;
- use your own email configuration;
- use your own SMS configuration;
- avoid hardcoding production credentials;
- restrict third-party API keys appropriately;
- use secure configuration management for production environments.

The repository is intended primarily for **portfolio and archival purposes**, not direct production deployment.

---

## 🎓 Project Context

RADHE was originally developed as a **BCA final-year team project during the 2023–24 academic year**.

The project was developed at:

**Sutex Bank College of Computer Applications & Science**  
Amroli, Surat, Gujarat, India

The institution is affiliated with:

**Veer Narmad South Gujarat University**

The original project was developed as part of the TYBCA Semester VI academic work.

The repository has since been cleaned and reorganized so that the technical implementation can be presented more clearly for portfolio and archival purposes.

---

## 📌 Current Status

This repository contains the original project implementation after cleanup and reorganization.

The original Firebase backend used during development is no longer configured or accessible.

The project has **not been fully rebuilt or verified against the latest versions of Android, Gradle, Firebase, Razorpay, Google Maps, or other external services**.

For that reason, this repository should be understood as a:

**portfolio and archival representation of the original system**

rather than a currently maintained production application.

The cleanup focused on:

- organizing the four application projects;
- removing or replacing private configuration where appropriate;
- documenting the architecture and features;
- preserving the original implementation;
- making the project easier to inspect publicly;
- avoiding risky large-scale code changes that could not be properly validated.

---

<details>
<summary><strong>Known Limitations and Archival Notes</strong></summary>

<br>

The project is approximately two years old and has not been comprehensively modernized.

### Original Architecture

The four original Android applications remain separate applications sharing one Firebase backend.

They were not rewritten into:

- a unified Android project;
- a multi-module application;
- a role-based single application;
- a modernized clean-architecture implementation.

The original architecture has been intentionally preserved.

### Android Package Names

Original package names such as:

```text
com.example.*
```

were intentionally preserved.

Changing package names would require coordinated changes across:

- Java source files
- manifests
- Gradle configuration
- Firebase application IDs
- external service configuration

Without access to the original active backend and a verified build environment, large-scale renaming would create unnecessary risk.

### Android View IDs

Some original Android layout identifiers remain generic, for example:

```text
textView2
button2
```

Large-scale renaming was intentionally avoided because these IDs may be referenced across:

- Java code
- XML layouts
- event handlers
- application flows

Changing hundreds of identifiers without a complete working environment would provide limited portfolio value while increasing the risk of breaking behaviour.

### Duplicate / Near-Duplicate Classes

A small number of duplicate or near-duplicate classes exist in the original implementation.

Examples include names such as:

```text
All_cars.java
All_cars1.java
ImageModel.java
ImageModel1.java
```

These were preserved where their exact intended runtime purpose could not be confidently changed without full application testing.

### Legacy Package Naming

Some historically named packages remain in the codebase.

For example:

```text
For_new_Project
```

Although the package name is not ideal, it contains application screens that were found to be reachable and/or declared within the original project.

For that reason, it was preserved rather than removed simply based on naming.

### Demo-Named Classes

Classes such as:

```text
Demo.java
Demo2.java
```

may appear temporary based on their names.

However, during repository cleanup, code inspection showed that at least some of these classes were connected to actual application flows.

For example, `Demo2` is launched from application logic and declared within the Android manifest.

They were therefore preserved rather than deleted based only on naming.

### External Services

The original Firebase backend is no longer available.

As a result, functionality dependent on Firebase has not been revalidated.

Similarly, functionality depending on services such as:

- Razorpay
- Google Maps
- OpenAI APIs
- SMTP email
- SMS delivery

may require current configuration and dependency updates.

### Build Verification

A complete build and runtime verification of all four applications has not been performed against current Android and third-party tooling.

Dependencies may require updating before the applications can successfully compile or run in a modern environment.

### Repository Purpose

This repository should therefore be understood as:

- an archival snapshot of the original implementation;
- a documented portfolio project;
- evidence of the system architecture and development work;

and **not** as a newly developed, actively maintained, or production-ready application.

</details>

---

## 👥 Original Project Team

The project was developed by:

**Devang Sarvaiya, Meet Ramani, and team**

under the guidance of:

**Dr. Jaimin H. Shukla**

### Academic Information

**Academic Year:** 2023–24  
**Program:** TYBCA Semester VI  
**Institution:** Sutex Bank College of Computer Applications & Science  
**Location:** Amroli, Surat, Gujarat, India  
**University:** Veer Narmad South Gujarat University

---

## 📄 Documentation

Additional project material is available inside the [`documentation/`](documentation/) directory.

The repository includes:

- **Full Academic Project Report**
- **Project Presentation**
- **Original Commit-History Documentation**

Example structure:

```text
documentation/
├── project-report.pdf
├── project-presentation.pdf
└── original-commit-history.md
```

These documents provide additional historical and academic context for the original project.

---

## 🧭 Portfolio Positioning

RADHE demonstrates practical experience with:

- Java application development
- Android development
- multi-application system design
- Firebase integration
- shared application data
- authentication workflows
- booking workflows
- payment integration
- mobile location functionality
- application debugging
- team-based software development

The project represents an important part of my earlier software-engineering work and complements my current focus on **backend, cloud, and AI-assisted software systems**.
