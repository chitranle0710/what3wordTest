What3Words Android Tech Test 2025 
This app displays trending movies and allows users to search for movies using the [TMDB API](https://developer.themoviedb.org/reference/intro/getting-started). It is built using modern Android development tools and best practices.

---

## ✨ Features

- 🔥 Show today's **Trending Movies**
- 🔍 **Search** movies online using TMDB API
- 🧭 **Detail Screen** with full movie info and links
- 📦 **Offline Support** for:
  - Trending movies (cached)
  - Viewed movie detail (persisted)
- ⚙️ Modern Android Stack:
  - Jetpack Compose
  - Paging 3
  - MVVM + Clean Architecture
  - Koin for Dependency Injection
  - Room for local cache
  - Kotlin Coroutines + Flows

---

## 📷 Screenshots

<img width="371" height="805" alt="Ảnh màn hình 2025-08-02 lúc 23 45 55" src="https://github.com/user-attachments/assets/af9b375a-e239-45c8-9fa4-7438cb5e5b8f" />

<img width="357" height="807" alt="Ảnh màn hình 2025-08-02 lúc 23 45 45" src="https://github.com/user-attachments/assets/7c5082f0-7d1c-4cf1-8b30-a2fa151ee9a0" />

----

- **UI Layer**: Jetpack Compose, ViewModel, StateFlow
- **Domain Layer**: UseCases + Models
- **Data Layer**: Repository Pattern, Room, Retrofit

  ---

## 🚀 How to Run

1. Clone this repo
2. Add your [TMDB API key](https://developer.themoviedb.org/reference/intro/getting-started) to `local.properties`:
3. Open in **Android Studio** (Hedgehog or newer)
4. Click **Run**

   ---

## 🔍 Tech Stack

| Tech             | Use                          |
|------------------|-------------------------------|
| **Kotlin**        | Programming Language          |
| **Jetpack Compose** | UI Toolkit                  |
| **Paging 3**       | List Pagination              |
| **Room**           | Local Database               |
| **Koin**           | Dependency Injection         |
| **Retrofit**  | Network API calls            |
| **StateFlow**      | Reactive UI state            |
| **Coroutines**     | Asynchronous programming     |

---

## ✅ Requirements Met

- [x] Trending Movie List
- [x] Search with debounce
- [x] Paging for both search & trending
- [x] Movie Detail Page
- [x] Open homepage URL in browser
- [x] Offline cache: trending + detail
- [x] Error Handling: offline, API failure, empty state
- [x] Clean Architecture + DI + Compose

---

## 🧠 Notes

- **Debounce + FlatMapLatest** used for smooth search experience
- Movie detail is saved offline after viewing
- Trending data is cached for 1 hour to avoid frequent API calls
- Switching between local and remote paging is dynamic

## ⚠️ Limitations
Initially, the goal was to build this app using Kotlin Multiplatform Mobile (KMM) and SQLDelight for shared business logic and local caching. However, due to compatibility issues between the latest Android Studio (Narwhal | 2025.1.1) and SQLDelight 2.x, and the limited time frame for the technical test, I reverted to a full native Android-only implementation using Room for offline persistence.
KMM setup and SQLDelight caching would be explored further if more time and IDE compatibility were available.

---

## 👨‍💻 Author

Created by **[Chi Tran]**  
For the **What3Words Android Tech Test 2025**  
Licensed under the MIT License.

