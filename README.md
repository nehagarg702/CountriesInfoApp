# Country List UI Application

This project is a Country List UI application built with **Jetpack Compose** using **MVVM architecture**. The app displays a list of countries with details such as their name, capital, currency, population, and flag. It supports offline data persistence with Room database, network data fetching, search functionality, population filtering, and graceful error handling.

---

## 🚀 **Features**
- **Country List:** View a list of countries with essential information and flag images.
- **Search Functionality:** Search countries by name with real-time filtering.
- **Population Filter:** Filter countries based on population categories: All, <1M, <5M, and <10M.
- **Offline Support:** Data is fetched from the local Room database when offline and updated in the background when online.
- **Error Handling:** Handles network errors with retry options.
- **Responsive UI:** Built with Jetpack Compose for a modern, responsive, and smooth user experience.

---

## 🏗️ **Project Structure**
```plaintext
├── data
│   ├── local
│   │   ├── dao/                  # Data Access Object for Room
│   │   └── entity/               # Room database entities
│   │   └── database/            
│   ├── global
│   │   └── model/               # Room database entities
│   │   └── network/              # Retrofit API services & network models
│   └── repository/
├── di/
├── ui
│   ├── screens/                  # Composables for UI screens
│   └── components/               # Reusable UI components (CountryItem, Filters)
│   └── activity/               
│   └── theme/               
├── viewmodel/                    # ViewModel classes
└── utils/                        # Utility classes (Network Connectivity, Mappers)
```

---

## 📦 **Setup & Installation**
1. **Clone the repository:**
   ```bash
   git clone https://github.com/nehagarg702/CountriesInfoApp.git
   ```
2. **Open in Android Studio:**
   - Open the project in Android Studio.
   - Let Gradle sync the project dependencies.
3. **Run the application:**
   - Connect an emulator or Android device.
   - Click on **Run ▶️** or use the terminal:
     ```bash
     ./gradlew installDebug
     ```

---

## 📊 **Tech Stack**
- **Kotlin** – Programming language.
- **Jetpack Compose** – Modern toolkit for building native Android UIs.
- **MVVM Architecture** – Clean architecture pattern.
- **Room Database** – Local data storage.
- **Retrofit** – Network requests.
- **Koin** – Dependency injection (if applicable).
- **Coroutines & Flow** – Asynchronous data handling.

---

## 🔎 **How it Works**
- On launch, the app checks the local database for country data.
- If data is available locally, it displays it immediately.
- In the background, the app fetches the latest data from the network (if available) and updates the database.
- The UI refreshes with the latest data without user intervention.
- Users can search countries by name and filter them based on population.
