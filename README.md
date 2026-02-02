# GeoFence Tracker (Android Native Assignment)

## Overview

GeoFence Tracker is an Android native application built using **Kotlin, XML, and Room DB** that demonstrates the use of **Android’s Geofencing API** to monitor user entry and exit from predefined geographic locations. The app calculates the duration spent inside geofenced areas, stores visit history locally, and notifies users in real time.

The project focuses on **practical Android fundamentals** such as background execution, location services, persistence, and clean multi-screen UI design.

---

## Key Objectives of the Assignment

* Implement **location-based entry and exit detection** using native Android APIs
* Track and calculate **time spent inside geofenced locations**
* Persist data reliably using **Room Database**
* Ensure functionality works on **Android 10 and above**, including background scenarios
* Present data clearly across **2–3 minimal screens**

---

## Features Implemented

### 1. Geofence Creation & Monitoring

* Geofences are created using **Android’s GeofencingClient** with configurable radius (10–50 meters).
* Each geofence is uniquely identified and persisted locally so it remains active even after app restart.

### 2. Entry & Exit Detection

* A **BroadcastReceiver** listens for geofence transition events (`ENTER` and `EXIT`) triggered by the Android system.
* Entry and exit events are detected even when the app is killed or running in the background.

### 3. Duration Calculation

* Entry time is recorded on geofence entry.
* Duration is calculated on exit using system timestamps (`exitTime - entryTime`).

### 4. Local Data Persistence

* **Room Database** is used to store:

  * Geofence configuration (name, latitude, longitude, radius, created time)
  * Visit history (entry time, exit time, duration)
* All data is stored offline and survives app restarts.

### 5. Visit History & Geofence List

* **RecyclerView** is used to display:

  * List of configured geofences
  * List of recent visits with date, entry time, exit time, and duration
* Data is fetched directly from Room DB and rendered in list-based screens.

### 6. Notifications

* **Android NotificationManager** is used to notify users when:

  * Entering a geofenced area
  * Exiting a geofenced area
* Notifications work even when the app is in the background.

---

## Screen Breakdown

### Screen 1: Geofence Setup (Intended)

* Map-based UI to visually add geofences using long-press or tap.
* Each geofence has a custom name and radius.

>  **Note:** Map-based visualization requires Google Maps SDK.

### Screen 2: Geofence List

* Displays all saved geofences with:

  * Location name
  * Latitude & longitude
  * Radius
  * Creation date & time

### Screen 3: Visit History

* Displays visit records with:

  * Geofence name
  * Date
  * Entry time
  * Exit time
  * Duration spent

---

## Android Features & APIs Used

* **Geofencing API (GeofencingClient)** – for entry/exit detection
* **BroadcastReceiver** – to receive geofence transitions
* **Room Database** – local persistent storage
* **RecyclerView** – list-based UI rendering
* **NotificationManager** – entry/exit notifications
* **Kotlin Coroutines** – background operations

---

## Android Version Compatibility

* Minimum Supported Version: **Android 10 (API 29)**
* Designed to comply with background execution and location limits introduced in Android 10+

---

## Important Note on Google Maps API Key

The assignment specifies a **map-view** for adding geofenced locations. On Android, implementing an interactive map requires the **Google Maps SDK**, which mandates:

* Generating a **Google Maps API key**
* Enabling a **billing account** in Google Cloud Platform (even for zero usage)

At this time, the decision was made **not to proceed with creating or linking a billing account**. Due to this platform constraint, it is **not technically possible to implement the map-view requirement without violating Google’s SDK usage policies**.

It is important to highlight that:

* The **core geofencing functionality does NOT depend on Google Maps**
* All logic related to geofence monitoring, duration calculation, persistence, notifications, and list-based UI is fully achievable using native Android APIs

This limitation has been documented transparently rather than implementing a partial or non-compliant workaround.


---

## Status

* Core geofencing logic: ✅ Implemented
* Entry/exit tracking & duration calculation: ✅ Implemented
* Local persistence (Room DB): ✅ Implemented
* List-based UI (RecyclerView): ✅ Implemented
* Map visualization (Google Maps): ❌ Not implemented due to API key & billing dependency

