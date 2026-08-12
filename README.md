# RADHE — Car Rental System

An Android car rental platform built as a BCA (Bachelor of Computer Application) final-year
college project. It connects three kinds of participants — **users** who book cars,
**renters** who list cars for rent, and **admins** who oversee the whole system — with a
fourth **driver** app handling pickup/drop-off logistics. All four apps share a single
Firebase backend.

This repository is a cleaned-up and reorganized version of that original 2023-24 academic
project, prepared for archival and portfolio purposes. See [Current Status](#current-status)
below for an honest note on what that does and doesn't mean.

## Overview

The Car Rental System application is a comprehensive platform designed to cater to the needs
of users, renters, and administrators in the car rental industry.

- **Users** create accounts, log in via OTP, browse and search available vehicles, view
  detailed listings, save favorites, and book a car for a chosen date range with a pickup
  location. Booking, payment, and PDF receipt generation are all part of the flow.
- **Renters** list their vehicles with descriptions, images, and rental prices; manage
  availability; and respond to booking requests from users.
- **Admins** oversee the whole system — verifying renters and users, managing the car
  catalog, drivers, bookings, and payments, and monitoring live location.
- **Drivers** (a separate app) handle pickup-point verification via OTP and report trip
  status.

## Features

**User app**
- OTP-based signup/login and profile management
- Browse, search, and filter available cars
- Favorites / saved cars
- Booking flow: confirm, extend, or cancel a booking; date-range selection with automatic
  rent calculation
- Live location sharing
- In-app chatbot for support queries
- Feedback submission and booking history
- Razorpay-based payment, with a generated PDF receipt/invoice
- Safety-instructions and tourism-info sections

**Renter app**
- OTP-based signup/login and profile management
- Add/manage car listings (details, images, pricing, availability)
- Manage "gadget" add-ons available for rent
- Confirm, extend, or cancel bookings from users
- Driver management
- Booking history and payments
- Email-based OTP delivery and driver-credential email delivery

**Admin app**
- OTP-based login
- Verify and manage renter and user accounts
- Manage the car catalog, drivers, and bookings across the platform
- Track and manage payments
- SMS-based OTP and login-notification system for a fixed set of admin phone numbers
- View live location data
- Push notifications to users/renters (Firebase Cloud Messaging)

**Driver app**
- OTP-based login (SMS)
- Pickup-point verification flow
- Live location reporting
- Trip/availability status updates
- Booking history

## Technology Stack

- **Language:** Java
- **Platform:** Android (compileSdk 34, minSdk 24, targetSdk 34)
- **Build system:** Gradle 8.4 / Android Gradle Plugin 8.3.x, Kotlin DSL (`build.gradle.kts`)
- **Backend:** Firebase — Authentication, Realtime Database, Cloud Storage, Cloud Messaging
- **Payments:** Razorpay Android SDK
- **Maps & Location:** Google Maps / Play Services Location, OSMDroid
- **PDF generation:** iText7
- **Email:** JavaMail (`com.sun.mail:android-mail`) for OTP/credential delivery over SMTP
- **Networking:** Retrofit + OkHttp
- **Images:** Glide, Picasso, CircleImageView
- **UI:** Facebook Shimmer (loading placeholders), Material Components

## Architecture

The project is **four independent Android Studio applications** that share one Firebase
project as a common backend, rather than a single app with role-based views:

```
admin-app/     Admin dashboard app       (package: com.example.car_admin)
renter-app/    Renter app                (package: com.example.caronrentrenter)
user-app/      User / customer app       (package: com.example.caronrent)
driver-app/    Driver / pickup-point app (package: com.example.driver_module)
```

Each is a standalone, independently buildable Gradle project with its own `app/` module,
manifest, and `google-services.json`. They talk to each other only indirectly, through
shared Firebase Realtime Database nodes and Cloud Messaging.

## Project Structure

```
.
├── README.md
├── .gitignore
├── documentation/
│   ├── project-report.pdf           # Full academic project report (BCA final year, 2023-24)
│   ├── project-presentation.pdf     # Project presentation slide deck
│   └── original-commit-history.md   # Preserved git history from two nested repos found during cleanup
├── screenshots/                     # App screenshots
├── admin-app/                       # Admin Android Studio project
├── renter-app/                      # Renter Android Studio project
├── user-app/                        # User Android Studio project
└── driver-app/                      # Driver Android Studio project
```

Each `*-app/` follows the standard Android Studio layout:
`app/src/main/java/...`, `app/src/main/res/...`, `app/src/main/AndroidManifest.xml`,
`build.gradle.kts`, `settings.gradle.kts`, `gradlew`/`gradlew.bat`.

## Setup / Configuration

This repository does **not** include any real credentials or private configuration. To
attempt to run any of the four apps, you will need to supply your own:

1. **Firebase**: create a Firebase project, enable Authentication, Realtime Database, Storage,
   and Cloud Messaging, and download your own `google-services.json` for each app you want to
   run, replacing the placeholder file at `<app>/app/google-services.json`.
2. **Razorpay**: create a Razorpay account and replace `YOUR_RAZORPAY_KEY_ID` in each
   `Main_Payment.java` / `Extend_new_payment.java` with your own Key ID.
3. **Google Maps**: replace `YOUR_GOOGLE_MAPS_API_KEY` in the relevant `AndroidManifest.xml`
   files with your own Maps API key.
4. **Email (OTP delivery)**: replace `YOUR_EMAIL@gmail.com` / `YOUR_GMAIL_APP_PASSWORD` in
   `DriverEmail.java` / `EmailSender.java` with your own Gmail address and a
   [Gmail App Password](https://support.google.com/accounts/answer/185833).
5. **OpenAI (chatbot)**: replace `YOUR_OPENAI_API_KEY` in `ChatMain.java` (renter-app,
   user-app) with your own OpenAI API key.
6. **SMS (admin/driver OTP)**: `SMS.java` (admin-app) and `Pick_upOTP.java` (driver-app) use
   the Android `SmsManager` API with a hardcoded allowlist of phone-number placeholders —
   replace with real numbers if you want to exercise that flow.

## Current Status

This project was originally developed as a college final-year project (academic year
2023-24, TYBCA Sem-VI, Sutex Bank College of Computer Applications & Science, Amroli, Surat,
affiliated to Veer Narmad South Gujarat University). The repository contains the original
implementation, cleaned and reorganized for archival and portfolio purposes.

The Firebase backend it originally used is no longer configured or accessible, dependencies
are roughly two years old, and the build has **not** been verified against current Android
SDK / Gradle / Firebase tooling. Some external services or dependencies may require
additional configuration — or may no longer work as-is — with current environments. This
should be read as an archival snapshot of the original work, not a maintained or
production-ready application.

## Known Limitations

- The project is approximately two years old and was **not** modernized — it was cleaned up,
  not rewritten or upgraded.
- The four Android applications retain their original architecture (four separate apps
  sharing one Firebase project, rather than a unified codebase).
- Android package names (`com.example.*`) were intentionally left unchanged — renaming them
  would touch every file, the manifest, and Firebase's registered app IDs, with no way to
  verify the result builds.
- Large-scale renaming of default/auto-generated Android view IDs (e.g. `textView2`,
  `button2`) across layout XML files was intentionally **not** performed — hundreds of
  instances across all four apps, low value, high risk without a working build to verify
  against.
- A small number of duplicate/near-duplicate classes exist (e.g. `All_cars.java` /
  `All_cars1.java`, `ImageModel.java` / `ImageModel1.java`) and a `For_new_Project` package
  (poorly named, but confirmed to contain live, manifest-declared, reachable screens — not
  scratch code) — these were preserved as-is since their exact intended usage couldn't be
  confidently determined without a working build.
- `Demo.java` / `Demo2.java` (renter-app) look like throwaway scratch files by name, but
  `Demo2` is actually launched from `Date_Book.java` and both are declared in the manifest —
  they were kept unchanged rather than removed or renamed.
- Firebase and Razorpay functionality has **not** been verified as working — the original
  backend project is inaccessible and no build/run test was performed.
- This should be read as an archival/portfolio cleanup of existing work, not a newly
  developed or production-ready application.

## Original Authors

Developed by Devang Sarvaiya, Meet Ramani, and team under the guidance of Dr. Jaimin H. Shukla, as a TYBCA final-year project
(academic year 2023-24) at Sutex Bank College of Computer Applications & Science, Amroli,
Surat — affiliated to Veer Narmad South Gujarat University.
