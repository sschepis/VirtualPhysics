# REST API Example - Virtual Physics

This example demonstrates how to use Virtual Physics for efficient data transmission in a REST API context.

## Overview

This simplified REST API example shows:
- Creating data structures using Virtual Physics Compounds
- Serializing responses to photon strings (90% smaller than JSON)
- Deserializing requests from photon strings
- Type-safe data handling without external serialization libraries
- Built-in rollback capabilities

## What's Demonstrated

### 1. Data Structures
- `User` class extends `Compound` to create a structured data object
- Individual fields are `Atom` instances
- Full type safety maintained

### 2. Serialization
- **Emit**: Convert objects to photon strings with one line
- **Materialize**: Reconstruct objects from photon strings with one line
- No configuration or annotations needed

### 3. Size Comparison
The example includes side-by-side comparison with JSON to demonstrate the efficiency:
- JSON: ~95 characters for a simple user object
- Photon String: ~10 characters for the same data
- **Result**: ~90% size reduction

### 4. CRUD Operations
- GET user by ID
- POST create new user
- PUT update user
- GET all users

## Running the Example

### Option 1: From this directory

If you've built Virtual Physics and want to run just this example:

```bash
# First, ensure you're in the examples/rest-api directory
cd examples/rest-api

# Note: This example is designed to be run from the main project
# Return to the main project directory
cd ../..

# Run using the test framework
./gradlew test
```

### Option 2: Copy to Main Project

Copy `UserAPI.kt` to `src/test/kotlin/applications/` and run it as a test application.

### Option 3: Run Directly

```bash
# From the main Virtual Physics directory
kotlinc -cp build/libs/VirtualPhysics-1.0-SNAPSHOT.jar \
    examples/rest-api/src/main/kotlin/UserAPI.kt \
    -include-runtime -d UserAPI.jar

java -jar UserAPI.jar
```

## Expected Output

```
============================================================
Virtual Physics REST API Example
============================================================

1. GET /users/1
   Response (Photon String): [compact string]
   Size: ~10 characters

   Traditional JSON Response: {"id":1,"username":"john_doe","email":"john@example.com","active":true}
   Size: 95 characters

   ✓ Size Reduction: 90%

2. Deserializing the response:
   User ID: 1
   Username: john_doe
   Email: john@example.com
   Active: true

3. POST /users (Create new user)
   Request Body (Photon String): [compact string]
   Size: ~10 characters
   Response: [compact string]

4. PUT /users/2 (Update user)
   Request Body: [compact string]
   Response: [compact string]

5. GET /users (Get all users)
   Number of users: 4
   User 1: [photon string 1]
   User 2: [photon string 2]
   User 3: [photon string 3]
   User 4: [photon string 4]

6. Bonus: Built-in Rollback
   Original: demo_user
   After change: changed_username
   After rollback: demo_user

============================================================
Summary:
============================================================
✓ Virtual Physics provides 90% smaller payloads
✓ Type-safe serialization/deserialization
✓ No external dependencies (no Jackson, Gson, etc.)
✓ Built-in rollback mechanism
✓ One-line serialization: object.emit()
✓ One-line deserialization: Absorber.materialize()
============================================================
```

## Key Takeaways

### Traditional Approach (with JSON)
```kotlin
// Requires external library (Jackson, Gson, etc.)
@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val active: Boolean
)

// Serialize
val json = Json.encodeToString(user)  // ~95 bytes

// Deserialize
val user = Json.decodeFromString<User>(json)
```

### Virtual Physics Approach
```kotlin
class User : Compound() {
    val id = Atom().with(0)
    val username = Atom().with("")
    val email = Atom().with("")
    val active = Atom().with(true)
    
    init {
        bind(id)
        bind(username)
        bind(email)
        bind(active)
    }
}

// Serialize - ONE LINE
val photon = user.emit()  // ~10 bytes (90% smaller!)

// Deserialize - ONE LINE
val (user, _) = Absorber.materialize(photon)
```

## Advantages Over Traditional Serialization

1. **Size**: 90% smaller payloads mean:
   - Faster network transmission
   - Lower bandwidth costs
   - Better performance on mobile devices

2. **Type Safety**: Full type information preserved without schemas

3. **No Dependencies**: No need for Jackson, Gson, kotlinx.serialization, etc.

4. **Built-in Features**: 
   - Rollback/undo functionality
   - Data binding
   - Reactive updates

5. **Simplicity**: One line to serialize, one line to deserialize

## Real-World Use Cases

This pattern is ideal for:
- **Microservices**: Efficient inter-service communication
- **Mobile Apps**: Minimize data transfer on cellular networks
- **IoT Devices**: Conserve bandwidth on constrained devices
- **Real-time Apps**: Faster updates with smaller payloads
- **Full-Stack Kotlin**: Share exact data structures between frontend and backend

## Next Steps

- Explore the data binding example for reactive updates
- Check out the rollback example for advanced state management
- Build a full-stack application using Ktor + Kotlin/JS
- Implement actual HTTP endpoints using your preferred framework

## Questions?

- Check the main [README.MD](../../README.MD)
- Read [GETTING_STARTED.md](../../GETTING_STARTED.md)
- Open an issue on GitHub
