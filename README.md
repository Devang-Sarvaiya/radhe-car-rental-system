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
- shared CRUD and data operations across applications
- booking and vehicle-management workflows
- live-location functionality
- Razorpay payment integration
- debugging and integration across the connected applications
- supporting coordination between multiple application modules

This repository represents the team project as a whole, while the responsibilities listed above describe the areas I personally worked on.

---

## 🏗️ System Architecture

RADHE is not a single Android application with multiple roles.

It consists of **four independent Android Studio applications**, each with its own package, Gradle configuration, manifest, resources, and application logic.

```text
                    ┌─────────────────────┐
                    │   Shared Firebase   │
                    │      Backend        │
                    │                     │
                    │ Authentication      │
                    │ Realtime Database   │
                    │ Cloud Storage       │
                    │ Cloud Messaging     │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              │                │                │
      ┌───────▼───────┐ ┌──────▼──────┐ ┌──────▼──────┐
      │   User App    │ │ Renter App  │ │  Admin App  │
      └───────────────┘ └─────────────┘ └─────────────┘
                               │
                        ┌──────▼──────┐
                        │ Driver App │
                        └─────────────┘
