
 
### 📱 Android-Notes 📱

This **Android Notes App** repository provides a solution for creating and managing personal notes on the Android platform. It incorporates the following features:

### ✏️ Note Creation and Maintenance
Users can create and maintain an unlimited number of personal notes. Each note consists of a **title**, **note text**, and a **last-update time**.

### 🔄 Support for Both Orientations
The app's design and layout are optimized to work seamlessly in both **portrait** and **landscape** orientations. It ensures a consistent user experience regardless of the device's orientation.

### 📂 Data Persistence with JSON Format
Notes are saved to and loaded from the internal file system in **JSON format**. Upon loading, if no file is found, the application starts with no existing notes and without encountering errors. When new notes are added, a new JSON file is automatically created.

### ⚙️ Implementation Details
JSON file loading occurs within the **onCreate** method of the application. Saving takes place whenever a new note is added or an existing note is deleted.

### 📝 Note Class Implementation
The repository includes a **Note class** representing each individual note within the application. This class encapsulates the title, note text, and last save date for each note. It implements the **Serializable interface** to facilitate serialization and deserialization.

### 🛠️ Development Environment
The app is developed using **Java** within **Android Studio**, leveraging the built-in development tools and libraries provided by the Android platform.

The repository is structured to provide a robust and user-friendly solution for managing personal notes on Android devices. It emphasizes **data persistence**, **intuitive user interface design**, and **efficient implementation practices** to ensure a smooth and reliable user experience. 🚀
